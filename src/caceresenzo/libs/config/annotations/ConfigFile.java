package caceresenzo.libs.config.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import caceresenzo.libs.config.processor.AbstractConfigProcessor;

/**
 * Use this {@link Annotation} on a {@code static} {@link Field}.
 * 
 * @author Enzo CACERES
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface ConfigFile {
	
	/** @return Name of the file. */
	String name();
	
	/** @return {@link AbstractConfigProcessor} class capable to handle this kind of file. */
	Class<? extends AbstractConfigProcessor> processor();
	
}