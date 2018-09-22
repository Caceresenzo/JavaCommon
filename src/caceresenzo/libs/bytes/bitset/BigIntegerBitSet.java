package caceresenzo.libs.bytes.bitset;

import java.math.BigInteger;

public class BigIntegerBitSet extends AbstractByteSet<BigInteger> {
	
	public BigIntegerBitSet(BigInteger value) {
		super(value);
	}
	
	@Override
	public BigInteger set(int index, boolean flag) {
		return value = flag ? value.setBit(index) : value.clearBit(index);
	}
	
	@Override
	public boolean get(int index) {
		return value.testBit(index);
	}
	
	public static BigIntegerBitSet fromHex(String hex) {
		return new BigIntegerBitSet(new BigInteger(hex, 16));
	}
	
}