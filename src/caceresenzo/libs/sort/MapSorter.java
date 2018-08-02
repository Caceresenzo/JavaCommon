package caceresenzo.libs.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class MapSorter {
	
	public static <K, V> HashMap<K, V> sortHashMap(HashMap<K, V> hashMap) {
		return new HashMap<K, V>(new TreeMap<K, V>(hashMap));
	}
	
	public static <K, V> Map<K, V> sorthMapToTreeMap(Map<K, V> map) {
		return new TreeMap<K, V>(new TreeMap<K, V>(map));
	}
	
	@SuppressWarnings("rawtypes")
	public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map) {
		List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		
		Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Entry<K, V> entry1, Entry<K, V> entry2) {
				return entry1.getValue().compareTo(entry2.getValue());
			}
		});
		Map<K, V> sortedMap = new LinkedHashMap<K, V>();
		
		for (Map.Entry<K, V> entry : entries) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;
	}
	
}