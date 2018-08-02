package caceresenzo.libs.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

	/**
	 * Get a given text between two anchor keywords
	 * 
	 * @param text
	 *            A line of text
	 * @param keyword1
	 *            beginning keyword
	 * @param keyword2
	 *            finishing keyword
	 * @return empty if nothing found, otherwise returns the result
	 */
	public static String getBetweenExpressions(final String text, String keyword1, String keyword2) {
		final String patternString = "" + keyword1 + "(.*)" + keyword2;

		Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}

	/**
	 * Gets a string value from laptop characteristics based on a given pattern.
	 * A Matcher object is used internally.
	 *
	 * @param source
	 *            string containing the text to be parsed
	 * @param reg
	 *            regular expression pattern to use
	 * @param group
	 *            index of one of the groups found by the pattern
	 * @return String containing the found pattern, or null otherwise
	 */
	public static String findRegEx(String source, String reg, int group) {
		String out = null;

		Pattern pattern = Pattern.compile(reg); // Prepare the search pattern.
		Matcher matcher = pattern.matcher(source); // Retrieve our items.

		if (matcher.find()) {
			try {
				out = matcher.group(group);
			} catch (Exception exception) {
			}
		}

		return out;
	}

	/**
	 * Get safe string
	 * Picks a given string and cleans out any invalid characters, replacing
	 * spaces with "_"
	 */
	public static String safeString(String input) {
		input = input.replace(" ", "_");
		input = input.replace("<", "");
		input = input.replace(">", "");
		input = input.replace("%", "");
		input = input.replace(";", "");
		input = input.replace(";", ".");

		String output = findRegEx(input, "[a-zA-Z0-9-_@\\.]+$", 0); // only accept a-Z, 0-9 and -, _ chars

		return output;
	}

	/**
	 * Removes all numbers from a text string
	 */
	public static String noNumbers(String input) {
		String temp = input.replaceAll("[0-9]", "");
		if (temp == null) {
			return "";
		}
		return "";
	}

}