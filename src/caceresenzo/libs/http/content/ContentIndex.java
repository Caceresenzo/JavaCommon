package caceresenzo.libs.http.content;

import java.io.IOException;

import caceresenzo.libs.io.IOUtils;

public class ContentIndex {
	
	public static String readFileStream(String folder, String file) {
		try {
			return IOUtils.readString(ContentIndex.class.getResourceAsStream(folder + "/" + file), "UTF-8");
		} catch (IOException exception) {
			return "";
		}
	}
	
}