package caceresenzo.libs.common.test;

import java.io.IOException;
import java.io.StringWriter;

import caceresenzo.libs.json.JsonObject;

public class JsonTest {

	public static void main(String[] args) {
		JsonObject obj = new JsonObject();

		obj.put("name", "foo");
		obj.put("num", new Integer(100));
		obj.put("balance", new Double(1000.21));
		obj.put("is_vip", new Boolean(true));

		//System.out.print(obj);

		StringWriter out = new StringWriter();
		try {
			obj.writeJsonString(out);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		String jsonText = out.toString();
		System.out.print(jsonText);
	}

}