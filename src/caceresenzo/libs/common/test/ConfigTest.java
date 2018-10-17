package caceresenzo.libs.common.test;

import caceresenzo.libs.config.Configuration;
import caceresenzo.libs.config.annotations.ConfigFile;
import caceresenzo.libs.config.annotations.ConfigProperty;
import caceresenzo.libs.config.annotations.ConfigProperty.PropertyType;
import caceresenzo.libs.config.processor.implementations.PropertiesConfigProcessor;
import caceresenzo.libs.logger.Logger;
import caceresenzo.libs.test.SimpleTestUnits;

public class ConfigTest extends SimpleTestUnits {
	
	public static void main(String[] args) {
		initializeUnit(false);
		
		MyConfig.initialize();
		
		Logger.info("MESSAGE_HELLO: " + MyConfig.MESSAGE_HELLO);
		Logger.info("MESSAGE_WORLD: " + MyConfig.MESSAGE_WORLD);

		Logger.info("WORKER_COUNT: " + MyConfig.WORKER_COUNT);
	}
	
	public static class MyConfig extends Configuration {
		
		private static MyConfig INSTANCE;
		
		@ConfigFile(name = "messages", processor = PropertiesConfigProcessor.class)
		public static final String CONFIG_MESSAGES = "messages.properties";
		
		@ConfigFile(name = "workers", processor = PropertiesConfigProcessor.class)
		public static final String CONFIG_WORKERS = "workers.properties";
		
		@ConfigProperty(file = "messages", key = "message.hello", defaultValue = "Hello")
		public static String MESSAGE_HELLO;
		@ConfigProperty(file = "messages", key = "message.world", defaultValue = "World")
		public static String MESSAGE_WORLD;
		
		@ConfigProperty(file = "workers", key = "worker.count", defaultValue = "2", type = PropertyType.INTEGER)
		public static int WORKER_COUNT;
		
		public static void initialize() {
			INSTANCE = (MyConfig) initialize(MyConfig.class);
			try {
				INSTANCE.loadAll();
			} catch (Exception exception) {
				Logger.exception(exception, "Failed to load config.");
			}
		}
		
	}
	
}