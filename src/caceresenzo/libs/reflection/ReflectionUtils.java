package caceresenzo.libs.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtils {
	
	public static Set<Field> findFields(Class<?> sourceClass, Class<? extends Annotation> annotationClass) {
		Set<Field> set = new HashSet<>();
		
		Class<?> clazz = sourceClass;
		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(annotationClass)) {
					set.add(field);
				}
			}
			
			clazz = clazz.getSuperclass();
		}
		
		return set;
	}
	
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
	
	public static void removeFinalProtection(Field field) throws NoSuchFieldException, IllegalAccessException {
		field.setAccessible(true);
		
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	}

	public static boolean silentlyRemoveFinalProtection(Field field) {
		try {
			removeFinalProtection(field);
			
			return true;
		} catch (Exception exception) {
			return false;
		}
	}
	
	public static void setFinal(Field field, Object object, Object value) throws NoSuchFieldException, IllegalAccessException {
		removeFinalProtection(field);
		
		field.set(object, value);
	}
	
}