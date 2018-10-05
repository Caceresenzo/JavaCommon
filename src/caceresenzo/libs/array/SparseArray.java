package caceresenzo.libs.array;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SparseArray<T> extends LinkedHashMap<Integer, T> {
	
	public int keyAt(int index) {
		return new ArrayList<>(keySet()).get(index);
	}
	
	public T append(int key, T value) {
		return put(key, value);
	}
	
}