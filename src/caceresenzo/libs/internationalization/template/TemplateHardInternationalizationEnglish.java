package caceresenzo.libs.internationalization.template;

import caceresenzo.libs.internationalization.HardInternationalization;

public class TemplateHardInternationalizationEnglish extends HardInternationalization {

	@Override
	public void set() {
		o("intro.hello", "Hello World");
		o("test.button.ok", "OK");
		o("test.button.cancel", "Cancel");
	}
	
}