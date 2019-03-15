package caceresenzo.libs.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConsoleUtils {
	
	/* Static */
	public static final InputStream CONSOLE_IN;
	public static final BufferedReader INPUT_READER;
	
	static {
		CONSOLE_IN = System.in;
		INPUT_READER = new BufferedReader(new InputStreamReader(CONSOLE_IN));
	}
	
	/**
	 * Same as {@link #waitForInput(Object)} but use a null prefix, causing it to not be used.
	 * 
	 * @return Readed input from the console.
	 * @throws IOException
	 *             If an I/O error occurs.
	 * @see #waitForInput(Object)
	 */
	public static String waitForInput() throws IOException {
		return waitForInput(null);
	}
	
	/**
	 * Same as {@link #waitForInput(Object)} but use a null prefix, causing it to not be used.
	 * 
	 * @param prefix
	 *            Prefix to use that will be written in the console before asking for an input.
	 * @return Readed input from the console.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public static String waitForInput(Object prefix) throws IOException {
		if (prefix != null) {
			System.out.print(String.valueOf(prefix));
		}
		
		return INPUT_READER.readLine();
	}
	
}