package caceresenzo.libs.filesystem;

import java.io.File;
import java.io.IOException;

import caceresenzo.libs.string.StringUtils;

public class FileChecker {
	
	public static String checkFile(File file, String extension, boolean tryReading, boolean ignoreEmptyData) throws Exception {
		if (file == null || !file.exists() || file.isDirectory() || !file.canRead()) {
			throw new IOException("File (" + file.getAbsolutePath() + ") is not accessible.");
		}
		
		if (extension != null && !FileUtils.getExtension(file).toLowerCase().equals("." + extension.toLowerCase())) {
			throw new Exception("File not a '" + extension.toLowerCase() + "' file.");
		}
		
		if (tryReading) {
			String data = StringUtils.fromFile(file);
			
			if (!ignoreEmptyData && (data == null || data.isEmpty())) {
				throw new Exception("File is empty or not readable.");
			}
			
			return data;
		}
		
		return null;
	}
	
	public static String checkFile(File file, String extension, boolean tryReading) throws Exception {
		return checkFile(file, extension, tryReading, true);
	}
	
	public static String checkFileAndRead(File file, String extention) throws Exception {
		return checkFile(file, extention, true, true);
	}
	
	public static boolean checkFile(File file) {
		return checkFile(file, false, false);
	}
	
	public static boolean checkFile(File file, boolean tryReading) {
		return checkFile(file, tryReading, false);
	}
	
	public static boolean checkFile(File file, boolean tryReading, boolean ignoreEmptyData) {
		try {
			checkFile(file, null, false);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}
	
}