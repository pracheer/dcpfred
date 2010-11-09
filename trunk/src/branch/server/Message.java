package branch.server;

// Message class.
// The communication between two servers, and a server and GUI is always
// using this type.
// We use the toString class to get a string to send over the network.
// In the receiving end we use parseString to get back the Message object.
public class Message {
	public static enum MsgType {
		REQ, RESP, SPECIAL
	}

	String srcNode_;
	MsgType type_;

	// Only relevant for REQ type message.
	Trxn trxn_ = null;

	// Only relevant for response type message.
	TrxnResponse trxnResponse_ = null;

	// Only relevant for Special message.
	SpecialMsg specialMsg = null;

	public static Message parseString(String str) {
		return new Message(str);
	}

	public Message(String sourceNode, MsgType type,
			Trxn trxn, TrxnResponse trxnResp) {
		this.srcNode_ = sourceNode;
		this.type_ = type;
		this.trxn_ = trxn;
		this.trxnResponse_ = trxnResp;
	}

	/**
	 * @param str
	 * Receives an 'str' and generates a Message object.
	 * Make sure the String was generated by the toString method for consistency.
	 */
	protected Message(String str) {
		System.out.println("Received a message:" + str);

		int index1 = str.indexOf(Trxn.msgSeparator);

		int index2 = str.indexOf(Trxn.msgSeparator, index1
				+ Trxn.msgSeparator.length());

		type_ = MsgType.valueOf(str.substring(0, index1));

		srcNode_ = str.substring(index1 + Trxn.msgSeparator.length(), index2);

		switch (type_) {
		case REQ:
			trxn_ = Trxn.parseString(str.substring(index2
					+ Trxn.msgSeparator.length()));
			break;
		case RESP:
			trxnResponse_ = TrxnResponse.parseString(str
					.substring(index2 + Trxn.msgSeparator.length()));
			break;			
		case SPECIAL:
			specialMsg = SpecialMsg.parseString(str.substring(index2
					+ Trxn.msgSeparator.length()));
		}
	}

	// Creates String representation of message object.
	public String toString() {
		String str = type_
		+ Trxn.msgSeparator
		+ srcNode_
		+ Trxn.msgSeparator;
		switch(type_) {
		case REQ:
			str += trxn_.toString();
			break;
		case RESP:
			str += trxnResponse_.toString();
			break;
		case SPECIAL:
			str += specialMsg.toString();
		}
		
		return str;
	}

	/**
	 * @return the sourceName_
	 */
	public String getSrcNode() {
		return srcNode_;
	}

	/**
	 * @return the type_
	 */
	public MsgType getType() {
		return type_;
	}

	/**
	 * @return transaction_
	 */
	public Trxn getTrxn() {
		return trxn_;
	}

	/**
	 * @return trxnResponse_
	 */
	public TrxnResponse getTrxnResponse() {
		return trxnResponse_;
	}

	public SpecialMsg getSpecialMsg() {
		return specialMsg;
	}
	
	/**
	 * Returns a pretty-string for showing in the GUI to the user.
	 * This is used to show the messages in the channel-state.
	 * @return String
	 */
	public String getPrettyString() {
		if (type_ == MsgType.REQ) {
			return trxn_.getPrettyString();
		} else {
			return "";
		}
	}
}
