package caceresenzo.libs.os.exception;

@SuppressWarnings("serial")
public class InvalidOSException extends Exception {
	
	public InvalidOSException(String message) {
		super(message);
	}
	
	public InvalidOSException(Throwable cause) {
		super(cause);
	}
	
}