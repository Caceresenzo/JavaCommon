package caceresenzo.libs.common.test;

import caceresenzo.libs.cryptography.MD5;
import caceresenzo.libs.logger.Logger;
import caceresenzo.libs.test.SimpleTestUnits;

public class Md5Test extends SimpleTestUnits {
	
	public static void main(String[] args) {
		Logger.info(MD5.silentMd5("hello"));
	}
	
}