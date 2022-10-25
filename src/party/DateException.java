package party;

public class DateException extends Exception {
	public DateException(String msg) {
		super(msg);
	}
	public DateException() {
		super ("DateException: faild to insert the date properly");
	}

}
