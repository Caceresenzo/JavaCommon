package caceresenzo.libs.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import caceresenzo.libs.logger.Logger;

public class SimpleTestUnits {
	
	public static void initializeUnit() {
		Logger.setClassOnly(true);
		Logger.setStaticLength(20);
	}
	
	public static void noLogFile() {
		Logger.setExceptionFileReport(false);
	}
	
	public static void redirectConsoleOutput() {
		try {
			System.setOut(new PrintStream(new FileOutputStream(new File("info.log")), true, "UTF-8"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}