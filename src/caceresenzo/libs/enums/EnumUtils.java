package caceresenzo.libs.enums;

import java.util.Arrays;

public class EnumUtils {

	public static String[] getNames(Class<? extends Enum<?>> enumClass) {
		return Arrays.toString(enumClass.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
	}

}