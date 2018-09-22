package caceresenzo.libs.bytes.bitset;

public class LongBitSet extends AbstractByteSet<Long> {
	
	public LongBitSet(long value) {
		super(value);
	}
	
	@Override
	public boolean get(int index) {
		return ((long) value & 1L << index) != 0;
	}
	
	@Override
	public Long set(int index, boolean flag) {
		if (flag) {
			return value = (value | 1L << index);
		} else {
			return value = (value & ~(1L << index));
		}
	}
	
}