package caceresenzo.libs.common.test;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import caceresenzo.libs.sort.MapSorter;

public class SortOnKeyTest {
	
	public static void main(String[] args) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("zthree", "=3");
		hm.put("xone", "=1");
		hm.put("yfour", "=4");
		hm.put("atwo", "=2");
		printMap(hm);
		// Map<String, String> treeMap = new TreeMap<String, String>(hm);
		hm = (HashMap<String, String>) MapSorter.sortHashMap(hm);
		printMap(hm);
	}// main
	
	public static void printMap(Map<String, String> map) {
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " => " + entry.getValue());
		}
		System.out.println("========================");
	}
	
}