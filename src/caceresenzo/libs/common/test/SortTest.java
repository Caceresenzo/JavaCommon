package caceresenzo.libs.common.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import caceresenzo.libs.sort.MapSorter;

public class SortTest {
	
	public static void main(String[] args) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("zthree", "=3");
		hashmap.put("xone", "=1");
		hashmap.put("yfour", "=4");
		hashmap.put("atwo", "=2");
		printMap(hashmap);
		hashmap = (HashMap<String, String>) MapSorter.sortHashMap(hashmap);
		printMap(hashmap);
	}
	
	public static void printMap(Map<String, String> map) {
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " => " + entry.getValue());
		}
		System.out.println("========================");
	}
	
}