package caceresenzo.libs.cryptography.drm;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class RSAEncrypter implements EncrypterBase {

	Cipher encrypter;
	Cipher decrypter;

	RSAPublicKey pubKey;
	RSAPrivateKey privKey;

	RSAEncrypter() {

	}

	static public RSAEncrypter createRandomRSAEncrypter() throws NoSuchPaddingException {
		RSAEncrypter ret = new RSAEncrypter();
		ret.initRandom();
		return ret;
	}

	static public RSAEncrypter createDecrypter(RSAPrivateKey key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		RSAEncrypter ret = new RSAEncrypter();
		ret.setPrivKey(key);
		return ret;
	}

	private void setPrivKey(RSAPrivateKey key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		privKey = key;
		getDecrypter().init(Cipher.DECRYPT_MODE, key);
	}

	static public RSAEncrypter createEncrypter(RSAPublicKey key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		RSAEncrypter ret = new RSAEncrypter();
		ret.setPubKey(key);
		return ret;
	}

	private void setPubKey(RSAPublicKey key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		pubKey = key;
		getEncrypter().init(Cipher.ENCRYPT_MODE, key);
	}

	private void initRandom() throws NoSuchPaddingException {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			KeyPair kp = kpg.generateKeyPair();

			pubKey = (RSAPublicKey) kp.getPublic();
			privKey = (RSAPrivateKey) kp.getPrivate();

			getEncrypter().init(Cipher.ENCRYPT_MODE, pubKey);
			getDecrypter().init(Cipher.DECRYPT_MODE, privKey);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	private Cipher getDecrypter() throws NoSuchAlgorithmException, NoSuchPaddingException {
		if (decrypter == null) {
			decrypter = Cipher.getInstance("RSA");
		}

		return decrypter;
	}

	private Cipher getEncrypter() throws NoSuchAlgorithmException, NoSuchPaddingException {
		if (encrypter == null) {
			encrypter = Cipher.getInstance("RSA");
		}

		return encrypter;
	}

	public RSAPublicKey getPubKey() {
		return pubKey;
	}

	public RSAPrivateKey getPrivKey() {
		return privKey;
	}

	@Override
	public byte[] encrypt(byte[] plain) {

		return null;
	}

	@Override
	public byte[] decrypt(byte[] encrypted) {
		return null;
	}

	@Override
	public void encrypt(InputStream in, OutputStream out) {

	}

	@Override
	public void decrypt(InputStream in, OutputStream out) {

	}

}
