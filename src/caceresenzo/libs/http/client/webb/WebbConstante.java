package caceresenzo.libs.http.client.webb;

/**
 * Constant values and strings.
 */
public class WebbConstante {
	public static final String DEFAULT_USER_AGENT = "com.goebl.david.Webb/1.0";
	public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
	public static final String APPLICATION_JSON = "application/json";
	public static final String APPLICATION_BINARY = "application/octet-stream";
	public static final String TEXT_PLAIN = "text/plain";
	public static final String HDR_CONTENT_TYPE = "Content-Type";
	public static final String HDR_CONTENT_ENCODING = "Content-Encoding";
	public static final String HDR_ACCEPT_ENCODING = "Accept-Encoding";
	public static final String HDR_ACCEPT = "Accept";
	public static final String HDR_USER_AGENT = "User-Agent";
	public static final String UTF8 = "utf-8";
	
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	public static final Class<? extends byte[]> BYTE_ARRAY_CLASS = EMPTY_BYTE_ARRAY.getClass();
	/** Minimal number of bytes the compressed content must be smaller than uncompressed */
	public static final int MIN_COMPRESSED_ADVANTAGE = 80;
}