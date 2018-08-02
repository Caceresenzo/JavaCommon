package caceresenzo.libs.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import caceresenzo.libs.settings.SettingsValue;

public class SettingsManager {

	public HashMap<Object, List<SettingsValue<?>>> referencedSettings;

	public SettingsManager() {
		referencedSettings = new HashMap<Object, List<SettingsValue<?>>>();
	}

	public void addSettings(Object referencer, SettingsValue<?> setting) {
		List<SettingsValue<?>> settings = referencedSettings.get(referencer);
		if (settings == null) {
			settings = new ArrayList<SettingsValue<?>>();
		}
		settings.add(setting);
	}

	public void removeSettings(Object referencer, SettingsValue<?> setting) {
		List<SettingsValue<?>> settings = referencedSettings.get(referencer);
		if (settings != null) {
			settings = new ArrayList<SettingsValue<?>>();
		}
		if (settings.size() == 0) {
			referencedSettings.remove(settings);
		}
	}

	public void removeAllSettings(Object referencer) {
		if (referencedSettings.containsKey(referencer)) {
			referencedSettings.remove(referencer);
		}
	}

	public List<SettingsValue<?>> getSettingsFromReferencer(Object referencer) {
		List<SettingsValue<?>> settings = referencedSettings.get(referencer);
		if (settings == null) {
			settings = new ArrayList<SettingsValue<?>>();
		}
		return settings;
	}

}