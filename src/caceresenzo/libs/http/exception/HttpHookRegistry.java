package caceresenzo.libs.http.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import caceresenzo.libs.http.HttpHook;
import caceresenzo.libs.logger.Logger;

public class HttpHookRegistry {
	
	public static final String PATTERN = "/\\{([a-zA-Z]+):(.+)}/";
	
	private HttpHook defaultHook;
	private Map<Pattern, HttpHook> hooks;
	
	public HttpHookRegistry() {
		this(null);
	}
	
	public HttpHookRegistry(Map<Pattern, HttpHook> hooks) {
		if (hooks == null) {
			hooks = new HashMap<Pattern, HttpHook>();
		}
		
		this.hooks = hooks;
	}
	
	public void setDefaultHook(HttpHook hook) {
		Logger.info("Registered (as default) hook: class " + hook.getClass().getSimpleName());
		this.defaultHook = hook;
	}
	
	public void register(String path, HttpHook hook) {
		Pattern pattern = Pattern.compile("^" + path.replaceAll(PATTERN, "(?P<$1>$2)") + "$");
		Logger.info("Registered hook: " + pattern.pattern() + " to class " + hook.getClass().getSimpleName());
		
		hooks.put(pattern, hook);
	}
	
	public HookRegistryMatch match(String urlPath) {
		if (urlPath.startsWith("/")) {
			urlPath = urlPath.substring(1);
		}
		// Logger.info(urlPath);
		
		if (urlPath.isEmpty() && defaultHook != null) {
			return new HookRegistryMatch(null, defaultHook);
		}
		
		for (Entry<Pattern, HttpHook> hookEntry : hooks.entrySet()) {
			Matcher matcher = hookEntry.getKey().matcher(urlPath);
			
			// if (RegexUtils.pregMatch(hookEntry.getKey().pattern(), urlPath)) {
			if (matcher.matches()) {
				return new HookRegistryMatch(matcher, hookEntry.getValue());
			}
		}
		
		return new HookRegistryMatch(null, null);
	}
	
	public static class HookRegistryMatch {
		private Matcher matcher;
		private HttpHook hook;
		
		public HookRegistryMatch(Matcher matcher, HttpHook hook) {
			this.matcher = matcher;
			this.hook = hook;
		}
		
		public Matcher getMatcher() {
			return matcher;
		}
		
		public HttpHook getHook() {
			return hook;
		}
		
	}
	
}