package caceresenzo.libs.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import caceresenzo.libs.filesystem.FileUtils;
import caceresenzo.libs.http.engine.HttpServerSideIncludeEngine;
import caceresenzo.libs.http.engine.HttpServerSideScriptEngine;
import caceresenzo.libs.http.exception.HttpRequestException;
import caceresenzo.libs.string.SimpleLineStringBuilder;

public class HttpRequest {
	
	private HashMap<String, String> headers;
	
	private HttpServer server;
	private Socket socket;
	
	private String ip = "unknown";
	private String request = "unknown";
	private int bytesSent = 0;
	
	private BufferedReader in;
	private BufferedOutputStream out;
	
	private int anwserCode = 200;
	private String anwserContent = "";
	private String answerMime = "";
	private List<String> anwserCustomAddToHeader = new ArrayList<String>();
	
	public HttpRequest(HttpServer server, Socket socket) throws IOException {
		this.headers = new HashMap<String, String>();
		
		this.server = server;
		this.socket = socket;
		
		// Handle Streams
		this.ip = socket.getInetAddress().getHostAddress();
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new BufferedOutputStream(socket.getOutputStream());
		
		request = in.readLine();
		
		// Read headers
		String line = null;
		while ((line = this.in.readLine()) != null) {
			line = line.trim();
			if (line.equals("")) {
				break;
			}
			int colonPos = line.indexOf(":");
			if (colonPos > 0) {
				String key = line.substring(0, colonPos);
				String value = line.substring(colonPos + 1);
				headers.put(key, value.trim());
			}
		}
	}
	
	public String getRequestedPath() throws IOException, HttpRequestException {
		if (request != null && request.startsWith("GET ") && (request.endsWith(" HTTP/1.0") || request.endsWith("HTTP/1.1"))) {
			return request.substring(4, request.length() - 9);
		} else { // Invalid request type (no "GET")
			HttpServerLogger.log(ip, request, 405);
			socket.close();
			throw new HttpRequestException("Invalid request: 405");
		}
	}
	
	public HttpRequest error(String content) {
		return output(ResponseCode.$500.getCode(), content, HttpExtensionRegistry.ExtensionMime.HTML.getMime());
	}
	
	public HttpRequest error(int code, String content) {
		return output(code, content, HttpExtensionRegistry.ExtensionMime.HTML.getMime());
	}
	
	public HttpRequest output(String content) {
		return output(ResponseCode.$200.getCode(), content, HttpExtensionRegistry.ExtensionMime.HTML.getMime());
	}
	
	public HttpRequest output(int code) {
		return output(code, "", HttpExtensionRegistry.ExtensionMime.OCTETSTREAM.getMime());
	}
	
	public HttpRequest output(String content, String mime) {
		return output(ResponseCode.$200.getCode(), content, mime);
	}
	
	public HttpRequest output(int code, String content, String mime) {
		this.anwserCode = code;
		this.anwserContent = content;
		this.answerMime = mime;
		
		return this;
	}
	
	public HttpRequest executeServerSideScriptEngine(File file, String path) throws Throwable {
		out.write(("HTTP/1.0 200 OK" + HttpServer.LINE_SEPARATOR).getBytes());
		HttpServerSideScriptEngine.execute(out, headers, file, path);
		return this;
	}
	
	public HttpRequest addToHeader(String key, Object value) {
		if (key == null || value == null) {
			return this;
		}
		
		anwserCustomAddToHeader.add(key + ": " + String.valueOf(value));
		
		return this;
	}
	
	private SimpleLineStringBuilder getHeaderFormatted() {
		SimpleLineStringBuilder builder = new SimpleLineStringBuilder("", HttpServer.LINE_SEPARATOR);
		
		builder.appendln("HTTP/1.0 " + anwserCode + " " + ResponseCode.get(anwserCode).getMessage());
		builder.appendln("Content-Type: " + answerMime);
		builder.appendln("Server: " + "Enzo CACERES Java Simple Web Server");
		
		if (!anwserCustomAddToHeader.isEmpty()) {
			for (String header : anwserCustomAddToHeader) {
				if (header.toLowerCase().startsWith("content-type: ") || header.toLowerCase().startsWith("server: ")) {
					continue;
				}
				
				builder.appendln(header);
			}
		}
		
		builder.appendln();
		
		return builder;
	}
	
	public void send() throws IOException {
		SimpleLineStringBuilder builder = getHeaderFormatted();
		
		if (anwserContent == null || anwserContent.equals("")) {
			builder.appendln("<h1>" + ResponseCode.get(anwserCode, ResponseCode.$0).getMessage() + "</h1>");
		} else {
			builder.appendln(anwserContent);
		}
		
		if (ResponseCode.get(anwserCode, ResponseCode.$0).mustIncludeVersion()) {
			builder.appendln("<hr>" + "<i>" + HttpServerConstants.VERSION + "</i>");
		}
		
		out.write(builder.toString().getBytes());
		
		close();
	}
	
	public void sendFile(File file) throws IOException {
		String extension = FileUtils.getExtension(file);
		
		String contentType = (String) server.getServerConfig().getExtensionRegistry().getMimeExtensions().get(extension);
		if (contentType == null) {
			contentType = HttpExtensionRegistry.ExtensionMime.OCTETSTREAM.getMime();
		}
		
		addToHeader("Content-Length", file.length());
		addToHeader("Date", new Date().toString());
		addToHeader("Last-modified", new Date(file.lastModified()).toString());
		
		out.write(getHeaderFormatted().toString().getBytes());
		
		if (server.getServerConfig().getExtensionRegistry().getSsiExtensions().contains(extension)) {
			HttpServerSideIncludeEngine.deliverDocument(out, file, server.getServerConfig());
			close();
			return;
		}
		
		try {
			BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
			
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesRead;
			while ((bytesRead = reader.read(buffer, 0, bufferSize)) != -1) {
				out.write(buffer, 0, bytesRead);
				bytesSent += bytesRead;
			}
			
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception exception) {
					; // Do nothing.
				}
			}
		} catch (IOException exception) {
			throw new IOException(exception);
		}
		
		close();
	}
	
	public void close() throws IOException {
		out.flush();
		socket.close();
	}
	
	public HashMap<String, String> getHeaders() {
		return headers;
	}
	
	public String getIp() {
		return ip;
	}
	
	public String getRequest() {
		return request;
	}
	
	public int getBytesSent() {
		return bytesSent;
	}
	
	public BufferedReader getIn() {
		return in;
	}
	
	public BufferedOutputStream getOut() {
		return out;
	}
	
	public static enum ResponseCode {
		$0("Default *empty* Server Response", true), //
		$200("Success", false), //
		$301("Moved Permanently", true), //
		$401("Unauthorized", true), //
		$403("Forbidden", true), //
		$404("Not found", true), //
		$405("Method Not Allowed", true), //
		$500("Internal Server Error", true); //
		
		private final String message;
		private final boolean includeVersion;
		
		private ResponseCode(String message, boolean includeVersion) {
			this.message = message;
			this.includeVersion = includeVersion;
		}
		
		public int getCode() {
			return Integer.parseInt(this.toString().replace("$", ""));
		}
		
		public String getMessage() {
			return message;
		}
		
		public boolean mustIncludeVersion() {
			return includeVersion;
		}
		
		public static ResponseCode get(int code, ResponseCode defaultCode) {
			ResponseCode responseCode = ResponseCode.valueOf("$" + code);
			
			if (responseCode == null) {
				return defaultCode;
			}
			
			return responseCode;
		}
		
		public static ResponseCode get(int code) {
			return get(code, ResponseCode.$500);
		}
	}
	
}
