package caceresenzo.libs.licencekey;

public class LicenceKey {
	
	public static final String PLACEHOLDER_KEY = "AAAA-BBBB-CCCC-DDDD-EEEE";
	
	private final String key;
	
	private boolean valid = false, checked = false;
	
	private LicenceKey(String key) {
		if (key == null || key.isEmpty()) {
			this.key = "";
			
			checked = true;
			valid = false;
			return;
		}
		
		this.key = key.toLowerCase();
	}
	
	public LicenceKey verify() {
		checked = true;
		valid = check();
		return this;
	}
	
	private boolean check() {
		int score = 0, checkDigitCount = 0;
		char checkDigit = key.toCharArray()[0];
		String[] chunks = key.split("-");
		
		for (String chunk : chunks) {
			if (chunk.length() != 4) {
				return false;
			}
			
			for (char character : chunk.toCharArray()) {
				if (character == checkDigit) {
					checkDigitCount++;
				}
				
				score += character;
			}
		}
		
		return (score == 1772 && checkDigitCount == 5);
	}
	
	public String getKey() {
		return key.toUpperCase();
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public static LicenceKey fromString(String string) {
		return new LicenceKey(string);
	}
	
}