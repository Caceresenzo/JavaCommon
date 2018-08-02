package caceresenzo.libs.comparator;

import org.apache.commons.lang3.StringUtils;

public class SimilarityUtils {

	/**
	 * Compute the similarity between two strings and provide a percentage,
	 * doesn't really matter in which order they are compared
	 * Return a value ranging from 0 to 100%
	 */
	public static int levenshteinPercentage(final String s0, final String s1) {
		final int value = StringUtils.getLevenshteinDistance(s0, s1);
		int percentage = (int) (100 - (float) value * 100 / (float) (s0.length() + s1.length()));
		return percentage;
	}

	/**
	 * Enjoy life, keep similarity matching as simple as possible.
	 */
	public static int britoshteinPercentage(final char[] char1, final char[] char2) {
		final int size = Math.min(char1.length, char2.length);
		int points = 0;
		int pointer = 0;
		for (int i = 0; i < size; i++) {
			if (char1[pointer] == char2[i]) {
				points++;
				pointer++;
			}
		}
		return (points * 100) / size;
	}

	public static int britoshteinPercentage2(final char[] char1, final char[] char2) {
		final int smallestSize = Math.min(char1.length, char2.length);
		int points = 0, pointer = 0;
		for (int index = 0; index < smallestSize; index++) {

			if (char1[index] == char2[index]) {
				points++;
				pointer++;
			} else if (char1[pointer] == char2[index]) {
				points++;
				pointer++;
			} else if (char1[index] == char2[pointer]) {
				points++;
				pointer++;
			}
		}
		return (points * 100) / smallestSize;
	}

	public static void main(String[] args) {
		// test two example strings where relevant characters are not repeated
		String a1 = "abcdefghijklmnopqrstuvwxyz0123456789";
		String a2 = "ab..-cdefghij..klmnopqrstuvwxyz0..123456789";

		String n1 = "Nuno Garcia da Silva Brito";
		String n2 = "Nun.o Garcia da Silva Brito";

		int result = britoshteinPercentage2(a1.toCharArray(), a2.toCharArray());
		System.out.println(result);
		result = britoshteinPercentage2(a2.toCharArray(), a1.toCharArray());
		System.out.println(result);

		result = britoshteinPercentage2(n1.toCharArray(), n2.toCharArray());
		System.out.println(result);
		result = britoshteinPercentage2(n2.toCharArray(), n1.toCharArray());
		System.out.println(result);
	}

}
