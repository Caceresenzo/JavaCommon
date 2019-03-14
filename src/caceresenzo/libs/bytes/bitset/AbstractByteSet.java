package caceresenzo.libs.bytes.bitset;

import java.io.Serializable;

public abstract class AbstractByteSet<T extends Number> implements Serializable {
	
	protected T value;
	
	public AbstractByteSet(T mask) {
		this.value = mask;
	}
	
	public abstract T set(int index, boolean flag);
	
	public boolean get(int index) {
		return ((int) ((Object) value) & 1 << index) != 0;
	}
	
	public T getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[mask=" + value + "]";
	}
	
}