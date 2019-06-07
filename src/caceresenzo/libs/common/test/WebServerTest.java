package caceresenzo.libs.common.test;

import caceresenzo.libs.http.HttpExtensionRegistry;
import caceresenzo.libs.http.HttpServer;
import caceresenzo.libs.http.HttpServerConfig;
import caceresenzo.libs.http.HttpServerConstants;
import caceresenzo.libs.http.HttpServer.ServerMode;
import caceresenzo.libs.http.exception.HttpHookRegistry;
import caceresenzo.libs.http.exception.HttpServerException;

public class WebServerTest {
	
	public static void main(String[] args) throws HttpServerException {
		
		final String rootDir = "C:\\Users\\cacer\\OneDrive\\Developments\\Workspace"; //HttpServerConstants.DEFAULT_ROOT_DIRECTORY;
		final int port = 85;
		
		// final HttpServer server = new HttpServer("C:\\Users\\cacer\\Videos", port);
		HttpServerConfig config = new HttpServerConfig(new HttpExtensionRegistry(true), new HttpHookRegistry());
		final HttpServer server = new HttpServer(rootDir, port, config, ServerMode.FULL);
		
		Thread webServer = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					server.activate();
				} catch (HttpServerException exception) {
					exception.printStackTrace();
				}
				
			}
		});
		
		webServer.start();
		
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// ThreadUtils.sleep(30000L);
		// Logger.error("EXITING");
		// System.exit(0);
		// }
		// }).start();
	}
	
}