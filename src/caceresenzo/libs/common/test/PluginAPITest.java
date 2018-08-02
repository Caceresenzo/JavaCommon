package caceresenzo.libs.common.test;

import caceresenzo.libs.plugin.PluginManager;
import caceresenzo.libs.plugin.UniversalPluginManager;

public class PluginAPITest {
	
	public static void main(String[] args) {
		PluginManager pluginManager = new UniversalPluginManager();

		pluginManager.loadPlugins();
		pluginManager.enablePlugins();
	}
	
}
