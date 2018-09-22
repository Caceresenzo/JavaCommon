package caceresenzo.libs.bytes;

/**
 * Utility to easely use BitSet
 * 
 * @author Enzo CACERES
 */
public class BitSetUtils {
	
	private BitSetUtils() {
		throw new IllegalStateException("This can't be instancied.");
	}
	
	/**
	 * Change a value in a BitSet
	 * 
	 * @param mask
	 *            Target mask
	 * @param index
	 *            Index of the value you want to change
	 * @param flag
	 *            New value
	 * @return The modified mask
	 */
	public static int set(int mask, int index, boolean flag) {
		if (flag) {
			return (int) (mask | 1L << index);
		} else {
			return (int) (mask & ~(1L << index));
		}
	}
	
	/**
	 * Change a value in a BitSet
	 * 
	 * @param mask
	 *            Target mask
	 * @param index
	 *            Index of the value you want to change
	 * @param flag
	 *            New value
	 * @return The modified mask
	 */
	public static byte set(byte mask, int index, boolean flag) {
		if (flag) {
			return (byte) ((mask & 0xff) | 1L << index);
		} else {
			return (byte) (mask & ~(1L << index));
		}
	}
	
	/**
	 * Get a value in a BitSet
	 * 
	 * @param mask
	 *            Target mask
	 * @param index
	 *            Index of the value you want to get
	 * @return Value state
	 */
	public static boolean get(int mask, int index) {
		return (mask & 1 << index) != 0;
	}
	
}