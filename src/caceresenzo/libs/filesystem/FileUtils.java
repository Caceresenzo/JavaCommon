package caceresenzo.libs.filesystem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import caceresenzo.libs.logger.Logger;

public class FileUtils {
	
	public static String getExtension(String filename) {
		String extension = "";
		int dotPos = filename.lastIndexOf(".");
		if (dotPos >= 0) {
			extension = filename.substring(dotPos);
		}
		return extension.toLowerCase();
	}
	
	public static String getExtension(File file) {
		return getExtension(file.getName());
	}
	
	public static String replaceIllegalChar(String name) {
		return name.replaceAll("[\\\\\\\\/:*?\\\"<>|]", "_");
	}
	
	public static boolean forceFolderCreation(File file) throws IOException {
		if (file.isDirectory() && file.exists()) {
			return true;
		}
		
		if (file.isFile()) {
			if (!file.delete()) {
				throw new IOException("Folder is a file that can't be deleted, can't continue.");
			}
		}
		
		if (!file.exists()) {
			file.mkdirs();
			return true;
		}
		
		return false;
	}
	
	public static void writeStringToFile(String string, String filename) throws IOException {
		File file = new File(filename);
		writeStringToFile(string, file);
	}
	
	public static void writeStringToFile(String string, File file) throws IOException {
		InputStream stream = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
		writeStreamToFile(stream, file);
	}
	
	public static void writeStreamToFile(InputStream stream, String filename) throws IOException {
		File file = new File(filename);
		writeStreamToFile(stream, file);
	}
	
	public static void writeStreamToFile(InputStream stream, File file) throws IOException {
		if (file.getParentFile() != null) {
			file.getParentFile().mkdirs();
		}
		
		if (file.exists()) {
			file.delete();
		}
		
		FileOutputStream out = new FileOutputStream(file);
		
		byte[] buffer = new byte[8192];
		int length;
		while ((length = stream.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		
		out.flush();
		out.close();
	}
	
	public static boolean renameFileOrDirectory(File oldFile, File target) throws IOException {
		if (target.exists()) {
			throw new IOException("File \"" + target.getName() + "\" already exists, can't rename!");
		}
		
		return oldFile.renameTo(target);
	}
	
	public static boolean copyFile(String source, String destination) {
		return copyFile(source, destination, null);
	}
	
	public static boolean copyFile(String source, String destination, OnCopyProgress onCopyProgress) {
		try {
			File sourceFile = new File(source);
			File destinationFile = new File(destination);
			
			InputStream in = new FileInputStream(sourceFile);
			OutputStream out = new FileOutputStream(destinationFile);
			
			byte[] buffer = new byte[1024];
			long totalLength = in.available(), progress = 0;
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
				if (onCopyProgress != null) {
					progress += length;
					onCopyProgress.onProgress(totalLength, progress);
				}
			}
			
			in.close();
			out.close();
		} catch (FileNotFoundException fileNotFoundException) {
			Logger.exception(fileNotFoundException);
			return false;
		} catch (IOException ioException) {
			Logger.exception(ioException);
			return false;
		}
		return true;
	}
	
	public static void copyFolder(File source, File destination) throws IOException {
		if (source.isDirectory()) {
			
			if (!destination.exists()) {
				destination.getParentFile().mkdirs();
				destination.mkdir();
			}
			
			String files[] = source.list();
			
			for (String file : files) {
				File sourceFile = new File(source, file);
				File destinationFile = new File(destination, file);
				
				copyFolder(sourceFile, destinationFile);
			}
		} else {
			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(destination);
			
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			
			in.close();
			out.close();
		}
	}
	
	public static boolean deleteTree(String filePath) {
		return deleteTree(new File(filePath));
	}
	
	public static boolean deleteTree(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteTree(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
	
	public static byte[] createChecksum(File f) throws Exception {
		InputStream in = new FileInputStream(f);
		
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int read;
		
		do {
			read = in.read(buffer);
			if (read > 0) {
				complete.update(buffer, 0, read);
			}
		} while (read != -1);
		
		in.close();
		return complete.digest();
	}
	
	public static byte[] createChecksum(String filename) throws Exception {
		return createChecksum(new File(filename));
	}
	
	public static String getMD5Checksum(File file) throws Exception {
		byte[] checksum = createChecksum(file);
		String result = "";
		
		for (int i = 0; i < checksum.length; i++) {
			result += Integer.toString((checksum[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
	
	public static String getMD5Checksum(String filename) throws Exception {
		return getMD5Checksum(new File(filename));
	}
	
	public static interface OnCopyProgress {
		void onProgress(long totalLength, long progress);
	}
	
}
