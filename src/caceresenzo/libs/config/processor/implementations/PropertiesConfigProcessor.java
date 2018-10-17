package caceresenzo.libs.config.processor.implementations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Properties;

import caceresenzo.libs.config.annotations.ConfigProperty;
import caceresenzo.libs.config.processor.AbstractConfigProcessor;

/**
 * Extended {@link AbstractConfigProcessor} class.<br>
 * This allow support for .properties file.
 * 
 * @author Enzo CACERES
 */
public class PropertiesConfigProcessor extends AbstractConfigProcessor {
	
	/* Variables */
	private Properties properties;
	
	/* Constructor */
	public PropertiesConfigProcessor(File file) {
		super(file);
		
		this.properties = new Properties();
	}
	
	@Override
	public void load() throws Exception {
		properties.clear();
		properties.load(new FileInputStream(file));
		
		boolean needSaving = false;
		
		for (Field field : configPropertyFields) {
			ConfigProperty configProperty = toConfigProperty(field);
			
			if (!needSaving) {
				needSaving = !properties.containsKey(configProperty.key());
			}
			
			Object rawValue = properties.getProperty(configProperty.key());
			String defaultValue = configProperty.defaultValue();
			
			Object value = parseValue(configProperty.type(), rawValue, defaultValue);
			
			field.set(null, value);
			
			if (!properties.containsKey(configProperty.key())) {
				properties.setProperty(configProperty.key(), String.valueOf(value));
			}
		}
		
		if (needSaving) {
			save();
		}
	}
	
	@Override
	public void save() throws Exception {
		for (Field field : configPropertyFields) {
			ConfigProperty configProperty = toConfigProperty(field);
			
			properties.setProperty(configProperty.key(), String.valueOf(toValue(field)));
		}
		
		properties.store(new FileWriter(file), null);
	}
	
}