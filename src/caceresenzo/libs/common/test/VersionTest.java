package caceresenzo.libs.common.test;

import caceresenzo.libs.comparator.Version;
import caceresenzo.libs.comparator.VersionType;
import caceresenzo.libs.logger.Logger;

public class VersionTest {
	
	private static final Version VERSION = new Version("3.0.0", VersionType.BETA);
	
	public static void main(String[] args) {
		Version update = new Version("2.0.0", VersionType.BETA);
		
		Logger.info(update.compareTo(VERSION));
	}
	
}
