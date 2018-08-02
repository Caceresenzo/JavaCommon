package caceresenzo.libs.common.test;

import java.io.IOException;

import caceresenzo.libs.exception.MissingInformationException;
import caceresenzo.libs.internationalization.FileInternationalization;
import caceresenzo.libs.internationalization.i18n;
import caceresenzo.libs.internationalization.template.TemplateHardInternationalizationEnglish;
import caceresenzo.libs.internationalization.template.TemplateHardInternationalizationFrench;
import caceresenzo.libs.logger.Logger;

public class LanguageTest {
	
	public static void main(String[] args) throws IOException, MissingInformationException {
		Logger.$("Step 1");
		new TemplateHardInternationalizationEnglish().register("en_US");
		new TemplateHardInternationalizationFrench().register("fr_FR");
		
		Logger.$("Step 2");
		new FileInternationalization(FileInternationalization.class.getResourceAsStream("./template/en_US.properties")).register("en_US");
		new FileInternationalization(FileInternationalization.class.getResourceAsStream("./template/fr_FR.properties")).register("fr_FR");
		
		Logger.$("Step 3");
		i18n.setSelectedLanguage("fr_FR");
		Logger.$(i18n.getString("intro.hello"));
		
		// i18n.register();
		i18n.addString("en_US", "hello.world", "Hello World");
		
		i18n.setSelectedLanguage("en_US");
		Logger.$(i18n.getString("hello.world"));
	}
	
}