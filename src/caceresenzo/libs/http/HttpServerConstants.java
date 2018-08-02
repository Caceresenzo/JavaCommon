package caceresenzo.libs.http;

public class HttpServerConstants {
	
	public static final String VERSION = "<a href=\"javascript:void()\">Enzo CACERES's Web Server 1.0</a><br>An extremely small & simple Java web server";
	
	public static final String DEFAULT_ROOT_DIRECTORY = ".";
	public static final int DEFAULT_PORT = 80;
	
	public static final String[] DEFAULT_FILES = new String[] { "index.html", "index.htm", "index.shtml", "index.shtm", "index.stm", "index.sht" };
	
	public static final byte[] LINE_SEPARATOR = "\r\n".getBytes();
	
}