package caceresenzo.libs.cryptography;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

	public static String generateStringSHA1(final String content) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.update(content.getBytes());
		final byte byteData[] = digest.digest();

		final StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static String generateStringSHA256(final String content) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(content.getBytes());
		byte byteData[] = digest.digest();

		StringBuffer stringBuilder = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			stringBuilder.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
