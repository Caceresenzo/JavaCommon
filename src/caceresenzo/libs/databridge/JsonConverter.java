package caceresenzo.libs.databridge;

import java.lang.reflect.Field;

import caceresenzo.libs.json.JsonObject;

public class JsonConverter {

	/**
	 * This is a generic JSON Converter, it will get all 'public' field and fetch data in JSONObject according to field name.
	 * SomeJAVABean bean = new SomeJAVABean(); </br>
	 * JSONConverter.extractFromJSON(bean.getClass(), bean, someJSONObject);
	 * 
	 * @param targetClass
	 *            bean .class
	 * @param targetObject
	 *            bean instance
	 * @param userObject
	 *            A json object for this bean
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void extractFromJSON(Class<?> targetClass, Object targetObject, JsonObject userObject) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = targetClass.getFields();
		for (Field field : fields) {
			field.set(targetObject, userObject.get(field.getName()));
		}
	}

}