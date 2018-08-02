package caceresenzo.libs.plugin.template;

import caceresenzo.libs.plugin.AbstractPlugin;
import caceresenzo.libs.plugin.PluginConfiguration;
import caceresenzo.libs.plugin.PluginManifest;

@PluginManifest(name = "TestPlugin", description = "An example plugin.", version = 0.1, authors = "Enzo CACERES")

public final class TemplatePlugin extends AbstractPlugin {

    @Override
    public final void onEnable() {
    	getLogger().info(" : Plugin Enabled!!!!!!!");
    	
    	PluginConfiguration configuration = new PluginConfiguration();
    	configuration.put("Key", "Value");
    	saveConfiguration(configuration, "config.properties");

    	getLogger().info(": " + configuration.get("Key"));
    }

    @Override
    public final void onDisable() {
    	getLogger().info(" : Plugin Disabled!!!!!!!");
    }

}