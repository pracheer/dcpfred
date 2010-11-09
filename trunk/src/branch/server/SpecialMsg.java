package branch.server;

public class SpecialMsg {

	private static String msgSeparator = ";;";
	
	enum Type {
		REGISTER, SYNC, VIEW
	}

	Type type;
	View view;
	Register register;
	Sync sync;
	
	public SpecialMsg(Type type, View view, Register register, Sync sync) {
		super();
		this.type = type;
		this.view = view;
		this.register = register;
		this.sync = sync;
	}

	public String toString() {
		String str = type.toString() + msgSeparator;
		switch (type) {
		case REGISTER:
			str += register.toString();
			break;
		case SYNC:
			str += sync.toString();
			break;
		case VIEW:
			str += view.toString();
		}

		return str;
	}

	public static SpecialMsg parseString(String str) {
		String[] parts = str.split(msgSeparator);
		Type type = Type.valueOf(parts[0]);
		Register register = null;
		Sync sync = null;
		View view = null;

		switch (type) {
		case REGISTER:
			register  = Register.parseString(parts[1]);
			break;
		case SYNC:
			sync = Sync.parseString(parts[1]);
			break;
		case VIEW:
			view = View.parseString(parts[1]);
			break;
		}
		
		return new SpecialMsg(type, view, register, sync);
	}
}
