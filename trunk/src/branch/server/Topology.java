package branch.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Maintains the network topology.
 * Gives the isReachable() interface to the current node, so that it knows
 * which servers / GUIs are reachable by it. 
 * Also gives an easy interface for the snapshot generator to know the
 * incoming-channels and outgoing-channels for the current node.
 * This is given by the two methods (inNeighbors) and (outNeighbors). 
 * @author qsh2
 * 
 */

public class Topology {
	public static class Connection {
		String src_;
		String dest_;

		public Connection() {
			src_ = null;
			dest_ = null;
		}

		// Parses string of the form, JVM1 JVM2
		// and updates the connection variables.
		public void parseString(String str) {
			int spaceAt = str.indexOf(' ');

			src_ = str.substring(0, spaceAt);
			dest_ = str.substring(spaceAt + 1);
		}

		public String getSourceString() {
			return src_.toString();
		}

		public String getDestinationString() {
			return dest_.toString();
		}
	}

	final private Vector<Connection> connections_;
	final private Vector<String> inNeighbors_;
	final private Vector<String> outNeighbors_;
	final private String node_;
	final private String group_;

	public Topology(String topologyFileLocation, String node, String group) throws IOException {
		group_ = group;
		FileReader fr = null;
		try {
			fr = new FileReader(new File(topologyFileLocation));
		} catch (FileNotFoundException fe) {
			throw new IOException(fe.getMessage());
		}

		connections_ = new Vector<Connection>();

		BufferedReader in = new BufferedReader(fr);
		String str;
		while ((str = in.readLine())!=null) {
			if(str.startsWith(Constants.COMMENT_START) || str.isEmpty())
				continue;

			Connection c = new Connection();
			c.parseString(str);
			connections_.add(c);
		}

		in.close();
		fr.close();

		node_ = node;
		inNeighbors_ = whoInNeighbors(node);
		outNeighbors_ = whoOutNeighbors(node);
	}

	public boolean isReachable(String dest) {
		String nodeString = node_;
		if (node_.startsWith("G") && (node_.substring(1,3).equals(dest.substring(1, 3))))
			return true;
		if (dest.startsWith("G") && (node_.substring(1,3).equals(dest.substring(1, 3))))
			return true;
		for (int i = 0; i < connections_.size(); ++i) {
			final Connection con = connections_.elementAt(i);

			if (con.getSourceString().equalsIgnoreCase(nodeString) &&
					con.getDestinationString().equalsIgnoreCase(dest)) {
				return true;
			}
		}

		return false;
	}

	private Vector<String> whoOutNeighbors(String src) {
		Vector<String> outNeighbors = new Vector<String>();

		for (int i = 0; i < connections_.size(); ++i) {
			final Connection curr = connections_.elementAt(i);
			if (curr.getSourceString().equals(src) && curr.getDestinationString().charAt(0) != 'G') {
				outNeighbors.add(curr.getDestinationString());
			}
		}
		return outNeighbors;
	}

	private Vector<String> whoInNeighbors(String src) {
		Vector<String> inNeighbors = new Vector<String>();

		for (int i = 0; i < connections_.size(); ++i) {
			final Connection curr = connections_.elementAt(i);
			if (curr.getDestinationString().equals(src) && curr.getSourceString().charAt(0) != 'G') {
				inNeighbors.add(curr.getSourceString());
			}
		}

		return inNeighbors;
	}

	public Vector<String> getInNeighbors() {
		return inNeighbors_;
	}

	public Vector<String> getOutNeighbors() {
		return outNeighbors_;
	}	
}
