package branch.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



/*
 * BranchGUI.java
 *
 * Created on 18 Sep, 2010, 9:21:51 AM
 */

/**
 *
 * @author Nikhil
 * 
 */
public class BranchGUI extends javax.swing.JFrame {
	public static final String LINE = "---------------------------------------------------------------------------------------------\n";
	private static NodeProperties properties_;
	
	private static BlockingMessageHandler bmh_;
	private int snapshotCounter_ = 0;
	private int lastSerialNumCounter_ =0;
	
	/** Creates new form BranchGUI */
	public BranchGUI() {
		super(BranchGUI.properties_.getNode());
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		transGroup = new javax.swing.ButtonGroup();
		messageLabel = new javax.swing.JLabel();
		submitReqestButton = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		destAcNumTextField = new javax.swing.JFormattedTextField();
		acNumTextField = new javax.swing.JFormattedTextField();
		acNoLabel = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		textArea = new JTextArea(15, 100);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		
		JScrollPane pScroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(pScroll);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		messageLabel.setText("Message:");

		submitReqestButton.setText("Submit Request");
		submitReqestButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				submitReqestButtonActionPerformed(evt);
			}
		});

		destAcNumTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
		destAcNumTextField.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				destAcNumTextFieldActionPerformed(evt);
			}
		});

		acNumTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
		acNumTextField.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				acNumTextFieldActionPerformed(evt);
			}
		});

		acNoLabel.setText("Account Num:");
		
		JButton snapshotButton = new JButton();
		snapshotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				snapshotButtonActionPerformed(evt);
			}
		});
		snapshotButton.setText("SNAPSHOT");
		snapshotButton.setVisible(false);
		snapshotButton.setEnabled(false);		

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(21)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(jPanel2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addComponent(jPanel1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
					.addGap(274))
				.addGroup(layout.createSequentialGroup()
					.addGap(31)
					.addComponent(submitReqestButton)
					.addGap(37)
					.addComponent(snapshotButton, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(293, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
					.addGap(14)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(pScroll, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(layout.createSequentialGroup()
							.addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
							.addGap(495))))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addGap(24)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(submitReqestButton)
						.addComponent(snapshotButton))
					.addGap(18)
					.addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pScroll, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(54, Short.MAX_VALUE))
		);
		jPanel2.setLayout(new GridLayout(0, 1, 0, 0));

		transferButton = new javax.swing.JRadioButton();

		transGroup.add(transferButton);
		transferButton.setLabel("Transfer");
		transferButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				transferButtonActionPerformed(evt);
			}
		});
		queryButton = new javax.swing.JRadioButton();

		transGroup.add(queryButton);
		queryButton.setLabel("Query");
		queryButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				queryButtonActionPerformed(evt);
			}
		});
		withdrawButton = new javax.swing.JRadioButton();

		transGroup.add(withdrawButton);
		withdrawButton.setLabel("Withdraw");
		withdrawButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				withdrawButtonActionPerformed(evt);
			}
		});
		depositButton = new javax.swing.JRadioButton();

		transGroup.add(depositButton);
		depositButton.setLabel("Deposit");
		depositButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				depositButtonActionPerformed(evt);
			}
		});
		jPanel2.add(depositButton);
		jPanel2.add(withdrawButton);
		jPanel2.add(queryButton);
		jPanel2.add(transferButton);
		jPanel1.setLayout(new GridLayout(0, 2, 0, 0));
		serialNoLabel = new javax.swing.JLabel();

		serialNoLabel.setText("Serail Number:");
		serialNoLabel.setVisible(false);
		jPanel1.add(serialNoLabel);
		serialNumTextField = new javax.swing.JFormattedTextField();

		serialNumTextField.setCursor(new java.awt.Cursor(
				java.awt.Cursor.TEXT_CURSOR));

		serialNumTextField.setVisible(false);
		jPanel1.add(serialNumTextField);
		jPanel1.add(acNoLabel);
		jPanel1.add(acNumTextField);
		srcAcNumTextField = new javax.swing.JFormattedTextField();

		srcAcNumTextField.setCursor(new java.awt.Cursor(
				java.awt.Cursor.TEXT_CURSOR));
		srcAcNumTextField
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						srcAcNumTextFieldActionPerformed(evt);
					}
				});
		amountTextField = new javax.swing.JFormattedTextField();

		amountTextField.setCursor(new java.awt.Cursor(
				java.awt.Cursor.TEXT_CURSOR));
		amountTextField.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				amountTextFieldActionPerformed(evt);
			}
		});
		amtLabel = new javax.swing.JLabel();

		amtLabel.setText("Amount:");
		jPanel1.add(amtLabel);
		jPanel1.add(amountTextField);
		srcAcNoLabel = new javax.swing.JLabel();

		srcAcNoLabel.setText("Source Account Num:");
		jPanel1.add(srcAcNoLabel);
		jPanel1.add(srcAcNumTextField);
		destAcNoLabel = new javax.swing.JLabel();

		destAcNoLabel.setText("Dest Account Num:");
		jPanel1.add(destAcNoLabel);
		jPanel1.add(destAcNumTextField);
		getContentPane().setLayout(layout);
		showOrHideFields();
		pack();
	}// </editor-fold>

	private void queryButtonActionPerformed(java.awt.event.ActionEvent evt) {
		showOrHideFields();
		textArea.append("");
	}

	private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {
		showOrHideFields();
		textArea.append("");
	}

	private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {
		showOrHideFields();
		textArea.append("");
	}

	private void transferButtonActionPerformed(java.awt.event.ActionEvent evt) {
		showOrHideFields();
		textArea.append("");
	}

	private void acNumTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void destAcNumTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void srcAcNumTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void amountTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}
	
	private void updateLastSerNumCounter (String ser_num){
		int serNumInt = Integer.parseInt(ser_num.substring(3));
		if (serNumInt > lastSerialNumCounter_)
			lastSerialNumCounter_ = serNumInt;
	}
	private String giveNextSerNum() {
		String ret = "";
		int newSerialInt = lastSerialNumCounter_ + 1;
		if (depositButton.isSelected()) {
			ret = "D";
		} else if (withdrawButton.isSelected()) {
			ret = "W";
		} else if (queryButton.isSelected()) {
			ret = "Q";
		} else if (transferButton.isSelected()) {
			ret = "T";
		} 
		ret = ret + properties_.getGroupId()
			+ String.format("%08d", newSerialInt);
		return ret;
	}
	private boolean isValidSerNo (String serNum) {
		int serNumInt = Integer.parseInt(serNum.substring(3));
		/* checks if this ser_num is either from the past or next in sequence*/
		if (serNumInt > lastSerialNumCounter_ + 1 || serNumInt <= 0)
			return false;
		return true;
	}
	private String isSerNoFormat(String str) {
		/*
		 * Serial Number will have following format: ^[DWTQ]: Will start with request type.
		 * [0-9]{10}+ : will have 10 numerals
		 */
		String expression = "^[DWTQ][0-9]{10}+$";
		CharSequence inputStr = str;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (!matcher.matches()) {
			return "Format should be [DWTQ] + branchID + 8 digit integer." ;
		}
		
		if (!BranchGUI.properties_.getGroupId().equals(NodeName.getService(str)))
			return "Format should be [DWTQ] + branchID + 8 digit integer. BranchId specified is not this branch" ;
		
		if (depositButton.isSelected() && str.charAt(0)!= 'D') {
			return "First digit should be 'D' for a deposit.";
		} else if (withdrawButton.isSelected() && str.charAt(0)!= 'W') {
			return "First digit should be 'W' for a withdraw.";
		} else if (queryButton.isSelected() && str.charAt(0)!= 'Q') {
			return "First digit should be 'Q' for a query.";
		} else if (transferButton.isSelected() && str.charAt(0)!= 'T') {
			return "First digit should be 'T' for a transfer.";
		} 
		return "";
	}
	private boolean isAmount(String str) {
		/*
		 * Number: A numeric value will have following format: ^[0-9]*: May have one or more
		 * digits. \\.? : May contain an optional "." (decimal point) character.
		 * [0-9]+$ : ends with numeric digit.
		 */
		// Initialize reg ex for numeric data.
		String expression = "^[0-9]*\\.?[0-9]+$";
		CharSequence inputStr = str;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	public boolean accBelongsToThisBranch(String accNo){
		if (BranchGUI.properties_.getGroupId().equals(accNo.substring(0, 2)))
			return true;
		else 
			return false;
	}
	public static boolean isAccountNumber(String number) {
		boolean isValid = false;
		/* Number: An account value will have following format: ^[0-9][0-9]:
		 * Starts with 2 numeric digits. \\. : contains "." (decimal point)
		 * character. [0-9]+$ : ends with numeric digits. */
		// Initialize reg ex for numeric data.
		String expression = "^[0-9][0-9]\\.[0-9][0-9][0-9][0-9][0-9]$";
		CharSequence inputStr = number;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}  
	
	private void showOrHideFields() {
		if (depositButton.isSelected() || withdrawButton.isSelected()) {

			acNumTextField.setVisible(true);
			serialNumTextField.setVisible(true);
			amountTextField.setVisible(true);
			destAcNumTextField.setVisible(false);
			srcAcNumTextField.setVisible(false);

			acNoLabel.setVisible(true);
			serialNoLabel.setVisible(true);
			amtLabel.setVisible(true);
			destAcNoLabel.setVisible(false);
			srcAcNoLabel.setVisible(false);

		} else if (queryButton.isSelected()) {
			acNumTextField.setVisible(true);
			serialNumTextField.setVisible(true);
			amountTextField.setVisible(false);
			destAcNumTextField.setVisible(false);
			srcAcNumTextField.setVisible(false);

			acNoLabel.setVisible(true);
			serialNoLabel.setVisible(true);
			amtLabel.setVisible(false);
			destAcNoLabel.setVisible(false);
			srcAcNoLabel.setVisible(false);

		} else if (transferButton.isSelected()) {
			acNumTextField.setVisible(false);
			serialNumTextField.setVisible(true);
			amountTextField.setVisible(true);
			destAcNumTextField.setVisible(true);
			srcAcNumTextField.setVisible(true);

			acNoLabel.setVisible(false);
			serialNoLabel.setVisible(true);
			amtLabel.setVisible(true);
			destAcNoLabel.setVisible(true);
			srcAcNoLabel.setVisible(true);
		} else {
			acNumTextField.setVisible(false);
			serialNumTextField.setVisible(false);
			amountTextField.setVisible(false);
			destAcNumTextField.setVisible(false);
			srcAcNumTextField.setVisible(false);

			acNoLabel.setVisible(false);
			serialNoLabel.setVisible(false);
			amtLabel.setVisible(false);
			destAcNoLabel.setVisible(false);
			srcAcNoLabel.setVisible(false);
		}
		serialNumTextField.setText(giveNextSerNum());
	}
	// Variables declaration - do not modify
	private javax.swing.JLabel acNoLabel;
	private javax.swing.JFormattedTextField acNumTextField;
	private javax.swing.JFormattedTextField amountTextField;
	private javax.swing.JLabel amtLabel;
	private javax.swing.JRadioButton depositButton;
	private javax.swing.JLabel destAcNoLabel;
	private javax.swing.JFormattedTextField destAcNumTextField;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JLabel messageLabel;
	private javax.swing.JRadioButton queryButton;
	private javax.swing.JLabel serialNoLabel;
	private javax.swing.JFormattedTextField serialNumTextField;
	private javax.swing.JLabel srcAcNoLabel;
	private javax.swing.JFormattedTextField srcAcNumTextField;
	private javax.swing.JButton submitReqestButton;
	private javax.swing.ButtonGroup transGroup;
	private javax.swing.JRadioButton transferButton;
	private javax.swing.JRadioButton withdrawButton;
	private javax.swing.JTextArea textArea;
	// End of variables declaration

	private void submitReqestButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		String amount = amountTextField.getText() ;
		String serNo = serialNumTextField.getText();
		String acNo = acNumTextField.getText();
		String srcAcNo = srcAcNumTextField.getText();
		String destAcNo = destAcNumTextField.getText();
		String defaultAcNo = "00.00000"; 
		String tempStr="";
		Trxn transaction= null;
		Message msg;
		
		/* DO NOT REORDER THE FOLLOWING IF-ELSE BRANCHES */
		/*Checking serial number format*/
		if (!isSerNoFormat(serNo).isEmpty()) {
				textArea.append("Serial Number format incorrectly specified. " 
						+ isSerNoFormat(serNo) 
						+ " Please validate.\n" 
						+ LINE);
				return;
		}
		if (!isValidSerNo(serNo)) {
			textArea.append("Serial Number niether next in sequence nor used before. Please validate."
					+ "Next serial number should be [DWQT]"
					+ String.format("%02d", properties_.getGroupId())
					+ String.format("%02d", lastSerialNumCounter_ + 1)
					+ "\n" +LINE);
			return;
		}
		if (!queryButton.isSelected() && !isAmount(amount)) {
			/*Checking format of the amount */
			textArea.append("Amount format incorrectly specified. Please validate.\n"+LINE);
			return;
		} else if (!transferButton.isSelected() && !isAccountNumber(acNo)){
			/*Checking format of the account number for deposit, withdraw and query */
			textArea.append("Account number format incorrectly specified. Please validate.\n"+LINE);
			return;
		} else if (transferButton.isSelected() && (!isAccountNumber(srcAcNo)||!isAccountNumber(destAcNo)) ){
			/*Checking format of the account numbers for transfers*/
			textArea.append("Either source or destination account number incorrectly specified. Please validate.\n"+LINE);
			return;
		} else if(!transferButton.isSelected() && !accBelongsToThisBranch(acNo)) {
			/* Checking if query/deposit/withdraw req can be executed at this branch */
			textArea.append("Ignoring request. Specified account not managed by this branch.\n"+LINE);
			return;
		} else if(transferButton.isSelected() && !accBelongsToThisBranch(srcAcNo)) {
			/* Checking if transfer req can be executed at this branch */
			textArea.append("Ignoring request. Source account not managed by this branch.\n"+LINE);
			return;
		} else if(transferButton.isSelected() && srcAcNo.equalsIgnoreCase(destAcNo)) {
			/* Checking if transfer src is same as dest */
			textArea.append("Ignoring request. Source account is same as destination account.\n"+LINE);
			return;
		}
		
		if (depositButton.isSelected()) {
			transaction = new Trxn("DEPOSIT",serNo, amount, acNo, defaultAcNo, defaultAcNo);
		} else if (withdrawButton.isSelected()) {
			transaction = new Trxn("WITHDRAW",serNo, amount, acNo, defaultAcNo, defaultAcNo);
		} else if (queryButton.isSelected()) {
			transaction = new Trxn("QUERY",serNo, "0.0", acNo, defaultAcNo, defaultAcNo);
		} else if (transferButton.isSelected()) {
			transaction = new Trxn("TRANSFER",serNo, amount, defaultAcNo, srcAcNo, destAcNo);
		} 

		msg = new Message(properties_.getNode(), Message.MsgType.REQ, transaction, null);
		tempStr = msg.toString();

		boolean sendStatus = bmh_.sendRequest(msg.toString());
		
		if (sendStatus == false) {
			textArea.append("Could not connect to server. Unable to process request.\n"+LINE);
		}
		updateLastSerNumCounter(serNo);
		serialNumTextField.setText(giveNextSerNum());
	}
	private void snapshotButtonActionPerformed(ActionEvent evt) {
//		Trxn transaction= null;
//		Message msg;
		
//		String snapshotId  = null;
//		snapshotCounter_ += 1;
//		snapshotId = "S"
//			+ String.format("%02d", properties_.getGroupId())
//			+ String.format("%08d", snapshotCounter_);
//		transaction = new Trxn("SNAPSHOT_MARKER",
//				snapshotId,"0.0","00.00000","00.00000","00.00000");
//		msg = new Message(properties_.getNode(),
//				Message.MsgType.REQ,
//				transaction,
//				null);
//		
//		boolean sendStatus = bmh_.sendRequest(msg.toString());
//		
//		if (sendStatus == false) {
//			textArea.append("GUI could not get reply from server. Unable to process request.\n"+LINE);
//		}
	}

	public void printUserString (TrxnResponse tr)	
	{
		String printMsg = "";
		if (tr.getType() == TrxnResponse.Type.TRANSACTION) {
			printMsg += ("Serial number for the transaction is " + tr.getSerialNum() + ".");
			printMsg += (" Account balance is " + tr.getAmt()+ ". ");
			//printMsg = (tr.getIsError()== false) ? "Transaction succeeded. " : "Transaction failed. ";
			printMsg += tr.getErrorMsg();
		} else {
			
			printMsg += ("Snapshot: " + tr.getSerialNum() 
					+ "initiated by " 
					+ "G" + tr.getSerialNum().substring(1,3)
					+ " is completed.\n ");
			String prettyString = Snapshot.getPrettyStringFromSnapshotString(tr.getSnapshotResponse());
			printMsg += (prettyString);
		}	
		textArea.append(printMsg+"\n"+LINE);
	}

	public static class BlockingMessageHandler {
		private	Thread toWakeUp_;
		private int waitingTime_;
		private static final int INITIAL_WAITING_TIME = 100;
		
		public BlockingMessageHandler() {
			initialize();			
		}
		
		private void initialize() {
			toWakeUp_ = null;
			waitingTime_ = INITIAL_WAITING_TIME;
		}

		boolean sendRequest(String msg) {
			try {
				toWakeUp_ = Thread.currentThread();
				while (true) {
					waitingTime_ *= 2;
					
					// Even if we fail to connect to the head, we keep on retrying.
					// As it could be the case that head is the server that went down.
					NetworkWrapper.sendToService(msg, properties_.getGroupId());

					Thread.sleep(waitingTime_);
				}
			} catch (InterruptedException e) {
				// The wait of the GUI was interrupted by the receiving thread.
				// Message was sent and response was received successfully.
				System.out.println("Response received.");
				return true;
			} finally {
				initialize();
			}
		}
		
		void notifyOfResponse() {
			// notifyOfResponse is called. That means the GUI thread should be waiting.
			// The case toWakeUp == null should never occur.
			if (toWakeUp_ != null) {
				toWakeUp_.interrupt();
			}
		}
	}
	
	public static class GuiThread implements Runnable {
		private BranchGUI gui_;
		
		public GuiThread(BranchGUI gui) {
			gui_ = gui;
		}

		public void run() {
			gui_.setVisible(true);
		}
	}
	
	public static void main(String args[]) {
		// The GUI should wait for a response of the sent request to arrive from the server.
		// 'bmh_' is used to keep the listening-thread synchronized with the GUI thread.
		bmh_ = new BlockingMessageHandler();
		ServerSocket serverSocket = null;
		
		try {
			properties_ = new NodeProperties(args, true);
		} catch (NodeProperties.NodePropertiesException e) {
			e.printStackTrace();
			System.err.println("Unable to parse CLI for GUI");
		}
		
		NetworkWrapper.setProperties(properties_);

		// Match the IP of the current machine with the IP provided in 'servers'
		String myIp = properties_.getIp();
		if (!myIp.equals("localhost") && !myIp.equals("127.0.0.1")) {
			InetAddress inet = null;
			try {
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e1) {
				System.err.println("Could not get inet-address for machine.");
				System.exit(1);
			}

			if (!inet.getHostAddress().equals(myIp)) {
				System.err.println("Server is supposed to start from: " + myIp);
				System.exit(1);
			}
		}

		// Check that a valid port was provided.
		if (properties_.getPort() < 0) {
			System.err.println("port not assigned.");
			System.exit(1);
		}

		// Create a ServerSocket for the given port.
		try {
			serverSocket = new ServerSocket(properties_.getPort());
		} catch (IOException e){
			System.err.println(
					"Coult not listen to port: " + properties_.getPort());
			//System.exit(1);
		}
		
		BranchGUI branchGUI = new BranchGUI();
		GuiThread guiThread = new GuiThread(branchGUI);
		java.awt.EventQueue.invokeLater(guiThread);
		System.out.println(properties_.print());
		
		// GUI starts listening.
		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();

				BufferedReader in = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				String str = in.readLine();

				Message msg = Message.parseString(str);

				if(msg.type_ == Message.MsgType.REQ) {
					// No-one should send a REQ to GUI.
					System.err.println("Unexpected message type in GUI. Ignoring.");
					continue;
				} else if (msg.type_ == Message.MsgType.RESP) {
					// Received a response message.
					// It can be either a snapshot response (initiated by some other GUI).
					// or response to a request from this GUI.
					// Print the response to the GUI text-area.
					TrxnResponse tResponse = msg.getTrxnResponse();

					branchGUI.printUserString(tResponse);
					if (tResponse.getType() == TrxnResponse.Type.SNAPSHOT) {
						// Response is of a snapshot request.
						String snapshotId = tResponse.getSerialNum();
						String initiatingBranch = snapshotId.substring(1, 3);

						// Wake-Up the GUI if the request was from this GUI.
						if (initiatingBranch.equalsIgnoreCase(properties_.getGroupId())){
							bmh_.notifyOfResponse();
						}
					} else {
						System.out.println("notifying...");
						// Wake-Up the GUI if the request was from this GUI.
						bmh_.notifyOfResponse();
					}
				} else {
					// Only REQ / RESP type message is expected.
					System.err.println("Unknown type of message received. Ignoring.");
				}
			} catch (IOException e) {
				System.err.println("Coult not accept connection.");
				System.exit(1);
			}
		}
	}
}
