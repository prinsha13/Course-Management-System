package management;

public class InvalidEmail extends Exception {

	private static final long serialVersionUID = -8145316108548232390L;

	public InvalidEmail(String message) {
		super(message);
	}
}