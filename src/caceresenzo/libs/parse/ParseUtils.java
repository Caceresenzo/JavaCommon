package caceresenzo.libs.parse;

import java.util.ArrayList;
import java.util.List;

public class ParseUtils {
	
	public static int parseInt(Object object, int defaultValue) {
		try {
			return Integer.parseInt(String.valueOf(object));
		} catch (NumberFormatException exception) {
			return defaultValue;
		}
	}
	
	public static float parseFloat(Object object, float defaultValue) {
		try {
			return Float.parseFloat(String.valueOf(object));
		} catch (NumberFormatException exception) {
			return defaultValue;
		}
	}
	
	public static double parseDouble(Object object, double defaultValue) {
		try {
			return Double.parseDouble(String.valueOf(object));
		} catch (NumberFormatException exception) {
			return defaultValue;
		}
	}
	
	public static long parseLong(Object object, long defaultValue) {
		try {
			return Long.parseLong(String.valueOf(object));
		} catch (NumberFormatException exception) {
			return defaultValue;
		}
	}
	
	public static boolean parseBoolean(Object object, boolean defaultValue) {
		if (String.valueOf(object).equalsIgnoreCase("TRUE") || String.valueOf(object).equalsIgnoreCase("FALSE")) {
			return Boolean.valueOf(object.toString());
		}
		
		return defaultValue;
	}
	
	public static String parseString(Object object, String defaultValue) {
		return parseString(object, defaultValue, true);
	}
	
	public static String parseString(Object object, String defaultValue, boolean allowEmpty) {
		String parse = String.valueOf(object);
		if ((parse == null || object == null) || (!allowEmpty && parse.isEmpty())) {
			return defaultValue;
		}
		return parse;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> parseList(Object object, List<T> defaultValue) {
		List<T> list = new ArrayList<T>();
		try {
			for (T t : (List<T>) object) {
				list.add(t);
			}
			return list;
		} catch (Exception exception) {
			return defaultValue;
		}
	}
	
}