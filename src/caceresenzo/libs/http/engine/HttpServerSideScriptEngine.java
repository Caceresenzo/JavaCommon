package caceresenzo.libs.http.engine;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Provides limited support for running server side scripts. The HashMap of server variables are sent to the process when it is executed. While the process is outputting data to standard output, this will be issued to the connecting client.
 */
public class HttpServerSideScriptEngine {
	
	// This could be a lot better. Consider server side scripting a beta feature for now.
	public static void execute(BufferedOutputStream out, HashMap<?, ?> serverVariables, File file, String path) throws Throwable {
		// Place server variables into a String array.
		String[] environementParameters = new String[serverVariables.size()];
		Iterator<?> variableIterator = serverVariables.keySet().iterator();
		for (int i = 0; i < serverVariables.size(); i++) {
			String key = (String) variableIterator.next();
			String value = (String) serverVariables.get(key);
			environementParameters[i] = key + "=" + value;
		}
		
		// Execute the external command
		String filename = file.toString();
		String[] cmdarray = null;
		
		if (filename.toLowerCase().endsWith(".pl")) {
			cmdarray = new String[] { "perl", filename };
		} else if (filename.toLowerCase().endsWith(".php")) {
			cmdarray = new String[] { "php", filename };
		} else {
			cmdarray = new String[] { filename };
		}
		Process process = Runtime.getRuntime().exec(cmdarray, environementParameters, file.getParentFile());
		
		// Send the process output to the connecting client.
		InputStream in = process.getInputStream();
		byte[] buffer = new byte[4096];
		int bytesRead;
		while ((bytesRead = in.read(buffer, 0, 4096)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		
		in.close();
	}
	
}