package caceresenzo.libs.exception;

@SuppressWarnings("serial")
public class MissingInformationException extends Exception {
	
	public MissingInformationException() {
		super();
	}
	
	public MissingInformationException(String message) {
		super(message);
	}
	
	public MissingInformationException(Throwable cause) {
		super(cause);
	}
	
}