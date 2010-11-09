package branch.server;

import java.util.ArrayList;

public class View {

	String groupId = null;

	ArrayList<String> listOfServers = null;

	public View(String groupId, String server) {
		this.groupId = groupId;
		listOfServers = new ArrayList<String>();
		listOfServers.add(server);
	}

	public void addServer(String server) {
		listOfServers.add(server);
	}

	/**
	 * @param serverIndex: head index is 0, tail has the last index.
	 */
	public void removeServer(int serverIndex) {
		listOfServers.remove(serverIndex);
	}

	public String getHead() {
		return listOfServers.get(0);
	}

	public String getTail() {
		return listOfServers.get(listOfServers.size() - 1);
	}

	public String getPredecessor(int serverIndex) {
		if (serverIndex == 0)
			return null;
		else
			return listOfServers.get(serverIndex - 1);
	}

	public String getSuccessor(int serverIndex) {
		if (serverIndex == (listOfServers.size()-1))
			return null;
		else 
			return listOfServers.get(serverIndex + 1);
	}
}
