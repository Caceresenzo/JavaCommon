package caceresenzo.libs.internationalization;

import java.util.HashMap;

import caceresenzo.libs.logger.Logger;

public class i18n {
	
	public static HashMap<String, Translation> languages = new HashMap<String, Translation>();
	private static String selectedLanguage = "en_US";
	private static boolean DEBUG = true;
	
	public static void register(Translation translation) {
		if (languages.containsKey(translation.getTargetLanguage())) {
			if (DEBUG) {
				Logger.info("Adding to language: " + translation.getTargetLanguage() + " " + translation.getSize() + " (possible) line(s)");
			}
			InternationalizationUtils.addTranslationsToAnother(languages.get(translation.getTargetLanguage()), translation);
		} else {
			if (DEBUG) {
				Logger.info("Register new language: " + translation.getTargetLanguage() + " (" + translation.getSize() + " line(s))");
			}
			languages.put(translation.getTargetLanguage(), translation);
		}
	}
	
	public static void addString(String language, String key, String line) {
		Translation translation = getTranslationInstance(language);
		if (translation != null) {
			translation.addString(key, line);
		} else {
			if (DEBUG) {
				Logger.error("i18n: Failed to add key \"" + key + "\" to language \"" + language + "\". (Not registered or unknown) (value=\"" + line + "\")");
			}
		}
	}
	
	public static Translation getTranslationInstance(String language) {
		if (languages.containsKey(language)) {
			return languages.get(language);
		}
		if (languages.containsKey("en_US")) {
			if (DEBUG) {
				Logger.warning("i18n: No Translation instance found for language \"" + language + "\". (Selecting default one)");
			}
			return languages.get("en_US");
		}
		
		if (DEBUG) {
			Logger.warning("i18n: No Translation instance found for language \"" + language + "\". (Creating temporaty one)");
		}
		return new Translation(language);
	}
	
	public static String getString(String key) {
		return getTranslationInstance(selectedLanguage).getString(key);
	}
	
	public static String getString(String key, Object... args) {
		return String.format(getTranslationInstance(selectedLanguage).getString(key), args);
	}
	
	public static String string(String key) {
		return getString(key);
	}
	
	public static String string(String key, Object... args) {
		return getString(key, args);
	}
	
	public static void setSelectedLanguage(String language) {
		selectedLanguage = language;
	}
	
	public static String getSelectedLanguage() {
		return selectedLanguage;
	}
	
	public static String getDefaultLanguage() {
		return "en_US";
	}
	
	public static void setDebug(boolean debug) {
		i18n.DEBUG = debug;
	}
	
}