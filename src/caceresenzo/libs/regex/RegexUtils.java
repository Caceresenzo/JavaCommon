package caceresenzo.libs.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	public static String getGroup(String regex, String source, int group) {
		Matcher matcher = Pattern.compile(regex).matcher(source);
		
		if (!matcher.find()) {
			return null;
		}
		
		return matcher.group(group);
	}
	
	public static String[] getGroup(String regex, String source) {
		return getGroup(Pattern.compile(regex), source);
	}
	
	public static String[] getGroup(Pattern pattern, String source) {
		return getGroup(pattern.matcher(source));
	}
	
	public static String[] getGroup(Matcher matcher) {
		if (matcher == null || !matcher.matches()) {
			return null;
		}
		
		String[] groups = new String[matcher.groupCount() + 1];
		for (int i = 0; i < matcher.groupCount() + 1; i++) {
			groups[i] = matcher.group(i);
		}
		
		return groups;
	}
	
	public static boolean pregMatch(String pattern, String content) {
		return content.matches(pattern);
	}
	
}