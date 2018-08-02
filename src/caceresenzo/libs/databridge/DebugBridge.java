package caceresenzo.libs.databridge;

import java.lang.reflect.Field;

public class DebugBridge {

	/**
	 * This is a debug tool just simply output all 'public' fields.
	 * For a more advanced version to print out private/protect members, maybe in next release.
	 * Usage : String str = DebugBridge.attachDebugInfo(foo.getClass(), foo);
	 * 
	 * @param targetClass
	 *            .class target
	 * @param targetObj
	 *            target obj
	 * @return String with all field / value, in format of 'somefield = 3'
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static String attachDebugInfo(Class<?> targetClass, Object targetObj) throws IllegalArgumentException, IllegalAccessException {
		if (targetObj == null) {
			return "This is a null object!";
		}
		Field[] fields = targetClass.getFields();

		StringBuilder strinngBuilder = new StringBuilder();
		for (Field field : fields) {
			strinngBuilder.append(field.getName() + " = " + field.get(targetObj) + " ");
		}
		return strinngBuilder.toString();

	}

}