package caceresenzo.libs.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCommonMatch {
	
	private static Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	
	public static boolean validateEmail(String email) {
		Matcher matcher = emailPattern.matcher(email);
		return matcher.matches();
	}
	
}