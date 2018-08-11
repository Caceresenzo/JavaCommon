package caceresenzo.libs.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map.Entry;

public class Downloader {
	
	/**
	 * Downloads a file from the Internet to the disk
	 */
	public static void downloadFile(File file, String url, OnDownloadProgress onDownloadProgress) throws IOException {
		BufferedInputStream bufferedInputStream = null;
		FileOutputStream fileOutputStream = null;
		
		URL webpage = new URL(url);
		URLConnection urlConnection = webpage.openConnection();
		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1941.0 Safari/537.36");
		InputStream inputStream = urlConnection.getInputStream();
		
		bufferedInputStream = new BufferedInputStream(inputStream);
		fileOutputStream = new FileOutputStream(file);
		
		byte data[] = new byte[1024];
		int count, length = 0;
		while ((count = bufferedInputStream.read(data, 0, 1024)) != -1) {
			fileOutputStream.write(data, 0, count);
			length += count;
			if (onDownloadProgress != null) {
				onDownloadProgress.onProgress(length);
			}
		}
		bufferedInputStream.close();
		fileOutputStream.close();
	}
	
	public static boolean downloadFile(File file, String url) throws IOException {
		try {
			downloadFile(file, url, null);
			return true;
		} catch (IOException exception) {
			throw exception;
		}
	}
	
	public static int getFileSize(String url) throws IOException {
		URL webpage = new URL(url);
		URLConnection urlConnection = webpage.openConnection();
		return urlConnection.getContentLength();
		// List<String> values = urlConnection.getHeaderFields().get("content-Length");
		// if (values != null && !values.isEmpty()) {
		// String length = (String) values.get(0);
		//
		// if (length != null) {
		// return ParseUtils.parseInt(length, -1);
		// }
		// }
		// return -1;
	}
	
	public static void downloadFileWithFileVerification(File file, String url) throws IOException {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		
		downloadFile(file, url);
	}
	
	/**
	 * Gets the contents of a file on the Internet into a string
	 */
	public static String getUrlContent(String targetUrl) throws IOException {
		String result = "";
		
		URL url = new URL(targetUrl);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1941.0 Safari/537.36");
		InputStream inputStream = urlConnection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		
		int numCharsRead;
		char[] charArray = new char[1024];
		StringBuilder stringBuilder = new StringBuilder();
		while ((numCharsRead = inputStreamReader.read(charArray)) > 0) {
			stringBuilder.append(charArray, 0, numCharsRead);
		}
		result = stringBuilder.toString();
		
		inputStream.close();
		inputStreamReader.close();
		stringBuilder = null;
		urlConnection = null;
		return result;
	}
	
	/**
	 * Get a given page from an URL address
	 */
	public static String webget(String address, HashMap<String, String> params, Charset charset) throws IOException {
		String result = "";
		
		URL webpage = new URL(address);
		HttpURLConnection urlConnection = (HttpURLConnection) webpage.openConnection();
		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1941.0 Safari/537.36");
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()) {
				if (entry.getKey().equals("setRequestMethod")) {
					urlConnection.setRequestMethod(entry.getValue());
				} else {
					urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
		}
		InputStream inputStream = urlConnection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
		BufferedReader in = new BufferedReader(inputStreamReader);
		
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			result = result.concat(inputLine);
		}
		in.close();
		
		return result;
	}
	
	public static String webget(String address, Charset charset) throws IOException {
		return webget(address, null, charset);
	}
	
	public static String webget(String address) throws IOException {
		return webget(address, null, Charset.defaultCharset());
	}
	
	public static interface OnDownloadProgress {
		void onProgress(int length);
	}
}