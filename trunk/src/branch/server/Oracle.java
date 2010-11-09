package branch.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;


/**
 * Launches the Oracle GUI and performs its functions
 * @author Nikhil
 * 
 */
public class Oracle extends javax.swing.JFrame {
	private static final String ORACLE = "ORACLE";
	public static final String LINE = "---------------------------------------------------------------------------------------------\n";
	private static NodeProperties properties_;

	
	/** Creates new form BranchGUI */
	public Oracle() {
		super(ORACLE);
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
		messageLabel = new javax.swing.JLabel();
		removeServerButton = new javax.swing.JButton();
		textArea = new JTextArea(15, 70);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);

		JScrollPane pScroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(pScroll);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		messageLabel.setText("Message:");

		removeServerButton.setText("Remove Server");
		removeServerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeServerButtonActionPerformed(evt);
			}
		});

		JButton addServerButton = new JButton();
		addServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addServerActionPerformed(evt);
			}
		});
		addServerButton.setText("Add Server");

		JLabel label = new JLabel();
		label.setText("Server Name:");

		serverNameTextField = new JFormattedTextField();


		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(31)
						.addGroup(layout.createParallelGroup(Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(removeServerButton)
										.addGap(37)
										.addComponent(addServerButton, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addComponent(label, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(serverNameTextField, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
												.addGap(24)))
												.addGap(60))
												.addGroup(layout.createSequentialGroup()
														.addGap(4)
														.addGroup(layout.createParallelGroup(Alignment.LEADING)
																.addGroup(layout.createSequentialGroup()
																		.addComponent(messageLabel, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
																		.addGap(60))
																		.addComponent(pScroll, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
																		.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(17)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
								.addComponent(serverNameTextField, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
								.addGap(8)
								.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(removeServerButton)
										.addComponent(addServerButton))
										.addGap(18)
										.addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(pScroll, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(54))
		);
		getContentPane().setLayout(layout);
		pack();
	}// </editor-fold>
	private javax.swing.JLabel messageLabel;
	private javax.swing.JButton removeServerButton;
	private javax.swing.JTextArea textArea;
	private javax.swing.JFormattedTextField serverNameTextField;
	// End of variables declaration

	private void removeServerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		View view;
		String server = serverNameTextField.getText();
		String groupid = NodeName.getService(server);

		if(properties_.views_.containsKey(groupid)) {
			view = properties_.views_.get(groupid);
			if (view.removeServer(server)) {
				if(view.isEmpty())
					properties_.views_.remove(groupid);
				else
					properties_.views_.put(groupid, view);
			} 
			else {
				// TODO print error
				textArea.setText("Error: cant find server");
			}
		} 
		else {
			// TODO error view = new View(groupid);
			textArea.setText("Error: cant find server");
		}		
	}

	private void broadcastView (Message msg) {
		ArrayList<String> listOfServers = new ArrayList<String>();
		Set<String> keySet = properties_.views_.keySet();
		for (String key : keySet) {
			View view = properties_.views_.get(key);
			listOfServers.addAll(view.listOfServers);
			NetworkWrapper.send(msg.toString(), NodeName.getGUI(key));
			for (String server : listOfServers) {
				NetworkWrapper.sendToServer(msg.toString(), server);
			}
		}
	}
	
	private void addServerActionPerformed(ActionEvent evt) {
		ArrayList<String> inititialConfig = new ArrayList<String>();
		// inititialConfig.add(serverNameTextField.getText());
		inititialConfig.add("S01_01");
		inititialConfig.add("S02_01");
		// inititialConfig.add("S01_02");
		// inititialConfig.add("S02_02");
		// inititialConfig.add("S01_01");

		Trxn transaction= null;
		for (String server : inititialConfig) {
			String groupid = NodeName.getService(server);
			View view;
			if(properties_.views_.containsKey(groupid))
				view = properties_.views_.get(groupid);
			else
				view = new View(groupid);

			view.addServer(server);

			properties_.views_.put(groupid, view);

			Message msg;
			SpecialMsg spl = new SpecialMsg(SpecialMsg.Type.VIEW, view, null, null);
			msg = new Message(properties_.getNode(), spl);
			broadcastView(msg);
			textArea.append(view.toString() + "\n");
		}
	}

	public static class GuiThread implements Runnable {
		private Oracle gui_;

		public GuiThread(Oracle gui) {
			gui_ = gui;
		}

		public void run() {
			gui_.setVisible(true);
		}
	}

	public static void main(String args[]) {
		
		ServerSocket serverSocket = null;

		try {
			properties_ = new NodeProperties(args, true);
		} catch (NodeProperties.NodePropertiesException e) {
			e.printStackTrace();
			System.err.println("Unable to parse CLI for Oracle GUI");
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
					"Could not listen to port: " + properties_.getPort());
			//System.exit(1);
		}

		Oracle branchGUI = new Oracle();
		GuiThread guiThread = new GuiThread(branchGUI);
		java.awt.EventQueue.invokeLater(guiThread);

		System.out.println(properties_.print());

		// Oracle GUI starts listening.
		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();

				BufferedReader in = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				String str = in.readLine();

				Message msg = Message.parseString(str);

				if(msg.type_ == Message.MsgType.REQ || msg.type_ == Message.MsgType.RESP ) {
					// No-one should send a REQ to Oracle GUI.
					System.err.println("Unexpected message type in Oracle GUI. Ignoring.");
					continue;
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
