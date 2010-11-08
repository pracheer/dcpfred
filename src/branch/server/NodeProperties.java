package branch.server;

import java.io.IOException;
import java.util.Vector;

/**
 * 
 * @author qsh2
 *
 * This class uses the FlagParser to parse the flags.
 * From the FlagParser class we receive a vector of program arguments which is
 * (argument-name, argument-value) pairs.
 * From that vector, this class populates the related properties fields.
 * The server maintains an object of this class so that everyone has access to common
 * elements like Topology or NodeLocations.
 * 
 */
public class NodeProperties {
	private final Topology topology_;
	private final NodeLocations serverLocations_;
	private final Node node_;
	private String ip_;
	private final int port_;
	private final boolean isGui_;
	
	private int groupId_;
	private int nodeId_;
	private String topologyFile_;
	private String serverLocationFile_;
	private Integer sleep_ = 0; // sleep time in milliseconds
	
	public static class NodePropertiesException extends Exception {
		public NodePropertiesException(String error) {
			super(error);
		}
	}

	public NodeProperties(String[] args, boolean isGui) throws NodePropertiesException {
		nodeId_ = -1;
		groupId_ = -1;
		topologyFile_ = "";
		serverLocationFile_ = "";
		isGui_ = isGui;
		
		try {
			parseCommandLine(args);
		} catch (FlagParser.FlagParseException fe) {
			throw new NodePropertiesException(fe.getMessage());
		}
		
		// Setting current node.
		if (nodeId_ == -1) {
			throw new NodePropertiesException("Branch Id (id) parameter not provided.");
		} else {
			node_ = new Node((isGui_ ? "G" : "S") + String.format("%02d", nodeId_));
		}
		
		
		// Topology.
		try {
			if(topologyFile_.equals("")) {
				throw new NodePropertiesException("No topology file.");
			}			
			topology_ = new Topology(topologyFile_, node_);
		} catch (IOException e) {
			throw new NodePropertiesException(e.getMessage());
		}
		
		
		// Server-Locations.
		try {
			if (serverLocationFile_.equals("")) {
				throw new NodePropertiesException("No server-location file.");
			}
			serverLocations_ = new NodeLocations(serverLocationFile_);
		} catch (IOException e) {
			throw new NodePropertiesException(e.getMessage());
		}
		
		port_ = serverLocations_.getLocationForNode(node_).getPort();
		ip_ = serverLocations_.getLocationForNode(node_).getIp(); 
	}
	
	private void parseCommandLine(String[] args) throws	FlagParser.FlagParseException {
		FlagParser parser = new FlagParser();
		Vector<FlagParser.Argument> parsedArguments = parser.parseFlags(args);
		
		for (int i = 0; i < parsedArguments.size(); ++i) {
			FlagParser.Argument argument = parsedArguments.elementAt(i);
			
			try {
				if (argument.getName().equals("id")) {
					nodeId_ = Integer.parseInt(argument.getValue());
				} else if (argument.getName().equals("topology")) {
					topologyFile_ = argument.getValue();
				} else if (argument.getName().equals("servers")) {
					serverLocationFile_ = argument.getValue();
				} else if (argument.getName().endsWith("sleep")) {
					sleep_ = Integer.parseInt(argument.getValue());
				} else if (argument.getName().endsWith("group")) {
					groupId_ = Integer.parseInt(argument.getValue());
				} else {
					throw new FlagParser.FlagParseException(
							"Unknown flag: " + argument.getName());
				}
			} catch(NumberFormatException ne) {
				throw new FlagParser.FlagParseException(
						"Could not parse integer. " + ne.getMessage());
			} 
		}
	}
	
	public String getIp() {
		return ip_;
	}
	
	public int getPort() {
		return port_;
	}
	
	public int getBranchId() {
		return node_.getBranchId();
	}
	
	public Topology getTopology() {
		return topology_;
	}
		
	public NodeLocations getNodeLocations() {
		return serverLocations_;
	}
	
	public Node getNode() {
		return node_;
	}

	public String print() {
		return node_.toString() + " starting at port:" + port_
		+ " using the topology file:" + topologyFile_
		+ ", servers file: " + serverLocationFile_
		+ " and sleep time of " + sleep_ + " milliseconds.";
	}

	/**
	 * @return time in milliseconds to sleep.
	 */
	public Integer getSleepTime() {
		return sleep_;
	}
}