package caceresenzo.libs.os.exception;

@SuppressWarnings("serial")
public class UnsupportedOSActionException extends Exception {
	
	public UnsupportedOSActionException(String message) {
		super(message);
	}
	
	public UnsupportedOSActionException(Throwable cause) {
		super(cause);
	}
	
}