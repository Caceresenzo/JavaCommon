package caceresenzo.libs.internationalization;

import java.util.HashMap;

public class Translation {
	
	private HashMap<String, String> translations = new HashMap<String, String>();
	private String language;
	
	public Translation(String language) {
		this.language = language;
	}
	
	public String getString(String key) {
		if (this.translations.containsKey(key)) {
			return this.translations.get(key);
		} else {
			return "%" + key + "%";
		}
	}
	
	public void addString(String key, String line) {
		this.translations.put(key, line);
	}
	
	public HashMap<String, String> getTranslations() {
		return this.translations;
	}
	
	public int getSize() {
		return this.translations.size();
	}
	
	public String getTargetLanguage() {
		return this.language;
	}

	public void setTargetLanguage(String language) {
		this.language = language;
	}
	
}