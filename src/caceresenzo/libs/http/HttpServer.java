package caceresenzo.libs.http;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import caceresenzo.libs.http.exception.HttpServerException;
import caceresenzo.libs.logger.Logger;

public class HttpServer {
	
	public static final String LINE_SEPARATOR = "\r\n";
	
	private File rootDirectory;
	private int port;
	private HttpServerConfig serverConfig;
	private ServerMode mode;
	
	private boolean active = true;
	
	public HttpServer(String rootDirectory, int port, HttpServerConfig config, ServerMode mode) throws HttpServerException {
		this.port = port;
		this.serverConfig = config;
		this.mode = mode;
		
		if (rootDirectory != null) {
			try {
				this.rootDirectory = new File(rootDirectory).getCanonicalFile();
			} catch (IOException exception) {
				throw new HttpServerException("Unable to determine the canonical path of the web root directory.");
			}
			
			if (!this.rootDirectory.isDirectory()) {
				throw new HttpServerException("The specified root directory does not exist or is not a directory.");
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void activate() throws HttpServerException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception exception) {
			throw new HttpServerException("Cannot start the web server on port " + port + ".");
		}
		
		Logger.info("WebServer started on port: " + port);
		
		ThreadGroup threadGroup = new ThreadGroup("HTTP Request Thread Group");
		while (active) {
			try {
				Socket socket = serverSocket.accept();
				HttpRequestThread requestThread = new HttpRequestThread(this, socket, rootDirectory);
				Thread thread = new Thread(threadGroup, requestThread);
				thread.start();
			} catch (Exception exception) {
				throw new HttpServerException("Error processing new connection: " + exception);
			}
		}
		
		try {
			serverSocket.close();
		} catch (IOException exception) {
			throw new HttpServerException("Cannot close the web server on port " + port + ", cause: " + exception.getLocalizedMessage());
		}
	}
	
	public File getRootDirectory() {
		return rootDirectory;
	}
	
	public void setRootDirectory(File rootDirectory) {
		this.rootDirectory = rootDirectory;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public HttpServerConfig getServerConfig() {
		return serverConfig;
	}
	
	public void setServerConfig(HttpServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
	
	public ServerMode getMode() {
		return mode;
	}
	
	public void setMode(ServerMode mode) {
		this.mode = mode;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public static enum ServerMode {
		EXPLORER(true, false), //
		HOOKS(false, true), //
		FULL(true, true); //
		
		private final boolean allowExplorer, allowHooks;
		
		private ServerMode(boolean allowExplorer, boolean allowHooks) {
			this.allowExplorer = allowExplorer;
			this.allowHooks = allowHooks;
		}
		
		public boolean isExplorerAllowed() {
			return allowExplorer;
		}
		
		public boolean isHooksAllowed() {
			return allowHooks;
		}
		
		@Override
		public String toString() {
			return "ServerMode[allowExplorer=" + allowExplorer + ", allowHooks=" + allowHooks + "]";
		}
	}
	
}