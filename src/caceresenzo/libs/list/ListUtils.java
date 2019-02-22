package caceresenzo.libs.list;

import java.util.List;

import caceresenzo.libs.exception.UtilityClassCantBeInstanced;

public class ListUtils {
	
	/* Constructor */
	private ListUtils() {
		throw new UtilityClassCantBeInstanced();
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
	
}