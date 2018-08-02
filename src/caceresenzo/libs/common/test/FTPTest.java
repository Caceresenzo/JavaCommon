package caceresenzo.libs.common.test;

import java.io.IOException;

import caceresenzo.libs.ftp.FileTransferProtocol;
import caceresenzo.libs.logger.Logger;

public class FTPTest {

	public static void main(String[] args) {
//		Logger.getLogPanel();
//		Logger.openLogWindow(true);
		
		FileTransferProtocol ftp = new FileTransferProtocol();
		try {
			ftp.connect("speedtest.tele2.net");
			
			Logger.info("PDW> " + ftp.pwd());
			
			Logger.info("CWD> " + ftp.cwd("./upload/"));
		} catch (IOException ioException) {
			Logger.exception(ioException);
		}
	}

}
