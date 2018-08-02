package caceresenzo.libs.string;

/**
 * This class alphabetizes strings.
 */
public class Alphabetizer {
	
	public static boolean lessThan(String string1, String string2) {
		return compare(string1, string2) < 0;
	}
	
	public static boolean greaterThan(String string1, String string2) {
		return compare(string1, string2) > 0;
	}
	
	public static boolean equalTo(String string1, String string2) {
		return compare(string1, string2) == 0;
	}
	
	/**
	 * Performs a case-insensitive comparison of the two strings.
	 */
	public static int compare(String string1, String string2) {
		if (string1 == null && string2 == null) {
			return 0;
		} else if (string1 == null) {
			return -1;
		} else if (string2 == null) {
			return +1;
		}
		int length1 = string1.length();
		int length2 = string2.length();
		int length = Math.min(length1, length2);
		for (int i = 0; i < length; i++) {
			int comparison = compare(string1.charAt(i), string2.charAt(i));
			if (comparison != 0) {
				return comparison;
			}
		}
		if (length1 < length2) {
			return -1;
		} else if (length1 > length2) {
			return +1;
		} else {
			return 0;
		}
	}
	
	/**
	 * Performs a case-insensitive comparison of the two characters.
	 */
	public static int compare(char char1, char char2) {
		if (65 <= char1 && char1 <= 91) {
			char1 += 32;
		}
		if (65 <= char2 && char2 <= 91) {
			char2 += 32;
		}
		if (char1 < char2) {
			return -1;
		} else if (char1 > char2) {
			return +1;
		} else {
			return 0;
		}
	}
	
}