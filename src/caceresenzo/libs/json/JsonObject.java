package caceresenzo.libs.json;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import caceresenzo.libs.parse.ParseUtils;

@SuppressWarnings("rawtypes")
public class JsonObject extends LinkedHashMap<Object, Object> implements Map<Object, Object>, JsonAware, JsonStreamAware {
	
	private static final long serialVersionUID = -503443796854799292L;
	
	public JsonObject() {
		super();
	}
	
	public JsonObject(Map<?, ?> map) {
		super(map);
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		return ParseUtils.parseBoolean(get(key), defaultValue);
	}
	
	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}
	
	public int getInteger(String key, int defaultValue) {
		return ParseUtils.parseInt(get(key), defaultValue);
	}
	
	public int getInteger(String key) {
		return getInteger(key, 0);
	}
	
	public long getLong(String key, long defaultValue) {
		return ParseUtils.parseLong(get(key), defaultValue);
	}
	
	public int getLong(String key) {
		return getInteger(key, 0);
	}
	
	public String getString(String key, String defaultValue) {
		return ParseUtils.parseString(get(key), defaultValue);
	}
	
	public String getString(String key) {
		return getString(key, "");
	}
	
	public JsonObject getJsonObject(String key, JsonObject defaultValue) {
		if (get(key) instanceof JsonObject) {
			return (JsonObject) get(key);
		}
		return defaultValue;
	}
	
	public JsonObject getJsonObject(String key) {
		return getJsonObject(key, null);
	}
	
	public JsonArray getJsonArray(String key, JsonArray defaultValue) {
		if (get(key) instanceof JsonArray) {
			return (JsonArray) get(key);
		}
		return defaultValue;
	}
	
	public JsonArray getJsonArray(String key) {
		return getJsonArray(key, null);
	}
	
	public static void writeJsonString(Map<?, ?> map, Writer out) throws IOException {
		if (map == null) {
			out.write("null");
			return;
		}
		
		boolean first = true;
		Iterator<?> iterator = map.entrySet().iterator();
		
		out.write('{');
		while (iterator.hasNext()) {
			if (first) {
				first = false;
			} else {
				out.write(',');
			}
			Map.Entry entry = (Map.Entry) iterator.next();
			out.write('\"');
			out.write(escape(String.valueOf(entry.getKey())));
			out.write('\"');
			out.write(':');
			JsonValue.writeJsonString(entry.getValue(), out);
		}
		out.write('}');
	}
	
	public void writeJsonString(Writer out) throws IOException {
		writeJsonString(this, out);
	}
	
	public static String toJsonString(Map<?, ?> map) {
		if (map == null) {
			return "null";
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		boolean first = true;
		Iterator<?> iterator = map.entrySet().iterator();
		
		stringBuffer.append('{');
		while (iterator.hasNext()) {
			if (first) {
				first = false;
			} else {
				stringBuffer.append(',');
			}
			
			Map.Entry entry = (Map.Entry) iterator.next();
			toJsonString(String.valueOf(entry.getKey()), entry.getValue(), stringBuffer);
		}
		stringBuffer.append('}');
		return stringBuffer.toString();
	}
	
	public String toJsonString() {
		return toJsonString(this);
	}
	
	private static String toJsonString(String key, Object value, StringBuffer stringBuffer) {
		stringBuffer.append('\"');
		if (key == null)
			stringBuffer.append("null");
		else
			JsonValue.escape(key, stringBuffer);
		stringBuffer.append('\"').append(':');
		
		stringBuffer.append(JsonValue.toJsonString(value));
		
		return stringBuffer.toString();
	}
	
	public String toString() {
		return toJsonString();
	}
	
	public static String toString(String key, Object value) {
		StringBuffer stringBuffer = new StringBuffer();
		toJsonString(key, value, stringBuffer);
		return stringBuffer.toString();
	}
	
	public static String escape(String string) {
		return JsonValue.escape(string);
	}
	
}