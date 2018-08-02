package caceresenzo.libs.plugin;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import caceresenzo.libs.logger.Logger;

public final class UniversalPluginLoader extends AbstractPluginLoader {
	
	@Override
	@SuppressWarnings("unchecked")
	public Plugin load(File file) {
		if (file.isDirectory() || !file.getName().toLowerCase().endsWith(".jar")) {
			return null;
		}
		
		Class<? extends Plugin> pluginClass = null;
		
		try {
			Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);
			method.invoke((URLClassLoader) ClassLoader.getSystemClassLoader(), file.toURI().toURL());
			
			ZipFile zip = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zip.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (entry.getName().toLowerCase().endsWith(".class")) {
					Class<? extends Plugin> potentialClass = (Class<? extends Plugin>) Class.forName(entry.getName().substring(0, entry.getName().length() - 6).replace("/", "."));
					if (potentialClass.isAnnotationPresent(PluginManifest.class)) {
						pluginClass = potentialClass;
						break;
					}
				}
			}
			zip.close();
			
			return pluginClass.newInstance();
		} catch (Throwable throwable) {
			Logger.exception(throwable);
			return null;
		}
	}
	
}