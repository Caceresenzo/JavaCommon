package caceresenzo.libs.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import caceresenzo.libs.json.parser.JsonParser;
import caceresenzo.libs.json.parser.JsonException;

public class JsonValue {

	public static Object parse(Reader in) {
		try {
			JsonParser parser = new JsonParser();
			return parser.parse(in);
		} catch (Exception e) {
			return null;
		}
	}

	public static Object parse(String s) {
		StringReader in = new StringReader(s);
		return parse(in);
	}

	public static Object parseWithException(Reader in) throws IOException, JsonException {
		JsonParser parser = new JsonParser();
		return parser.parse(in);
	}

	public static Object parseWithException(String s) throws JsonException {
		JsonParser parser = new JsonParser();
		return parser.parse(s);
	}

	public static void writeJsonString(Object value, Writer out) throws IOException {
		if (value == null) {
			out.write("null");
			return;
		}

		if (value instanceof String) {
			out.write('\"');
			out.write(escape((String) value));
			out.write('\"');
			return;
		}

		if (value instanceof Double) {
			if (((Double) value).isInfinite() || ((Double) value).isNaN())
				out.write("null");
			else
				out.write(value.toString());
			return;
		}

		if (value instanceof Float) {
			if (((Float) value).isInfinite() || ((Float) value).isNaN())
				out.write("null");
			else
				out.write(value.toString());
			return;
		}

		if (value instanceof Number) {
			out.write(value.toString());
			return;
		}

		if (value instanceof Boolean) {
			out.write(value.toString());
			return;
		}

		if ((value instanceof JsonStreamAware)) {
			((JsonStreamAware) value).writeJsonString(out);
			return;
		}

		if ((value instanceof JsonAware)) {
			out.write(((JsonAware) value).toJsonString());
			return;
		}

		if (value instanceof Map) {
			JsonObject.writeJsonString((Map<?, ?>) value, out);
			return;
		}

		if (value instanceof List) {
			JsonArray.writeJSONString((List<?>) value, out);
			return;
		}

		out.write(value.toString());
	}

	public static String toJsonString(Object value) {
		if (value == null)
			return "null";

		if (value instanceof String)
			return "\"" + escape((String) value) + "\"";

		if (value instanceof Double) {
			if (((Double) value).isInfinite() || ((Double) value).isNaN())
				return "null";
			else
				return value.toString();
		}

		if (value instanceof Float) {
			if (((Float) value).isInfinite() || ((Float) value).isNaN())
				return "null";
			else
				return value.toString();
		}

		if (value instanceof Number)
			return value.toString();

		if (value instanceof Boolean)
			return value.toString();

		if ((value instanceof JsonAware))
			return ((JsonAware) value).toJsonString();

		if (value instanceof Map)
			return JsonObject.toJsonString((Map<?, ?>) value);

		if (value instanceof List)
			return JsonArray.toJsonString((List<?>) value);

		return value.toString();
	}

	public static String escape(String s) {
		if (s == null)
			return null;
		StringBuffer sb = new StringBuffer();
		escape(s, sb);
		return sb.toString();
	}

	static void escape(String s, StringBuffer sb) {
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				// Reference: http://www.unicode.org/versions/Unicode5.1.0/
				if ((ch >= '\u0000' && ch <= '\u001F') || (ch >= '\u007F' && ch <= '\u009F')
						|| (ch >= '\u2000' && ch <= '\u20FF')) {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		} // for
	}

}