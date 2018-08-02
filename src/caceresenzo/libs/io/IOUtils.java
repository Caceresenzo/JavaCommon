package caceresenzo.libs.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public final class IOUtils {

	public static OutputStream copy(InputStream inputStream, OutputStream outputStream) throws IOException {
		int i = 0;
		byte[] buffer = new byte[65565];
		while ((i = inputStream.read(buffer, 0, buffer.length)) != -1) {
			outputStream.write(buffer, 0, i);
		}
		return outputStream;
	}

	public static OutputStream copy(InputStream inputStream, String output) throws FileNotFoundException, IOException {
		return copy(inputStream, new BufferedOutputStream(new FileOutputStream(output)));
	}

	public static String readString(InputStream in, String charset) throws UnsupportedEncodingException, IOException {
		return new String(read(in), charset);
	}

	public static byte[] read(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[65565];
		ByteArrayOutputStream ous = new ByteArrayOutputStream(buffer.length);
		int i = 0;
		while ((i = inputStream.read(buffer, 0, buffer.length)) != -1) {
			ous.write(buffer, 0, i);
		}
		ous.close();
		return ous.toByteArray();
	}

	public static void deleteFolderContents(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					deleteFolderContents(file);
					file.delete();
				} else {
					file.delete();
				}
			}
		}
	}
}