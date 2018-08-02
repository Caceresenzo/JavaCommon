package caceresenzo.libs.cryptography;

public class ROT13 {
	
	public static String rot13(String input) {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < input.length(); i++) {
			char charactere = input.charAt(i);
			if (charactere >= 'a' && charactere <= 'm') {
				charactere += 13;
			} else if (charactere >= 'A' && charactere <= 'M') {
				charactere += 13;
			} else if (charactere >= 'n' && charactere <= 'z') {
				charactere -= 13;
			} else if (charactere >= 'N' && charactere <= 'Z') {
				charactere -= 13;
			}
			
			builder.append(charactere);
		}
		
		return builder.toString();
	}
	
}