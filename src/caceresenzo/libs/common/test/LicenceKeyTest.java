package caceresenzo.libs.common.test;

import caceresenzo.libs.licencekey.LicenceKeyGenerator;
import caceresenzo.libs.licencekey.LicenceKey;

public class LicenceKeyTest {
	
	public static void main(String[] args) {
		// S6SY-9A1M-W751-SMG1-9TSS
		// K5KK-JCNI-5KR3-KN49-I73T
		
		LicenceKeyGenerator keyGenerator = new LicenceKeyGenerator();
		
		LicenceKey licenceKey = keyGenerator.generateKey();
		
		$("licenceKey: " + licenceKey.getKey());
		$("valid: " + licenceKey.isValid());
		
		LicenceKey licenceKeyWithString = LicenceKey.fromString("K5KK-JCNI-5KR3-KN49-I73T").verify();
		
		$("licenceKey: " + licenceKeyWithString.getKey());
		$("valid: " + licenceKeyWithString.isValid());
	}
	
	public static void $(Object message) {
		System.out.println(String.valueOf(message));
	}
	
}