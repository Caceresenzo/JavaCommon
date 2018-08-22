package caceresenzo.libs.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {
	
	@SuppressWarnings("all")
	public static Object reflectMethod(Class<?> clazz, String method, Object instance, Class<?>[] parametersClass, Object... parameters) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method targetMethod = clazz.getDeclaredMethod(method, parametersClass);
		return targetMethod.invoke(instance, parameters);
	}
	
	public static Object reflectMethodIgnoreException(Class<?> clazz, String method, Object instance, Class<?>[] parametersClass, Object... parameters) {
		try {
			return reflectMethod(clazz, method, instance, parametersClass, parameters);
		} catch (Exception ignored) {
			return null;
		}
	}
	
}