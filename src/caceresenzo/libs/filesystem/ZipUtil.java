package caceresenzo.libs.filesystem;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import caceresenzo.libs.stream.StreamUtils;

public class ZipUtil {
	
	public static final int ZIP_OUTPUTSTREAM_BUFFER_SIZE = 8192;
	
	public static final void unzip(String filepath, String destinationDir) throws IOException {
		Enumeration<?> entries;
		ZipFile zipFile;
		
		zipFile = new ZipFile(filepath);
		entries = zipFile.entries();
		
		if (destinationDir.endsWith("/") == false) {
			destinationDir += "/";
		}
		
		File file = new File(destinationDir);
		if (file.exists() == false) {
			file.mkdirs();
		}
		
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) entries.nextElement();
			
			if (zipEntry.isDirectory()) {
				File directory = new File(destinationDir + zipEntry.getName());
				directory.mkdirs();
				continue;
			}
			
			String totalFile = destinationDir + zipEntry.getName();
			String targetPath = totalFile.substring(0, totalFile.lastIndexOf("/"));
			
			File fileDestination = new File(targetPath);
			fileDestination.mkdirs();
			
			StreamUtils.copyInputStream(zipFile.getInputStream(zipEntry), new BufferedOutputStream(new FileOutputStream(destinationDir + zipEntry.getName()), ZIP_OUTPUTSTREAM_BUFFER_SIZE));
		}
		
		zipFile.close();
	}
	
	public static final void unzip(InputStream stream, File destination) throws IOException {
		ZipInputStream zipStream = new ZipInputStream(stream);
		ZipEntry zipEntry = null;
		while ((zipEntry = zipStream.getNextEntry()) != null) {
			
			if (zipEntry.isDirectory()) {
				File directory = new File(destination.getPath() + "/" + zipEntry.getName());
				directory.mkdirs();
				continue;
			}
			
			String totalFile = destination.getPath() + "/" + zipEntry.getName();
			String targetPath = totalFile.substring(0, totalFile.lastIndexOf("/"));
			
			File fileDestination = new File(targetPath);
			fileDestination.mkdirs();
			
			FileOutputStream out = new FileOutputStream(new File(destination.getPath() + "/" + zipEntry.getName()));
			for (int c = zipStream.read(); c != -1; c = zipStream.read()) {
				out.write(c);
			}
			
			out.flush();
			out.close();
			
			StreamUtils.copyInputStream(zipStream, new BufferedOutputStream(new FileOutputStream(destination + zipEntry.getName()), ZIP_OUTPUTSTREAM_BUFFER_SIZE));
		}
	}
	
	public static final void extractZip(String filepath, String destinationDir) throws IOException {
		ZipFile zipFile = new ZipFile(filepath);
		Enumeration<?> enu = zipFile.entries();
		while (enu.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) enu.nextElement();
			
			String name = zipEntry.getName();
//			long size = zipEntry.getSize();
//			long compressedSize = zipEntry.getCompressedSize();
//			System.out.printf("[zip] file: %-30s | size: %7d | compressed: %7d\n", name, size, compressedSize);
			
			File file = new File(destinationDir + "/" + name);
			if (name.endsWith("/")) {
				file.mkdirs();
				continue;
			}
			
			File parent = file.getParentFile();
			if (parent != null) {
				parent.mkdirs();
			}
			
			// Extract the file
			InputStream is = zipFile.getInputStream(zipEntry);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = is.read(bytes)) >= 0) {
				fos.write(bytes, 0, length);
			}
			is.close();
			fos.close();
			
		}
		zipFile.close();
	}
	
}
