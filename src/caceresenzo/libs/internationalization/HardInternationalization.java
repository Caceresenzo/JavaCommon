package caceresenzo.libs.internationalization;

public abstract class HardInternationalization {
	
	private Translation translation;
	
	protected HardInternationalization() {
		this.translation = new Translation(null);
		this.set();
	}
	
	public abstract void set();
	
	public void o(String key, String line) {
		this.translation.addString(key, line);
	}
	
	public Translation getTranslation() {
		return translation;
	}
	
	public void register(String language) {
		translation.setTargetLanguage(language);
		i18n.register(translation);
	}
	
}