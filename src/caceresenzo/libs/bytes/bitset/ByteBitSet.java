package caceresenzo.libs.bytes.bitset;

public class ByteBitSet extends AbstractByteSet<Byte> {
	
	public ByteBitSet(byte mask) {
		super(mask);
	}

	@Override
	public Byte set(int index, boolean flag) {
		if (flag) {
			return value = (byte) ((value & 0xff) | 1L << index);
		} else {
			return value = (byte) (value & ~(1L << index));
		}
	}
	
}