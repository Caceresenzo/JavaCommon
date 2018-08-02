package caceresenzo.libs.common.test;

import caceresenzo.libs.logger.Logger;
import caceresenzo.libs.math.MathUtils;

public class MathsTest {
	public static void main(String[] args) {
		
		double math = MathUtils.round(((float) 5524 * 1024 / 1048576L), 1);
		
		Logger.$(((float) 5524 * 1024 / 1048576L));
		Logger.$(math);
	}
}