package caceresenzo.libs.random;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer extends Random {
	private static final long serialVersionUID = 1L;
	
	private static Randomizer randomizer = new Randomizer();
	
	public static Randomizer getRandomizer() {
		return randomizer;
	}
	
	public Randomizer() {
		super();
	}
	
	public Randomizer(long seed) {
		super(seed);
	}
	
	public int[] nextInt(int n, int size) {
		if (size > n) {
			size = n;
		}
		
		Set<Integer> set = new LinkedHashSet<Integer>();
		
		for (int i = 0; i < size; i++) {
			while (true) {
				Integer value = new Integer(nextInt(n));
				
				if (!set.contains(value)) {
					set.add(value);
					
					break;
				}
			}
		}
		int[] array = new int[set.size()];
		
		Iterator<Integer> iterator = set.iterator();
		
		for (int i = 0; i < array.length; i++) {
			array[i] = ((Integer) iterator.next()).intValue();
		}
		
		return array;
	}
	
	public void randomize(char array[]) {
		int length = array.length;
		
		for (int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			char y = array[i];
			
			array[i] = array[i + x];
			array[i + x] = y;
			
			length--;
		}
	}
	
	public void randomize(int array[]) {
		int length = array.length;
		
		for (int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			int y = array[i];
			
			array[i] = array[i + x];
			array[i + x] = y;
			
			length--;
		}
	}
	
	public <K> void randomize(List<K> list) {
		int size = list.size();
		
		for (int i = 0; i <= size; i++) {
			int j = nextInt(size);
			K object = list.get(i);
			
			list.set(i, list.get(i + j));
			list.set(i + j, object);
			
			size--;
		}
	}
	
	public void randomize(Object array[]) {
		int length = array.length;
		
		for (int i = 0; i < length - 1; i++) {
			int x = nextInt(length);
			Object y = array[i];
			
			array[i] = array[i + x];
			array[i + x] = y;
			
			length--;
		}
	}
	
	public String randomize(String string) {
		if (string == null) {
			return null;
		}
		
		char[] array = string.toCharArray();
		
		randomize(array);
		
		return new String(array);
	}
	
	public static int nextRangeInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static int randomInt(int min, int max) {
		return randomizer.nextInt(max - min) + min;
	}
	
	public static int advancedRandomRangeInteger(int min, int max) {
		final int number = (int) (Math.random() * (max + 1));
		return number < min ? advancedRandomRangeInteger(min, max) : number;
	}
	
	public static Color getRandomColor() {
		return new Color(randomizer.nextInt(255), randomizer.nextInt(255), randomizer.nextInt(255));
	}
	
}