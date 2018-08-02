package caceresenzo.libs.plugin;

import caceresenzo.libs.logger.Logger;

public abstract class AbstractPluginLogger implements PluginLogger {
	
	private Plugin plugin;
	
	protected AbstractPluginLogger(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override @Deprecated
	public final void log(String format, Object... args) {
		// Disabled
	}

	@Override
	public final void info(String format, Object... args) {
		Logger.info("[" + plugin + "] " + format, args);
	}

	@Override
	public final void success(String format, Object... args) {
		Logger.success("[" + plugin + "] " + format, args);
	}

	@Override
	public final void warning(String format, Object... args) {
		Logger.warning("[" + plugin + "] " + format, args);
	}

	@Override
	public final void error(String format, Object... args) {
		Logger.error("[" + plugin + "] " + format, args);
	}

	@Override
	public final void critical(String format, Object... args) {
		Logger.critical("[" + plugin + "] " + format, args);
	}

	@Override
	public final void fatal(String format, Object... args) {
		Logger.fatal("[" + plugin + "] " + format, args);
	}

	@Override
	public final void debug(String format, Object... args) {
		Logger.debug("[" + plugin + "] " + format, args);
	}

	@Override
	public final void exception(Throwable throwable, String format, Object... args) {
		Logger.exception(throwable, "[" + plugin + "] " + format, args);
	}

}
