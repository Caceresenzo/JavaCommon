package caceresenzo.libs.empty;

import java.util.List;

import caceresenzo.libs.string.StringUtils;

public class EmptyUtils {
	
	/**
	 * Check all <code>strings</code> and return <code>true</code> if they are all not empty and not <code>null</code>.<br>
	 * In the other case, if only one is not valid, a <code>false</code> will be returned.
	 * 
	 * @param strings
	 *            {@link String} that you want to test.
	 * @return Weather or not all string are valid or not.
	 * @see StringUtils#validate(String...) validate() from StringUtils
	 */
	public static boolean validate(String... strings) {
		return StringUtils.validate(strings);
	}
	
	/**
	 * Check all <code>lists</code> and return <code>true</code> if they are all not empty and not <code>null</code>.<br>
	 * In the other case, if only one is not valid, a <code>false</code> will be returned.
	 * 
	 * @param lists
	 *            {@link List} that you want to test.
	 * @return Weather or not all lists are valid or not.
	 */
	public static boolean validate(List<?>... lists) {
		for (List<?> list : lists) {
			if (list == null || list.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
}