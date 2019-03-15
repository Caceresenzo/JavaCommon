package caceresenzo.libs.config.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;

import static caceresenzo.libs.config.annotations.ConfigProperty.PropertyType.STRING;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Use this {@link Annotation} on a {@code static} {@link Field}.<br>
 * <br>
 * This will define the {@link Field} as a config property, with his own (and unique) key.
 * 
 * @author Enzo CACERES
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface ConfigProperty {
	
	/** @return Already defined {@link ConfigFile}'s name. */
	String file();
	
	/** @return Property's key. */
	String key();
	
	/** @return Property's default value. It can be anything but it must be scoped in a {@link String}. */
	String defaultValue();
	
	/**
	 * If your {@link Field} is not a {@link String}, you must change the type by one of your type.<br>
	 * <br>
	 * Default: {@link PropertyType#STRING}.
	 * 
	 * @return Property's type.
	 */
	PropertyType type() default STRING;
	
	/**
	 * Enumeration of property type actually supported.
	 * 
	 * @author Enzo CACERES
	 */
	public enum PropertyType {
		STRING, INTEGER, LONG, BOOLEAN;
	}
	
}