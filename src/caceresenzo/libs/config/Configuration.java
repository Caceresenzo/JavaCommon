package caceresenzo.libs.config;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import caceresenzo.libs.config.annotations.ConfigFile;
import caceresenzo.libs.config.annotations.ConfigProperty;
import caceresenzo.libs.config.processor.AbstractConfigProcessor;
import caceresenzo.libs.reflection.ReflectionUtils;

/**
 * Simple configuration handler class.
 * 
 * @author Enzo CACERES
 */
public class Configuration {
	
	/* Variables */
	private List<AbstractConfigProcessor> processors;
	
	/* Constructor */
	/** Don't {@link Override} or make him protected/private. */
	public Configuration() {
		this.processors = new ArrayList<>();
	}
	
	/**
	 * Load all config file attached to {@link AbstractConfigProcessor} instances.
	 * 
	 * @throws Exception
	 *             If anything went wrong.
	 */
	public void loadAll() throws Exception {
		for (AbstractConfigProcessor processor : processors) {
			processor.load();
		}
	}
	
	/**
	 * Save all config file attached to {@link AbstractConfigProcessor} instances.
	 * 
	 * @throws Exception
	 *             If anything went wrong.
	 */
	public void saveAll() throws Exception {
		for (AbstractConfigProcessor processor : processors) {
			processor.save();
		}
	}
	
	/**
	 * Will return an {@link AbstractConfigProcessor} instance, it can be new or already created.<br>
	 * This will depend if they will have the same class and the same target file.
	 * 
	 * @param processorClass
	 *            {@link ConfigProperty}'s processor() class.
	 * @param file
	 *            Target config file.
	 * @return An {@link AbstractConfigProcessor} instance.
	 * @throws IllegalStateException
	 *             If the function failed to create instance of the {@code processorClass} class.
	 */
	private AbstractConfigProcessor findProcessor(Class<? extends AbstractConfigProcessor> processorClass, File file) {
		for (AbstractConfigProcessor processor : processors) {
			if (processor.getClass().equals(processorClass) && processor.getFile().equals(file)) {
				return processor;
			}
		}
		
		AbstractConfigProcessor abstractConfigProcessor;
		try {
			abstractConfigProcessor = processorClass.getConstructor(File.class).newInstance(file);
			processors.add(abstractConfigProcessor);
		} catch (Exception exception) {
			throw new IllegalStateException("Failed to create instance for class: " + processorClass, exception);
		}
		
		return abstractConfigProcessor;
	}
	
	/** @return {@link List} of actually instanced {@link AbstractConfigProcessor}. */
	public List<AbstractConfigProcessor> getProcessors() {
		return processors;
	}
	
	/**
	 * Initialize a configuration class.<br>
	 * <br>
	 * This will read all {@link Field} and pick witch have {@link ConfigFile} or {@link ConfigProperty} {@link Annotation}.
	 * 
	 * @param configurationClass
	 *            Your {@link Configuration} extended class.
	 * @return A {@link Configuration} instance.
	 * @throws NullPointerException
	 *             If the {@code configurationClass} is <code>null</code>.
	 * @throws IllegalStateException
	 *             If the instance creation class failed.
	 * @throws RuntimeException
	 *             If {@link Configuration} mapping has failed.
	 */
	protected static Configuration initialize(Class<? extends Configuration> configurationClass) {
		if (configurationClass == null) {
			throw new NullPointerException("Configuration class can't be null.");
		}
		
		Configuration configuration;
		try {
			configuration = configurationClass.newInstance();
		} catch (InstantiationException | IllegalAccessException exception) {
			throw new IllegalStateException("Failed to intialize configuration class: " + configurationClass + ".", exception);
		}
		
		Set<Field> configFiles = ReflectionUtils.findFields(configurationClass, ConfigFile.class);
		Set<Field> configProperties = ReflectionUtils.findFields(configurationClass, ConfigProperty.class);
		
		try {
			for (Field configField : configFiles) {
				ConfigFile configFile = configField.getAnnotation(ConfigFile.class);
				
				configField.setAccessible(true);
				
				AbstractConfigProcessor processor = configuration.findProcessor(configFile.processor(), new File((String) configField.get(configField)));
				
				for (Field propertyField : configProperties) {
					ConfigProperty configProperty = propertyField.getAnnotation(ConfigProperty.class);
					
					if (configProperty.file().equals(configFile.name())) {
						processor.getFields().add(propertyField);
					}
				}
			}
			
			configuration.loadAll();
		} catch (Exception exception) {
			throw new RuntimeException("Failed to fully create config mapping.", exception);
		}
		
		return configuration;
	}
	
}