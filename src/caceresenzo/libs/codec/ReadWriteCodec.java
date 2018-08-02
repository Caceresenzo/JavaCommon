package caceresenzo.libs.codec;

import java.io.File;

public class ReadWriteCodec<T> {
	
	public T read(File file) throws Exception {
		throw new UnsupportedOperationException("This Codec don't support reading.");
	}
	
	public void write(File file, T t) throws Exception {
		throw new UnsupportedOperationException("This Codec don't support writing.");
	}
	
}