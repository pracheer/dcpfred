package branch.server;

import java.util.ArrayList;

public class View {

	String groupId = null;

	ArrayList<String> listOfServers = null;

	public View(String groupId) {
		this.groupId = groupId;
		listOfServers = new ArrayList<String>();
	}

	public void addServer(String server) {
		listOfServers.add(server);
	}

	public boolean removeServer(String server) {
		return listOfServers.remove(server);
	}

	public String getHead() {
		return listOfServers.get(0);
	}

	public String getTail() {
		return listOfServers.get(listOfServers.size() - 1);
	}

	public String getPredecessor(String server) {
		int index = listOfServers.indexOf(server);
		if(index == 0) {
			return null;
		}
		else 
			return listOfServers.get(index-1);
	}

	public String getSuccessor(String server) {
		int index = listOfServers.indexOf(server);
		if(index == listOfServers.size() -1)
			return null;
		else 
			return listOfServers.get(index + 1);
	}
	
	public String toString() {
		String str = groupId;
		for(int i = 0; i < listOfServers.size(); i++)
		{
			str += Trxn.msgSeparator + listOfServers.get(i);
		}
		
		return str;
	}
	
	public static View parseString(String str) {
		String[] parts = str.split(Trxn.msgSeparator);
		View view = new View(parts[0]);
		for ( int i = 1; i < parts.length; i++) {
			view.addServer(parts[i]);
		}
		return view;
	}
}
