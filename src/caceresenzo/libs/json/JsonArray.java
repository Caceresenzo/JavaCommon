package caceresenzo.libs.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonArray extends ArrayList<Object> implements List<Object>, JsonAware, JsonStreamAware {
	private static final long serialVersionUID = 3957988303675231981L;

	public static void writeJSONString(List<?> list, Writer out) throws IOException {
		if (list == null) {
			out.write("null");
			return;
		}

		boolean first = true;
		Iterator<?> iterator = list.iterator();

		out.write('[');
		while (iterator.hasNext()) {
			if (first) {
				first = false;
			} else {
				out.write(',');
			}

			Object value = iterator.next();
			if (value == null) {
				out.write("null");
				continue;
			}

			JsonValue.writeJsonString(value, out);
		}
		out.write(']');
	}

	public void writeJsonString(Writer out) throws IOException {
		writeJSONString(this, out);
	}

	public static String toJsonString(List<?> list) {
		if (list == null) {
			return "null";
		}

		boolean first = true;
		StringBuffer builder = new StringBuffer();
		Iterator<?> iterator = list.iterator();

		builder.append('[');
		while (iterator.hasNext()) {
			if (first) {
				first = false;
			} else {
				builder.append(',');
			}

			Object value = iterator.next();
			if (value == null) {
				builder.append("null");
				continue;
			}
			builder.append(JsonValue.toJsonString(value));
		}
		builder.append(']');
		return builder.toString();
	}

	public String toJsonString() {
		return toJsonString(this);
	}

	public String toString() {
		return toJsonString();
	}

}