package caceresenzo.libs.http.exception;

public class HttpRequestException extends Exception {
	private static final long serialVersionUID = 1L;

	public HttpRequestException(String error) {
		super(error);
	}
	
}