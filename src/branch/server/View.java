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
}
