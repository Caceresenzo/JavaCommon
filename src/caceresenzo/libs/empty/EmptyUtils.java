package caceresenzo.libs.empty;

import java.util.List;

public class EmptyUtils {
	
	public static boolean validate(String... strings) {
		for (String string : strings) {
			if (string == null || string.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean validate(List<?>... lists) {
		for (List<?> list : lists) {
			if (list == null || list.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
}