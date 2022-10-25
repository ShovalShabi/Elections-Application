package citizen;

public class MisMatchObjectException extends Exception {
	public MisMatchObjectException(String msg) {
		super(msg);
	}

	public MisMatchObjectException() {
		super("The object is not matching this data base");
	}

}
