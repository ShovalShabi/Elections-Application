package citizen;

public class IdException extends Exception {

	public IdException(String msg) {
		super(msg);
	}

	public IdException() {
		super("ID number must contain 9 figures");
	}
}
