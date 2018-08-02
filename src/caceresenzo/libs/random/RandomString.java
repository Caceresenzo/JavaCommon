package caceresenzo.libs.random;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * Create an insecure generator for 8-character identifiers: <code>
 * RandomString gen = new RandomString(8, ThreadLocalRandom.current());
 * </code>
 * 
 * Create a secure generator for session identifiers: <code>
 * RandomString session = new RandomString();
 * </code>
 * 
 * Create a generator with easy-to-read codes for printing. The strings are longer than full alphanumeric strings to compensate for using fewer symbols: <code>
 * String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
 * RandomString tickets = new RandomString(23, new SecureRandom(), easy);
 * </code>
 */
public class RandomString {
	
	/**
	 * Generate a random string.
	 */
	public String nextString() {
		for (int idx = 0; idx < buffer.length; ++idx) {
			buffer[idx] = symbols[random.nextInt(symbols.length)];
		}
		return new String(buffer);
	}
	
	public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static final String LOWER = UPPER.toLowerCase(Locale.ROOT);
	
	public static final String DIGITS = "0123456789";
	
	public static final String ALPHANUM = UPPER + LOWER + DIGITS;
	
	private final Random random;
	
	private final char[] symbols;
	
	private final char[] buffer;
	
	public RandomString(int length, Random random, String symbols) {
		if (length < 1) {
			throw new IllegalArgumentException();
		}
		if (symbols.length() < 2) {
			throw new IllegalArgumentException();
		}
		this.random = Objects.requireNonNull(random);
		this.symbols = symbols.toCharArray();
		this.buffer = new char[length];
	}
	
	/**
	 * Create an alphanumeric string generator.
	 */
	public RandomString(int length, Random random) {
		this(length, random, ALPHANUM);
	}
	
	/**
	 * Create an alphanumeric strings from a secure generator.
	 */
	public RandomString(int length) {
		this(length, new SecureRandom());
	}
	
	/**
	 * Create session identifiers.
	 */
	public RandomString() {
		this(21);
	}
	
}