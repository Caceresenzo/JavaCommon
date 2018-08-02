package caceresenzo.libs.cryptography.drm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public interface EncrypterBase {

	public abstract byte[] encrypt(byte[] plain);

	public abstract byte[] decrypt(byte[] encrypted) throws IllegalBlockSizeException, BadPaddingException;

	public abstract void encrypt(InputStream in, OutputStream out);

	public abstract void decrypt(InputStream in, OutputStream out) throws IOException, IllegalBlockSizeException, BadPaddingException;

}