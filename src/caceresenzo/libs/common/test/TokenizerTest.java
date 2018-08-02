package caceresenzo.libs.common.test;

import caceresenzo.libs.token.SmartTokenizer;

public class TokenizerTest {
	

	public static void main(String args[]) {
		SmartTokenizer tokenizer = new SmartTokenizer("This,is,a,,test,", ",");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			System.out.println("#" + token + "#");
		}
	}
	
}
