package caceresenzo.libs.common.test;

import java.io.File;
import java.io.IOException;

import caceresenzo.libs.logger.Logger;
import caceresenzo.libs.network.Downloader;
import caceresenzo.libs.network.Internet;

public class NetTest {
	
	public static void main(String... args) throws IOException {
		
		Logger.info("Local IP: " + Internet.getLocalIP());

		Logger.info("Webcontent of monip.org: " + Downloader.getUrlContent("http://monip.org"));
		Logger.info("Webcontent2 of monip.org: " + Downloader.webget("http://monip.org"));
		Logger.info("Download file too");
		Downloader.downloadFile(new File("monip.org.html"), "http://monip.org");
		
		
		
	}
	
	
}