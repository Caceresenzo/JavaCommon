package caceresenzo.libs.bytes.bitset;

public class IntegerBitSet extends AbstractByteSet<Integer> {

	public IntegerBitSet(int mask) {
		super(mask);
	}

	@Override
	public Integer set(int index, boolean flag) {
		if (flag) {
			return value = (int) (value | 1L << index);
		} else {
			return value = (int) (value & ~(1L << index));
		}
	}
	
}