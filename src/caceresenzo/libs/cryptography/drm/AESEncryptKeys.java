package caceresenzo.libs.cryptography.drm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import caceresenzo.libs.string.StringUtils;

/**
 * Use for {@link ComicView#Initialize(String, String, AESEncryptKeys)} only.<BR>
 * Provide decryption service for ComicView.<BR
 * If you passed null into ComicView.initialize, ComicView will act as no decryption.<BR>
 */
public class AESEncryptKeys {

	String uuid;
	String sisdn;
	String magic;
	byte[] aesIv = new byte[] { 'm', 'j', 'i', 'k', 'y', 'g', 'r', 'f', 'd', 'e', '6', 'y', 'u', '8', 'i', 'n' };

	/**
	 * @param uuid
	 *            Part of encrypt keys, in android system, use IMEI instead.
	 * @param sisdn
	 *            Part of encrypt keys, in HamiBook, use login account name instead
	 * @param magic
	 *            Part of encrypt keys, provided in HamiBook global.
	 */
	public AESEncryptKeys(String uuid, String sisdn, String magic) {
		this.uuid = uuid;
		this.sisdn = sisdn;
		this.magic = magic;
	}

	/**
	 * For special propose only.
	 */
	public byte[] genAesKey(String filename) throws NoSuchAlgorithmException {
		// MD5 md5 = new MD5();
		String key = null;
		byte[] source = (uuid + filename + magic + sisdn).getBytes();
		MessageDigest digest = MessageDigest.getInstance("MD5");
		// digest.digest(source);

		byte[] md5Result = digest.digest(source);
		key = StringUtils.asHex(md5Result).toUpperCase().substring(0, 16);

		return key.getBytes();
	}

	/**
	 * For special propose only
	 * Return AES Internal Vector
	 */
	public byte[] getVector() {
		return aesIv;
	}

	public AESEncrypter getEncrypter(String filename) throws NoSuchAlgorithmException {
		return new AESEncrypter(genAesKey(filename), getVector());
	}

}