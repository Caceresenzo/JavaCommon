package caceresenzo.libs.common.test;

import caceresenzo.libs.bytes.ByteFormat;
import caceresenzo.libs.logger.Logger;

public class FormatTest {
	
	public static void main(String[] args) {
		Logger.$(": " + ByteFormat.toHumanBytes(46843518776L));
	}
	
}