package caceresenzo.libs.licencekey;

import java.util.Random;

public class LicenceKeyGenerator {
	
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz1234567890";
	
	private Random random;
	
	public LicenceKeyGenerator() {
		random = new Random();
	}
	
	public LicenceKey generateKey() {
		String key = "", chunk = "";
		
		while (true) {
			while (key.length() < 25) {
				char charactere = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
				
				key += charactere;
				chunk += charactere;
				
				if (chunk.length() == 4) {
					key += "-";
					chunk = "";
				}
			}
			
			key = key.substring(0, key.length() - 1);
			
			LicenceKey licenceKey = LicenceKey.fromString(key);
			if (licenceKey.verify().isValid()) {
				return licenceKey;
			} else {
				key = "";
			}
		}
	}
	
}