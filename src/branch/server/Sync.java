package branch.server;

import java.util.HashMap;
import java.util.Set;

public class Sync {

	private static String msgSeparator = ":::";
	
	String src;
	String dest;
	HashMap<String, Double> accountDetails;
	HashMap<String, Trxn> trxnLog;
	
	public Sync(String src, String dest,
			HashMap<String, Double> accountDetails,
			HashMap<String, Trxn> trxnLog) {
		super();
		this.src = src;
		this.dest = dest;
		this.accountDetails = accountDetails;
		this.trxnLog = trxnLog;
	}
	
	public String toString() {
		
		String str = src + msgSeparator + dest + msgSeparator;
		
		str += msgSeparator + accountDetails.size();
		Set<String> keys = accountDetails.keySet();
		for (String key : keys) {
			str += msgSeparator + key + msgSeparator + accountDetails.get(key);
		}
		
		str += msgSeparator + trxnLog.size();
		keys = trxnLog.keySet();
		for (String key : keys) {
			str += msgSeparator + key + msgSeparator + trxnLog.get(key);
		}
		
		return str;
	}
	
	public static Sync parseString(String str) {
		String[] parts = str.split(msgSeparator);
		
		int index = 0;
		
		String src = parts[index++];
		String dest = parts[index++];
		
		int accountCount = Integer.parseInt(parts[index++]);
		HashMap<String, Double> accountDetails = new HashMap<String, Double>(accountCount);
		for (int i = 0; i < accountCount; i++) {
			String account = parts[index++];
			Double balance = Double.parseDouble(parts[index++]);
			accountDetails.put(account, balance);
		}
		
		int logCount = Integer.parseInt(parts[index++]);
		HashMap<String, Trxn> trxnLog = new HashMap<String, Trxn>(logCount);
		for (int i = 0; i < logCount; i++) {
			String account = parts[index++];
			Trxn trxn = Trxn.parseString(parts[index++]);
			trxnLog.put(account, trxn);
		}
		
		return new Sync(src, dest, accountDetails, trxnLog);
	}
}
