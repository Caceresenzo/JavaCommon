package caceresenzo.libs.http;

import caceresenzo.libs.http.exception.HttpHookRegistry;

public class HttpServerConfig {
	
	private HttpExtensionRegistry extensionRegistry;
	private HttpHookRegistry hookRegistry;
	
	public HttpServerConfig(HttpExtensionRegistry extensionRegistry, HttpHookRegistry hookRegistry) {
		this.extensionRegistry = extensionRegistry;
		this.hookRegistry = hookRegistry;
	}
	
	public HttpExtensionRegistry getExtensionRegistry() {
		return extensionRegistry;
	}
	
	public void setExtensionRegistry(HttpExtensionRegistry extensionRegistry) {
		this.extensionRegistry = extensionRegistry;
	}
	
	public HttpHookRegistry getHookRegistry() {
		return hookRegistry;
	}
	
	public void setHookRegistry(HttpHookRegistry hookRegistry) {
		this.hookRegistry = hookRegistry;
	}
	
}