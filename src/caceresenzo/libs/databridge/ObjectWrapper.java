package caceresenzo.libs.databridge;

/**
 * This class is for some native class which be declared as 'final' so that can't change it's initial value.</br>
 * It is extremely useful while 'final' keyword troubles you while using anonymous class.
 * 
 * @param <T>
 *            target native class
 */
public class ObjectWrapper<T> {
	@SuppressWarnings("unchecked")
	T targetObject = (T) new Object();

	public ObjectWrapper(T value) {
		targetObject = value;
	}

	public T getValue() {
		return targetObject;
	}

	public void setValue(T value) {
		targetObject = value;
	}

}