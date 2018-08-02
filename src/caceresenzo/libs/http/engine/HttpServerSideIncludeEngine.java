package caceresenzo.libs.http.engine;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import caceresenzo.libs.filesystem.FileUtils;
import caceresenzo.libs.http.HttpServerConfig;
import caceresenzo.libs.http.HttpServerConstants;

public class HttpServerSideIncludeEngine {
	
	public static void deliverDocument(BufferedOutputStream out, File file, HttpServerConfig serverConfig) throws IOException {
		HashSet<File> visited = new HashSet<File>();
		parse(out, visited, file, serverConfig);
		out.flush();
	}
	
	private static void parse(BufferedOutputStream out, HashSet<File> visited, File file, HttpServerConfig serverConfig) throws IOException {
		if (!file.exists() || file.isDirectory()) {
			out.write(("[SSI include not found: " + file.getCanonicalPath() + "]").getBytes());
			return;
		}
		
		if (visited.contains(file)) {
			out.write(("[SSI circular inclusion rejected: " + file.getCanonicalPath() + "]").getBytes());
			return;
		}
		
		visited.add(file);
		
		String extension = FileUtils.getExtension(file);
		
		if (serverConfig.getExtensionRegistry().getSsiExtensions().contains(extension)) { // Process this SSI page line by line
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				int startIndex;
				int endIndex;
				while ((startIndex = line.indexOf("<!--#include file=\"")) >= 0) {
					if ((endIndex = line.indexOf("\" -->", startIndex)) > startIndex) {
						out.write(line.substring(0, startIndex).getBytes());
						String filename = line.substring(startIndex + 19, endIndex);
						parse(out, visited, new File(file.getParentFile(), filename), serverConfig);
						line = line.substring(endIndex + 5, line.length());
					} else {
						out.write(line.substring(0, 19).getBytes());
						line = line.substring(19, line.length());
					}
				}
				out.write(line.getBytes());
				out.write(HttpServerConstants.LINE_SEPARATOR);
			}
			reader.close();
		} else {
			BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = reader.read(buffer, 0, 4096)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			reader.close();
		}
		
		visited.remove(file);
	}
	
}