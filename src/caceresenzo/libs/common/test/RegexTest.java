package caceresenzo.libs.common.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
	
	private static final Pattern pattern = Pattern.compile("(?:youtu\\.be\\/|youtube\\.com(?:\\/embed\\/|\\/v\\/|\\/watch\\?v=|\\/user\\/\\S+|\\/ytscreeningroom\\?v=|\\/sandalsResorts#\\w\\/\\w\\/.*\\/))([^\\/&]{10,12})");
	
	public static void main(String[] args) {
		
		String[] urls = new String[] { "http://www.youtube.com/sandalsResorts#p/c/54B8C800269D7C1B/0/FJUvudQsKCM", "http://www.youtube.com/user/Scobleizer#p/u/1/1p3vcRhsYGo", "http://youtu.be/NLqAF9hrVbY", "http://www.youtube.com/embed/NLqAF9hrVbY", "https://www.youtube.com/embed/NLqAF9hrVbY", "http://www.youtube.com/v/NLqAF9hrVbY?fs=1&hl=en_US", "http://www.youtube.com/watch?v=NLqAF9hrVbY", "http://www.youtube.com/user/Scobleizer#p/u/1/1p3vcRhsYGo", "http://www.youtube.com/ytscreeningroom?v=NRHVzbJVx8I", "http://www.youtube.com/user/Scobleizer#p/u/1/1p3vcRhsYGo", "http://www.youtube.com/watch?v=JYArUl0TzhA&feature=featured" };
		
		for (String url : urls) {
			$("");
			$("Parsing: " + url);
			String output = getVideoId(url);
			$("Out: " + output);
		}
	}
	
	public static String getVideoId(String url) {
		Matcher matcher = pattern.matcher(url);
		if (!matcher.find()) {
			return null;
		}
		return matcher.group(1);
	}
	
	public static void $(Object object) {
		System.out.println(String.valueOf(object));
	}
	
}
