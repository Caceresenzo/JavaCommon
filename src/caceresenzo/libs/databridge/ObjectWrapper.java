package caceresenzo.libs.databridge;

/**
 * This class is for some native class which be declared as 'final' so that can't change it's initial value.</br>
 * It is extremely useful while 'final' keyword troubles you while using anonymous class.
 * 
 * @param <T>
 *            Target native class.
 */
public class ObjectWrapper<T> {
	
	/* Variable */
	private T object;
	
	/* Constructor */
	public ObjectWrapper(T object) {
		this.object = object;
	}
	
	/** @return Wrapped object. */
	public T get() {
		return object;
	}
	
	/**
	 * Set the new wrapped object.
	 * 
	 * @param object
	 *            New object.
	 */
	public void set(T object) {
		this.object = object;
	}
	
}