package branch.server;

import java.util.ArrayList;

public class View {

	String groupId = null;

	ArrayList<Node> listOfServers = null;

	public View(String groupId, Node server) {
		this.groupId = groupId;
		listOfServers = new ArrayList<Node>();
		listOfServers.add(server);
	}

	public void addServer(Node server) {
		listOfServers.add(server);
	}

	/**
	 * @param serverIndex: head index is 0, tail has the last index.
	 */
	public void removeServer(int serverIndex) {
		listOfServers.remove(serverIndex);
	}

	public Node getHead() {
		return listOfServers.get(0);
	}

	public Node getTail() {
		return listOfServers.get(listOfServers.size() - 1);
	}

	public Node getPredecessor(int serverIndex) {
		if (serverIndex == 0)
			return null;
		else
			return listOfServers.get(serverIndex - 1);
	}

	public Node getSuccessor(int serverIndex) {
		if (serverIndex == (listOfServers.size()-1))
			return null;
		else 
			return listOfServers.get(serverIndex + 1);
	}
}
