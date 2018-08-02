package caceresenzo.libs.plugin;

public interface PluginLogger {

	void log(String format, Object... args);

	void info(String format, Object... args);

	void success(String format, Object... args);

	void warning(String format, Object... args);

	void error(String format, Object... args);

	void critical(String format, Object... args);

	void fatal(String format, Object... args);

	void debug(String format, Object... args);

	void exception(Throwable throwable, String format, Object... args);

}