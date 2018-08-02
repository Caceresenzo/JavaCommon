package caceresenzo.libs.cryptography;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {

	public static synchronized String[] generateKeys(int BIT_SIZE) throws Exception {
		String[] bi = new String[3];

		SecureRandom secure = new SecureRandom();
		BigInteger p = new BigInteger(BIT_SIZE / 2, 100, secure);
		BigInteger q = new BigInteger(BIT_SIZE / 2, 100, secure);
		BigInteger n = p.multiply(q);
		BigInteger m = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger e = new BigInteger("3");
		while (m.gcd(e).intValue() > 1) {
			e = e.add(new BigInteger("2"));
		}
		BigInteger d = e.modInverse(m);

		bi[0] = e.toString();
		bi[1] = n.toString();
		bi[2] = d.toString();

		return bi;
	}

}