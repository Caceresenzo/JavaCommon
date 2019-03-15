package caceresenzo.libs.databridge;

import java.lang.reflect.Field;
import java.util.Objects;

public class DebugBridge {
	
	/**
	 * This is a debug tool just simply output all 'public' fields.<br>
	 * Private and protect members are not supported yet.<br>
	 * 
	 * @param object
	 *            Target object to dump.
	 * @return String with all field / value, in format of 'somefield = 3'.
	 * @throws NullPointerException
	 *             If the {@link Object} is null.
	 * @throws RuntimeException
	 *             If the export failed.
	 */
	public static String attachDebugInfo(Object object) {
		Objects.requireNonNull(object, "Target object can't be null.");
		
		try {
			Field[] fields = object.getClass().getFields();
			
			StringBuilder strinngBuilder = new StringBuilder();
			for (Field field : fields) {
				strinngBuilder.append(field.getName() + " = " + field.get(object) + " ");
			}
			
			return strinngBuilder.toString();
		} catch (Exception exception) {
			throw new RuntimeException("Failed to export public fields.", exception);
		}
	}
	
}