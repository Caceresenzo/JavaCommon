package caceresenzo.libs.config.processor;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import caceresenzo.libs.config.annotations.ConfigProperty;
import caceresenzo.libs.config.annotations.ConfigProperty.PropertyType;
import caceresenzo.libs.filesystem.FileUtils;
import caceresenzo.libs.parse.ParseUtils;

/**
 * This class need to be extended to allow file type handling.<br>
 * 
 * @author Enzo CACERES
 */
public abstract class AbstractConfigProcessor {
	
	/* Variables */
	protected File file;
	protected List<Field> configPropertyFields;
	
	/* Constructor */
	public AbstractConfigProcessor(File file) {
		this.file = file;
		this.configPropertyFields = new ArrayList<>();
		
		try {
			FileUtils.forceFileCreation(file);
		} catch (Exception exception) {
			throw new RuntimeException("Failed to validate file: " + file.getAbsolutePath(), exception);
		}
	}
	
	/**
	 * Called when the config must be load and put back in fields
	 * 
	 * @throws Exception
	 *             If anything go wrong
	 */
	public abstract void load() throws Exception;
	
	/**
	 * Called when the config must be saved from field
	 * 
	 * @throws Exception
	 *             If anything go wrong
	 */
	public abstract void save() throws Exception;
	
	/**
	 * Get a {@link Field}'s value
	 * 
	 * @param field
	 *            Target {@link Field}
	 * @return {@link Field}'s value
	 * @throws Exception
	 *             If a security error append
	 */
	protected Object toValue(Field field) throws Exception {
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		
		return field.get(null);
	}
	
	/**
	 * Convert the {@code rawValue} (surely from string) into his appropriate type defined by the {@code propertyType}.
	 * 
	 * @param propertyType
	 *            Target type to convert
	 * @param rawValue
	 *            Original value
	 * @param defaultValue
	 *            Default value, supposed to be correct for direct valueOf();
	 * @return A converted value
	 */
	protected Object parseValue(PropertyType propertyType, Object rawValue, String defaultValue) {
		switch (propertyType) {
			default:
			case STRING: {
				return String.valueOf(rawValue != null ? rawValue : defaultValue);
			}
			
			case BOOLEAN: {
				return ParseUtils.parseBoolean(rawValue, Boolean.valueOf(defaultValue));
			}
			
			case INTEGER: {
				return ParseUtils.parseInt(rawValue, Integer.parseInt(defaultValue));
			}
			
			case LONG: {
				return ParseUtils.parseLong(rawValue, Long.valueOf(defaultValue));
			}
		}
	}
	
	/**
	 * Get the {@link ConfigProperty} {@link Annotation} from the {@link Field}
	 * 
	 * @param field
	 *            Target {@link Field}
	 * @return Attached {@link ConfigProperty}
	 */
	protected ConfigProperty toConfigProperty(Field field) {
		return field.getAnnotation(ConfigProperty.class);
	}
	
	/**
	 * @return Attached {@link AbstractConfigProcessor}'s {@link File}
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * Add a {@link Field} to the list.<br>
	 * Used for loading and saving values.
	 * 
	 * @param field
	 *            Target {@link Field}
	 * @return If the field has successfully been added to the list
	 * @throws IllegalArgumentException
	 *             If the {@code field} don't have the {@link ConfigProperty} {@link Annotation} attached
	 */
	public boolean addField(Field field) {
		if (field.getAnnotation(ConfigProperty.class) == null) {
			throw new IllegalArgumentException("This field has no ConfigProperty annotation attached.");
		}
		
		if (!configPropertyFields.contains(field)) {
			return configPropertyFields.add(field);
		}
		
		return false;
	}
	
	/**
	 * @return {@link List} of {@link Field} that are supposed to have the {@link ConfigProperty} {@link Annotation}
	 */
	public List<Field> getFields() {
		return configPropertyFields;
	}
	
}