package branch.server;

/**
 * 
 * Gives a processTransaction interface for processing a transaction.
 *
 */

public class TrxnManager {
	private Trxn trxn_;

	public TrxnManager(Trxn ts) {
		trxn_ = ts;
	}

	public TrxnResponse processTransaction() {

		/* need to set balance, status, serial no. and Error msg in response */
		TrxnResponse trxnResponse = null;
		String serial_Num = trxn_.getSerialNum();

		Double balance = 0.0;
		switch (trxn_.getType()) {
		case DEPOSIT:

			if (!TransactionLog.containsTrxn(trxn_.getSerialNum())) {
				AccDetails.deposit(trxn_.getSourceAccount(), trxn_.getAmount());
				TransactionLog.addTrxn(trxn_);
			}
			balance = AccDetails.getAllAccnts().get(trxn_.getSourceAccount());
			trxnResponse = new TrxnResponse(trxn_.getSerialNum(), TrxnResponse.Type.TRANSACTION, balance, false, "");
			break;

		case WITHDRAW:

			if (!TransactionLog.containsTrxn(serial_Num)) {
				AccDetails.withdraw(trxn_.getSourceAccount(), trxn_.getAmount());
				TransactionLog.addTrxn(trxn_);
			}
			balance = AccDetails.getAllAccnts().get(trxn_.getSourceAccount());
			trxnResponse = new TrxnResponse(trxn_.getSerialNum(), TrxnResponse.Type.TRANSACTION, balance, false, "");
			break;

		case QUERY:
			/* not added to log, not even checked in log */
			balance = AccDetails.query(trxn_.getSourceAccount());
			trxnResponse = new TrxnResponse(trxn_.getSerialNum(), TrxnResponse.Type.TRANSACTION, balance, false, "");
			break;

		case TRANSFER:
			if(trxn_.getSourceBranch().equalsIgnoreCase(BranchServer.getProperties().getGroupId())) {
				trxnResponse = handleTransferAtSource();
			} else if (trxn_.getDestBranch().equalsIgnoreCase(BranchServer.getProperties().getGroupId())) {
				trxnResponse = handleTransferAtDest();
			} else {
				System.err.println("Incorrect transaction");
			}
			break;
		default: 
			System.err.println("Illegal state. Exiting");
			System.exit(1);
		}
		return trxnResponse;
	}

	private TrxnResponse handleTransferAtDest() {
		TrxnResponse trxnResponse;
		Double balance;
		// handle transfer at destination
		if (!TransactionLog.containsTrxn(trxn_.getSerialNum())) {
			AccDetails.deposit(trxn_.getDestAccount(), trxn_.getAmount());
			TransactionLog.addTrxn(trxn_);
		}
		balance = AccDetails.getAllAccnts().get(trxn_.getDestAccount());
		trxnResponse = new TrxnResponse(trxn_.getSerialNum(), TrxnResponse.Type.TRANSACTION, balance, false, "");
		return trxnResponse;
	}

	private TrxnResponse handleTransferAtSource() {
		TrxnResponse trxnResponse;
		Double balance;
		String destinationGrp = trxn_.getDestBranch();
		
		// See if Destination Server is reachable.
		if (!trxn_.getDestBranch().equalsIgnoreCase(trxn_.getSourceBranch())) {
			final Topology topology = BranchServer.getProperties().getTopology();
			if (!topology.isReachable(destinationGrp)) {
				trxnResponse = new TrxnResponse(trxn_.getSerialNum()
						, TrxnResponse.Type.TRANSACTION 
						, AccDetails.query(trxn_.getSourceAccount())
						, true
						, "Error: Destination Server Not reachable in topology.");
				return trxnResponse;
			}
		}
		// Local Withdraw 
		if (!TransactionLog.containsTrxn(trxn_.getSerialNum())) {
			AccDetails.withdraw(trxn_.getSourceAccount(), trxn_.getAmount());
			if (trxn_.getSourceBranch().equalsIgnoreCase(trxn_.getDestBranch())) {
				//Local Deposit
				AccDetails.deposit(trxn_.getDestAccount(), trxn_.getAmount());
			}
			TransactionLog.addTrxn(trxn_);
		}
		else {
			trxn_ = TransactionLog.getTrxn(trxn_.getSerialNum());
		}

		// Deposit the amount to the destination account at different branch
		if (!trxn_.getSourceBranch().equalsIgnoreCase(trxn_.getDestBranch())) {
			Message msg = new Message(BranchServer.getProperties().getNode(),
					Message.MsgType.REQ, trxn_, null);

			if (!NetworkWrapper.send(msg.toString(), destinationGrp)) {
				System.err.println("Not able to send message to destination Server.");
			}
		}

		balance = AccDetails.getAllAccnts().get(trxn_.getSourceAccount());
		trxnResponse = new TrxnResponse(trxn_.getSerialNum(), TrxnResponse.Type.TRANSACTION, balance, false, "");
		return trxnResponse;
	}

}
