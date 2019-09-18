package caceresenzo.libs.list;

import java.util.Iterator;
import java.util.List;

import caceresenzo.libs.random.Randomizer;

public class ListUtils {
	
	/* Constructor */
	private ListUtils() {
		throw new IllegalStateException("Class can't be instanced.");
	}
	
	/**
	 * Get a random element from a {@link List list}.
	 * 
	 * @param <T>
	 *            {@link List}'s type parameter.
	 * @param list
	 *            Target {@link List}.
	 * @return A random item from the {@link List list}. Or <code>null</code> if the {@link List list} is empty or <code>null</code>.
	 */
	public static <T> T getRandomItem(List<T> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		
		return list.get(Randomizer.nextRangeInt(0, list.size() - 1));
	}
	
	/**
	 * Get the lastest item of a {@link List}.
	 * 
	 * @param <T>
	 *            {@link List}'s type parameter.
	 * @param list
	 *            Target {@link List}.
	 * @return <code>null</code> if the <code>list</code> is <code>null</code> or empty. Or the lastest item got from the index size - 1.
	 */
	public static <T> T getLastestItem(List<T> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		
		return list.get(list.size() - 1);
	}
	
	/**
	 * Same as {@link #separate(List, String)} but with "<code>, </code>" as the separator.
	 * 
	 * @param list
	 *            Target {@link List} to be separated.
	 * @return A {@link String} containing all item separated.<br>
	 *         Or null if the list is <code>null</code> or empty.
	 * @see #separate(List, String)
	 */
	public static String separate(List<?> list) {
		return separate(list, ", ");
	}
	
	/**
	 * Separate each element in the <code>list</code> with a specified <code>separator</code>.
	 * 
	 * @param list
	 *            Target {@link List} to be separated.
	 * @param seperator
	 *            {@link String} that will be added between each item.
	 * @return A {@link String} containing all item separated.<br>
	 *         Or null if the list is <code>null</code> or empty.
	 */
	public static String separate(List<?> list, String seperator) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		Iterator<?> iterator = list.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			
			builder.append(object);
			
			if (iterator.hasNext()) {
				builder.append(seperator);
			}
		}
		
		return builder.toString();
	}
	
}