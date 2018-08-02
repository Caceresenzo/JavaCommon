package caceresenzo.libs.miscellaneous;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {

	/**
	 * Sort any collection and return a new list with it. Extremely useful while you need sorted map key/value pairs
	 */
	public static <T> List<T> asSortedList(Collection<T> c, Comparator<T> comparator) {
		List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list, comparator);
		return list;
	}

}