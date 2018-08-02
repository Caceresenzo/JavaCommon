package caceresenzo.libs.settings;

public class SettingsValue<T> {
	
	private String name, description, category;
	private T value, defvalue;
	
	public SettingsValue(String name, String description, String category, T value, T defvalue) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.value = value;
		this.defvalue = defvalue;
	}
	
	public void resetValue() {
		this.value = defvalue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T getDefvalue() {
		return defvalue;
	}

	public void setDefvalue(T defvalue) {
		this.defvalue = defvalue;
	}

	public String getName() {
		return name;
	}
	
	
}