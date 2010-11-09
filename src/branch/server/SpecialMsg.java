package branch.server;

public class SpecialMsg {

	private static String msgSeparator = ";;";
	
	enum Type {
		REGISTER, SYNC, VIEW
	}

	Type type_;
	View view_;
	Register register_;
	Sync sync_;
	
	public SpecialMsg(Type type, View view, Register register, Sync sync) {
		super();
		this.type_ = type;
		this.view_ = view;
		this.register_ = register;
		this.sync_ = sync;
	}
	
	public SpecialMsg(View view) {
		view_ = view;
		type_ = Type.VIEW;
		sync_ = null;
		register_ = null;
	}
	
	public SpecialMsg(Sync sync) {
		sync_ = sync;
		type_ = Type.SYNC;
		view_ = null;
		register_ = null;
	}
		
	public SpecialMsg.Type getType() {
		return type_;
	}
	
	public View getView() {
		return view_;
	}
	
	public Sync getSync() {
		return sync_;
	}

	public String toString() {
		String str = type_.toString() + msgSeparator;
		switch (type_) {
		case REGISTER:
			str += register_.toString();
			break;
		case SYNC:
			str += sync_.toString();
			break;
		case VIEW:
			str += view_.toString();
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
