package caceresenzo.libs.string;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import caceresenzo.libs.empty.EmptyUtils;
import caceresenzo.libs.stream.StreamUtils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
	/**
	 * A simple way to write string to file.
	 */
	public static void stringToFile(String filePath, String content) throws IOException {
		stringToFile(new File(filePath), content);
	}
	
	/**
	 * A simple way to write string to file.
	 */
	public static void stringToFile(File file, String content) throws FileNotFoundException, IOException {
		stringToStream(content, new FileOutputStream(file));
	}
	
	/**
	 * Simple way to make string into stream
	 */
	public static void stringToStream(String content, OutputStream fout) throws IOException {
		DataOutputStream dataout = new DataOutputStream(fout);
		byte[] data = content.getBytes("UTF-8");
		dataout.write(data);
		// fout.flush();
		// fout.close();
		dataout.flush();
		dataout.close();
		
	}
	
	/**
	 * Load string from file
	 */
	public static String fromFile(String path) throws IOException {
		File file = new File(path);
		if (file.exists() == false)
			return null;
		
		return fromFile(file);
	}
	
	/**
	 * A simple way to load string from file
	 */
	public static String fromFile(File file) throws IOException {
		return fromStream(new FileInputStream(file));
	}
	
	/**
	 * A simple load string from stream
	 */
	public static String fromStream(InputStream inputStream) throws IOException {
		return StreamUtils.InputStreamToString(inputStream);
	}
	
	public static String asHex(byte buffer[]) {
		StringBuffer stringBuffer = new StringBuffer(buffer.length * 2);
		int i;
		
		for (i = 0; i < buffer.length; i++) {
			if ((buffer[i] & 0xff) < 0x10) {
				stringBuffer.append("0");
			}
			
			stringBuffer.append(Long.toString(buffer[i] & 0xff, 16));
		}
		
		return stringBuffer.toString();
	}
	
	public static String fromException(Exception exception) {
		StringWriter stringWriter = new StringWriter();
		exception.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
	
	public static String fromException(Throwable throwable) {
		StringWriter stringWriter = new StringWriter();
		throwable.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
	
	public static String cutIfTooLong(String source, int maxLength) {
		if (source == null) {
			return "";
		}
		if (source.length() > maxLength) {
			return source.substring(0, maxLength);
		}
		return source;
	}
	
	public static String multiplySequence(String sequence, int times) {
		if (times <= 0) {
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < times; i++) {
			builder.append(sequence);
		}
		
		return builder.toString();
	}
	
	public static boolean validate(String... strings) {
		for (String string : strings) {
			if (string == null || string.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	public static String join(List<?> list, String delimiter) {
		if (!EmptyUtils.validate(list)) {
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		
		Iterator<?> iterator = list.iterator();
		
		while (iterator.hasNext()) {
			builder.append(String.valueOf(iterator.next())).append(iterator.hasNext() ? delimiter : "");
		}
		
		return builder.toString();
	}
	
}