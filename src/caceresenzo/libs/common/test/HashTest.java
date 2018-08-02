package caceresenzo.libs.common.test;

import java.security.NoSuchAlgorithmException;

import caceresenzo.libs.cryptography.MD5;
import caceresenzo.libs.cryptography.SHA;
import caceresenzo.libs.logger.Logger;

public class HashTest {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		String password = "hello";
		
		String md5 = MD5.generateStringMD5(password);
		
		String sha = SHA.generateStringSHA256(md5);

		Logger.$("password: " + password);
		Logger.$("md5: " + md5);
		Logger.$("md5.length: " + md5.length());
		Logger.$("sha: " + sha);
		Logger.$("sha.length: " + sha.length());
	}

}