package caceresenzo.libs.logger;

public abstract class AbstractLogger {
	
	protected AbstractLogger() {
		
	}
	
	protected abstract void log(LogLevel level, Object format, Object... args);
	
	public void log(Object format, Object... args) {
		log(LogLevel.VOID, format, args);
	}
	
	public void log(Object object) {
		log(LogLevel.VOID, object);
	}
	
	public void $(Object format, Object... args) {
		log(LogLevel.VOID, format, args);
	}
	
	public void $(Object object) {
		log(LogLevel.VOID, object);
	}
	
	public void success(Object format, Object... args) {
		log(LogLevel.SUCCESS, format, args);
	}
	
	public void success(Object object) {
		log(LogLevel.SUCCESS, object);
	}
	
	public void info(Object format, Object... args) {
		log(LogLevel.INFO, format, args);
	}
	
	public void info(Object object) {
		log(LogLevel.INFO, object);
	}
	
	public void warning(Object format, Object... args) {
		log(LogLevel.WARNING, format, args);
	}
	
	public void warning(Object object) {
		log(LogLevel.WARNING, object);
	}
	
	public void error(Object format, Object... args) {
		log(LogLevel.ERROR, format, args);
	}
	
	public void error(Object object) {
		log(LogLevel.ERROR, object);
	}
	
	public void critical(Object format, Object... args) {
		log(LogLevel.CRITICAL, format, args);
	}
	
	public void critical(Object object) {
		log(LogLevel.CRITICAL, object);
	}
	
	public void fatal(Object format, Object... args) {
		log(LogLevel.FATAL, format, args);
	}
	
	public void fatal(Object object) {
		log(LogLevel.FATAL, object);
	}
	
	public void debug(Object format, Object... args) {
		log(LogLevel.DEBUG, format, args);
	}
	
	public void debug(Object object) {
		log(LogLevel.DEBUG, object);
	}
	
	public void raw(Object object) {
		System.out.println(String.valueOf(object));
	}
	
}
