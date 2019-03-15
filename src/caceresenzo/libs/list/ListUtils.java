package caceresenzo.libs.list;

import java.util.Iterator;
import java.util.List;

import com.sun.xml.internal.txw2.IllegalSignatureException;

public class ListUtils {
	
	/* Constructor */
	private ListUtils() {
		throw new IllegalSignatureException("Class can't be instanced.");
	}
	
	/**
	 * Get the lastest item of a {@link List}.
	 * 
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