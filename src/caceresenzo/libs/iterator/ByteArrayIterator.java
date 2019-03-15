package caceresenzo.libs.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Custom {@link Iterator} class to iterate {@link Byte}.
 * 
 * @author Enzo CACERES
 */
public class ByteArrayIterator implements Iterator<Byte> {
	
	private final byte[] array;
	private int index = 0;
	
	public ByteArrayIterator(byte[] bytes) {
		this.array = bytes;
	}
	
	@Override
	public boolean hasNext() {
		return index < array.length;
	}
	
	@Override
	public Byte next() {
		if (index == array.length) {
			throw new NoSuchElementException();
		}
		
		return array[index++];
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}