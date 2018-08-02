package caceresenzo.libs.internationalization;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import caceresenzo.libs.exception.MissingInformationException;

public class FileInternationalization {
	
	private Translation translation;
	
	public FileInternationalization(InputStream stream) throws MissingInformationException, IOException {
		Properties properties = new Properties();
		properties.load(stream);
		
		translation = new Translation(null);
		for (String key : properties.stringPropertyNames()) {
			translation.addString(key, properties.getProperty(key));
		}
	}
	
	public void register(String language) {
		translation.setTargetLanguage(language);
		i18n.register(translation);
	}
	
}