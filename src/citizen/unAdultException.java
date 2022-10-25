package citizen;

public class unAdultException extends Exception {
	public unAdultException(String msg) {
		super(msg);
	}
	public unAdultException() {
		super ("we're sorry, this person cannot vote in this election");
	}

}
