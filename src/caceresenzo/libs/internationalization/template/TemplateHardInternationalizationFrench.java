package caceresenzo.libs.internationalization.template;

import caceresenzo.libs.internationalization.HardInternationalization;

public class TemplateHardInternationalizationFrench extends HardInternationalization {

	@Override
	public void set() {
		o("intro.hello", "Bonjours le monde");
		o("test.button.ok", "OK");
		o("test.button.cancel", "Annuler");
	}
	
}