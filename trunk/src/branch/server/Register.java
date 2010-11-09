package branch.server;

import java.util.ArrayList;

public class Register {
	String name;
	String groupId;
	ArrayList<String> outgoingChannels;
	
	public Register(String name, String groupId, ArrayList<String> outgoingChannels) {
		this.name = name;
		this.groupId = groupId;
		this.outgoingChannels = outgoingChannels;
	}
	
	public String toString() {
		String str = name + Trxn.msgSeparator + groupId + Trxn.msgSeparator;
		
		for (String channel : outgoingChannels) {
			str += Trxn.msgSeparator + channel;
		}
		
		return str;
	}
	
	public static Register parseString(String str) {
		String[] parts = str.split(Trxn.msgSeparator);
		ArrayList<String> channels = new ArrayList<String>();
		for(int i = 2; i < parts.length; i++) {
			channels.add(parts[i]);
		}
		return new Register(parts[0], parts[1], channels);
	}
}

