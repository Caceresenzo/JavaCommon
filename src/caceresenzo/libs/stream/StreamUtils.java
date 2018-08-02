package caceresenzo.libs.stream;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/*
 * Code was gotten from http://www.kodejava.org/examples/266.html
 */
public class StreamUtils {
	
	/**
	 * @param inputStream
	 *            InputStream which needed to be converted
	 * @return String convert from InputStream, or "" if error occurs.
	 * @throws IOException
	 *             throw by BufferedReader.readLine
	 */
	public static String InputStreamToString(InputStream inputStream) throws IOException {
		return InputStreamToString(inputStream, false, true);
	}
	
	/**
	 * Another way to convert Stream to String.
	 * 
	 * @param is
	 * @param filterLastZeroPadding
	 * @param closeStream
	 * @return
	 * @throws IOException
	 */
	public static String InputStreamToString(InputStream in, boolean filterLastZeroPadding, boolean closeStream) throws IOException {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine() method. We iterate until the BufferedReader return null which means there's no more data to read. Each line will appended to a StringBuilder and returned as String.
		 */
		
		if (in != null) {
			StringBuilder builder = new StringBuilder();
			String line;
			try {
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				
				while ((line = reader.readLine()) != null) {
					if (filterLastZeroPadding == true) {
						int offset = line.indexOf(0);
						if (offset != -1) {
							line = line.substring(0, offset);
						}
					}
					builder.append(line).append("\n");
				}
				
			} finally {
				if (closeStream) {
					in.close();
				}
			}
			
			return builder.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * Get a input stream and write to multiple output stream, very useful while need T-pipe
	 * 
	 * @param bufferSize
	 *            default is 1024
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static final void duplicateStream(int bufferSize, InputStream in, OutputStream... outs) throws IOException {
		byte[] buffer = new byte[bufferSize];
		int length;
		
		while ((length = in.read(buffer)) >= 0) {
			for (OutputStream out : outs) {
				out.write(buffer, 0, length);
			}
		}
		
		for (OutputStream out : outs) {
			out.flush();
			out.close();
		}
		
		in.close();
	}
	
	/**
	 * Get a input stream and write to multiple output stream, very useful while need T-pipe
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static final void duplicateStream(InputStream in, OutputStream... out) throws IOException {
		duplicateStream(1024, in, out);
	}
	
	/**
	 * Directly adapt inputstream to outputstream
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
		copyInputStream(1024, in, out);
	}
	
	/**
	 * Directly adapt inputstream to outputstream
	 * 
	 * @param bufferSize
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static final void copyInputStream(int bufferSize, InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[bufferSize];
		int length;
		
		while ((length = in.read(buffer)) >= 0) {
			out.write(buffer, 0, length);
		}
		
		// EDIT : The close/flush responbility should relay on who creates them,
		// not in this snippet.
		
		// in.close();
		// out.flush();
		// out.close();
		
	}
	
	// TODO: Maybe it can integrate with InputStreamToString
	public static byte[] InputStreamToBytes(InputStream in) throws IOException {
		// TODO Auto-generated method stub
		return InputStreamToBytes(in, 4096, null);
	}
	
	public static byte[] InputStreamToBytes(InputStream in, int bufferSize, String terminator) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int read;
		byte[] data = new byte[bufferSize];
		
		while ((read = in.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, read);
		}
		
		buffer.flush();
		return buffer.toByteArray();
	}
	
	public static void closeOutputStream(OutputStream stream) throws IOException {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException exception) {
			throw new IOException(exception);
		}
	}
	
	public static void closeInputStream(InputStream stream) throws IOException {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException exception) {
			throw new IOException(exception);
		}
	}
	
	public static InputStream stringToInputStream(String string) {
		try {
			return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8.name()));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
