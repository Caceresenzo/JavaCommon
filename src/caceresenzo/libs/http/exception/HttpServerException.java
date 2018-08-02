package caceresenzo.libs.http.exception;

public class HttpServerException extends Exception {
	private static final long serialVersionUID = 1L;

	public HttpServerException(String error) {
		super(error);
	}
	
}