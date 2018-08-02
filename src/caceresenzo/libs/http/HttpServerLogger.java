package caceresenzo.libs.http;

import java.util.Date;

import caceresenzo.libs.logger.Logger;

public class HttpServerLogger {
	
	public static void log(String ip, String request, int code) {
		Logger.info("[" + new Date().toString() + "] " + ip + " \"" + request + "\" " + code);
	}
	
}