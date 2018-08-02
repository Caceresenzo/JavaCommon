package caceresenzo.libs.common.test;

import caceresenzo.libs.random.RandomString;

public class RandomTest {
	
	public static void main(String[] args) {
		RandomString session = new RandomString(64);
		
		String random = session.nextString();
		System.out.println(random);
		System.out.println(random.length());
	}
	
}