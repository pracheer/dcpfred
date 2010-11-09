package branch.server;

import java.util.HashMap;

/**
 * 
 * Picks up a message from MsgQueue and processes it.
 * It also implements the calling of different snapshot mechanism.
 * It contains the reference of the snapshot objects being processed.
 * If a new SNAPSHOT_MARKER comes it updates the currently running snapshot states
 * by stopping recording in an incoming channel or by creating a new snapshot process.
 * In case of a transaction request, it creates the Trxn object for it
 * and calls the processTransaction function to process the request.
 * It will also update the incoming-channel states for the pending snapshots if
 * required.
 */

public class MsgProcessingThread extends Thread {
	MsgQueue messages_;
	HashMap<String, Snapshot> snapshots = new HashMap<String, Snapshot> (100);

	public MsgProcessingThread(MsgQueue messages) {
		messages_ = messages;
	}

	public void run() {
		while (true) {
			// will block if the queue is empty.
			Message msg = messages_.getMsg();

			if(msg.getTrxn().getType() == Trxn.TransxType.SNAPSHOT_MARKER) {
				String snapshotId = msg.getTrxn().getSerialNum();
				Snapshot snapshot;

				// Thread goes to sleep as per the properties specified.
				// Useful to test snapshots.
				if(!snapshots.containsKey(snapshotId)) {
					try {
						sleep(BranchServer.getProperties().getSleepTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if (!snapshots.containsKey(snapshotId)) {
					/* initiating a new snapshot */
					snapshot = new Snapshot(snapshotId);
					snapshots.put(snapshotId, snapshot);			
				}

				snapshot = snapshots.get(snapshotId);
				snapshot.updateSnapshot(msg);
				
				if(snapshot.getState() == Snapshot.State.LOCALLY_COMPLETE){
					// Send the completed snapshot response to the GUI for display.
					System.out.println(snapshot.print());
					Message responseMessage = new Message(
							BranchServer.getProperties().getNode(),
							Message.MsgType.RESP,
							null,
							new TrxnResponse(snapshotId, TrxnResponse.Type.SNAPSHOT, snapshot.print()));
					NetworkWrapper.sendToGui(responseMessage.toString());
				}
			} else { // Message is a normal Message - Request.
				/* Process the transaction request */
				TrxnManager tm = new TrxnManager(msg.getTrxn());
				Message responseMessage = new Message(
						BranchServer.getProperties().getNode(),
						Message.MsgType.RESP,
						null,
						tm.processTransaction());

				// Reply the GUI if the request came from him.
				if (msg.getTrxn().getSerialNum().substring(1,3).equalsIgnoreCase(BranchServer.getProperties().getGroupId())) {
					NetworkWrapper.sendToGui(responseMessage.toString());
				}

				// update all snapshots with this msg
				if (!snapshots.isEmpty()) {
					for (Snapshot sn : snapshots.values()) {
						if (sn.getState() == Snapshot.State.RECORDING_CHANNELS) {
							sn.updateSnapshot(msg);
						}
					}
				}		
			}
		}
	}
}
