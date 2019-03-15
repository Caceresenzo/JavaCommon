package caceresenzo.libs.enums;

import java.util.Arrays;

public class EnumUtils {
	
	/**
	 * Get an array of {@link String} of all the values of an enum class.
	 * 
	 * @param enumClass
	 *            Target enum class.
	 * @return An array of the values of the enum.
	 */
	public static String[] getNames(Class<? extends Enum<?>> enumClass) {
		return Arrays.toString(enumClass.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
	}
	
}