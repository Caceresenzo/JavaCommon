package caceresenzo.libs.http;

import java.util.HashMap;
import java.util.regex.Matcher;

public class HttpHook {
	
	private int responseCode = 200;
	private String responseContent = "", responseMime = "";
	
	public HttpHook() {
		;
	}
	
	public void call(Matcher matcher, String path, String ressource, HashMap<String, String> parameters, String[] regex) {
		; // Need to be overrided
	}
	
	public int code() {
		return responseCode;
	}
	
	public void code(int code) {
		this.responseCode = code;
	}
	
	public String content() {
		return responseContent;
	}
	
	public void content(String string) {
		this.responseContent = string;
	}
	
	public String mime() {
		return responseMime;
	}
	
	public void mime(String string) {
		this.responseMime = string;
	}
	
	public void append(String string) {
		this.responseContent += string;
	}
	
	public void appendln(String string) {
		this.responseContent += string;
	}
	
	public void appendln() {
		appendln("");
	}
	
}