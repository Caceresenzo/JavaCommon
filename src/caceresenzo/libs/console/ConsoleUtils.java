package caceresenzo.libs.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUtils {
	
	private static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
	
	public static String waitForInput(Object prefix) throws IOException {
		System.out.print(String.valueOf(prefix));
		return inputReader.readLine();
	}
	
}