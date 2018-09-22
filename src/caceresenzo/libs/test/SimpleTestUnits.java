package caceresenzo.libs.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import caceresenzo.libs.logger.Logger;

public class SimpleTestUnits {
	
	public static void initializeUnit() {
		initializeUnit(true);
	}
	
	public static void initializeUnit(boolean withLogFile) {
		Logger.setClassOnly(true);
		Logger.setStaticLength(20);
		
		logFile(withLogFile);
	}
	
	public static void noLogFile() {
		logFile(false);
	}
	
	public static void logFile(boolean enable) {
		Logger.setExceptionFileReport(enable);
	}
	
	public static void redirectConsoleOutput() {
		redirectConsoleOutput("info.log");
	}
	
	public static void redirectConsoleOutput(String file) {
		try {
			System.setOut(new PrintStream(new FileOutputStream(new File(file)), true, "UTF-8"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public static void $(Object object) {
		System.out.println(object);
	}
	
}