package caceresenzo.libs.common.test;

import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKey;

import caceresenzo.libs.cryptography.AES;
import caceresenzo.libs.logger.Logger;

public class LoggerTest {

	public static void main(String[] args) throws Exception {
//		Logger.getLogPanel();
//		Logger.openLogWindow(true);

		Logger.success("Its success");
		Logger.info("Its info");
		Logger.warning("Its warning");
		Logger.error("Its error");
		Logger.critical("Its critical");
		Logger.debug("Its debug");
		
		SecretKey key = AES.generateKey(-1);
		Logger.info("Key: " + Base64.getEncoder().encodeToString(key.getEncoded()));
		
		String encrypt = AES.encrypt("Hi mate, i love you", key);
		Logger.info("encrypt: " + encrypt);
		
		String decrypt = AES.decrypt(encrypt, key);
		Logger.info("decrypt: " + decrypt);
		
		// Random logger test
		Random random = new Random();
		boolean active = true;
		while (active) {
			switch (random.nextInt(6)) {
				case 0:
					Logger.success("Success:" + random.nextLong());
					break;
				case 1:
					Logger.info("Info:" + random.nextLong());
					break;
				case 2:
					Logger.warning("Warning:" + random.nextLong());
					break;
				case 3:
					Logger.error("Error:" + random.nextLong());
					break;
				case 4:
					Logger.critical("Critical:" + random.nextLong());
					break;
				case 5:
					Logger.fatal("Fatal:" + random.nextLong());
					break;
				case 6:
					Logger.debug("Debug:" + random.nextLong());
					break;
				case 8:
					active = false;
					break;
			}
			Thread.sleep(100L);
		}
	}

}
