package caceresenzo.libs.parse;

import java.util.ArrayList;
import java.util.List;

public class ParseUtils {
	
	public static int parseInt(Object object, int defaultValue, ParseExceptionCustomCode parseExceptionCustomCode) {
		try {
			return Integer.parseInt(String.valueOf(object));
		} catch (NumberFormatException exception) {
			if (parseExceptionCustomCode != null) {
				parseExceptionCustomCode.errorAppend(exception);
			}
			return defaultValue;
		}
	}
	
	public static float parseFloat(Object object, float defaultValue, ParseExceptionCustomCode parseExceptionCustomCode) {
		try {
			return Float.parseFloat(String.valueOf(object));
		} catch (NumberFormatException exception) {
			if (parseExceptionCustomCode != null) {
				parseExceptionCustomCode.errorAppend(exception);
			}
			return defaultValue;
		}
	}
	
	public static long parseLong(Object object, long defaultValue, ParseExceptionCustomCode parseExceptionCustomCode) {
		try {
			return Long.parseLong(String.valueOf(object));
		} catch (NumberFormatException exception) {
			if (parseExceptionCustomCode != null) {
				parseExceptionCustomCode.errorAppend(exception);
			}
			return defaultValue;
		}
	}
	
	public static boolean parseBoolean(Object object, boolean defaultValue, ParseExceptionCustomCode parseExceptionCustomCode) {
		if (String.valueOf(object).equalsIgnoreCase("TRUE") || String.valueOf(object).equalsIgnoreCase("FALSE")) {
			return Boolean.valueOf(object.toString());
		}
		if (parseExceptionCustomCode != null) {
			parseExceptionCustomCode.errorAppend(new Exception("Invalid Boolean."));
		}
		return defaultValue;
	}
	
	public static String parseString(Object object, String defaultValue, ParseExceptionCustomCode parseExceptionCustomCode, boolean allowEmpty) {
		String parse = String.valueOf(object);
		if ((parse == null || object == null) || (!allowEmpty && parse.isEmpty())) {
			return defaultValue;
		}
		return parse;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> parseList(Object object, List<T> defaultValue, ParseExceptionCustomCode parseExceptionCustomCode) {
		List<T> list = new ArrayList<T>();
		try {
			for (T t : (List<T>) object) {
				list.add(t);
			}
			return list;
		} catch (Exception exception) {
			if (parseExceptionCustomCode != null) {
				parseExceptionCustomCode.errorAppend(exception);
			}
		}
		return defaultValue;
	}
	
	public static int parseInt(Object object, int defaultValue) {
		return parseInt(object, defaultValue, null);
	}
	
	public static float parseFloat(Object object, float defaultValue) {
		return parseFloat(object, defaultValue, null);
	}
	
	public static long parseLong(Object object, long defaultValue) {
		return parseLong(object, defaultValue, null);
	}
	
	public static boolean parseBoolean(Object object, boolean defaultValue) {
		return parseBoolean(object, defaultValue, null);
	}
	
	public static String parseString(Object object, String defaultValue) {
		return parseString(object, defaultValue, null, true);
	}
	
	public static String parseString(Object object, String defaultValue, boolean allowEmpty) {
		return parseString(object, defaultValue, null, allowEmpty);
	}
	
	public static <T> List<T> parseList(Object object, List<T> defaultValue) {
		return parseList(object, defaultValue, null);
	}
	
	public static interface ParseExceptionCustomCode {
		void errorAppend(Exception exception);
	}
	
}