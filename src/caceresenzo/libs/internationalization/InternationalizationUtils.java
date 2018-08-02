package caceresenzo.libs.internationalization;

import java.util.Map.Entry;

public class InternationalizationUtils {
	
	public static void addTranslationsToAnother(Translation target, Translation addition) {
		for (Entry<String, String> entry : addition.getTranslations().entrySet()) {
			target.addString(entry.getKey(), entry.getValue());
		}
	}
	
}