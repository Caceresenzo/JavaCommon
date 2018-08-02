package caceresenzo.libs.plugin;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPluginManager implements PluginManager {
	
	private File PLUGINS_DIRECTORY = DEFAULT_PLUGINS_DIRECTORY;
	
	protected final PluginRegistry registry;
	protected final PluginLoader loader;
	
	protected AbstractPluginManager(PluginRegistry registry, PluginLoader loader) {
		this.registry = registry;
		this.loader = loader;
	}
	
	protected AbstractPluginManager(PluginLoader loader) {
		this(new PluginRegistry(), loader);
	}
	
	protected final PluginRegistry getRegistry() {
		return registry;
	}
	
	@Override
	public final PluginLoader getLoader() {
		return loader;
	}
	
	@Override
	public final Set<Plugin> getPlugins() {
		return Collections.unmodifiableSet(new HashSet<Plugin>(registry.accessRegistry().values()));
	}
	
	@Override
	public final Plugin getPlugin(String name) {
		for (Plugin plugin : getPlugins()) {
			if (plugin.getManifest().name().equalsIgnoreCase(name)) {
				return plugin;
			}
		}
		return null;
	}
	
	@Override
	public final Set<Plugin> loadPlugins(File directory) {
		if (!directory.exists()) {
			directory.mkdir();
		}
		Set<Plugin> plugins = new HashSet<Plugin>();
		for (File file : directory.listFiles()) {
			Plugin plugin = getLoader().load(file);
			if (plugin != null) {
				plugins.add(plugin);
			}
		}
		for (Plugin plugin : plugins) {
			registry.registerPlugin(plugin.getManifest(), plugin);
		}
		return plugins;
	}
	
	@Override
	public final Set<Plugin> loadPlugins() {
		return loadPlugins(PLUGINS_DIRECTORY);
	}
	
	@Override
	public final void enablePlugins() {
		for (Plugin plugin : getPlugins()) {
			plugin.enable();
		}
	}
	
	@Override
	public final void disablePlugins() {
		for (Plugin plugin : getPlugins()) {
			plugin.disable();
		}
	}
	
	@Override
	public void setPluginsDirectory(File directory) {
		PLUGINS_DIRECTORY = directory;
		if (!directory.exists()) {
			directory.mkdir();
		}
	}
	
}