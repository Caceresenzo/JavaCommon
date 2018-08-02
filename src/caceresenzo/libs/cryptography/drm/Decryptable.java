package caceresenzo.libs.cryptography.drm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public abstract class Decryptable {
	/**
	 * AES Encrypt specified function.
	 * Return The specified AESKey, if return null, means no AES Key was used.
	 */
	public abstract AESEncryptKeys getAESKeys();

	public InputStream createDecryptStream(File inFile) throws NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, IOException {

		if (getAESKeys() == null)
			return new FileInputStream(inFile);

		InputStream fis = new FileInputStream(inFile);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		AESEncrypter encrypter = getAESKeys().getEncrypter(inFile.getName());
		encrypter.decrypt(fis, out);
		fis = new ByteArrayInputStream(out.toByteArray());
		return fis;
	}

	public InputStream createDecryptStream(String filePath) throws NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, IOException {
		File file = new File(filePath);
		return createDecryptStream(file);
	}

}