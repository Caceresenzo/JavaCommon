package caceresenzo.libs.codec;

import java.io.File;

public class ReadWriteCodec<T> {
	
	/* Constants */
	public static final String TAB = "\t";
	
	/**
	 * Read a {@link File} and process its content a this codec implementation.
	 * 
	 * @param file
	 *            Target {@link File} to read from.
	 * @return Paramerized type of the implementation.
	 * @throws Exception
	 *             If anything goes wrong.
	 * @throws UnsupportedOperationException
	 *             If the codec don't support to be read.
	 */
	public T read(File file) throws Exception {
		throw new UnsupportedOperationException("This Codec don't support reading.");
	}
	
	/**
	 * Write a content to a {@link File} with a codec implementation.
	 * 
	 * @param file
	 *            Target {@link File} to write to.
	 * @return Paramerized type of the implementation.
	 * @throws Exception
	 *             If anything goes wrong.
	 * @throws UnsupportedOperationException
	 *             If the codec don't support to be written.
	 */
	public void write(File file, T t) throws Exception {
		throw new UnsupportedOperationException("This Codec don't support writing.");
	}
	
}