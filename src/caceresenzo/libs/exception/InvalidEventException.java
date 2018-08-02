package caceresenzo.libs.exception;

@SuppressWarnings("serial")
public class InvalidEventException extends Exception {
	
	public InvalidEventException() {
		super();
	}
	
	public InvalidEventException(String message) {
		super(message);
	}
	
	public InvalidEventException(Throwable cause) {
		super(cause);
	}
	
}