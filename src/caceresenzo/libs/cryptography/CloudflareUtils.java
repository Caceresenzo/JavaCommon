package caceresenzo.libs.cryptography;

public class CloudflareUtils {
	
	/**
	 * Used: https://usamaejaz.com/cloudflare-email-decoding/
	 * 
	 * Exemple: <a href="/cdn-cgi/l/email-protection" class="__cf_email__" data-cfemail="543931142127353935313e352e7a373b39">[email&#160;protected]</a>
	 * 
	 * or: <a href="/cdn-cgi/l/email-protection#5f323a1f2a2c3e323e3a353e25713c3032"><i class="svg-icon email"></i></a>
	 * 
	 * @param encodedString
	 *            5f323a1f2a2c3e323e3a353e25713c3032 or 543931142127353935313e352e7a373b39
	 * @return Escaped email
	 */
	public static String decodeEmail(String encodedString) {
		String email = "";
		int r = Integer.parseInt(encodedString.substring(0, 2), 16), n;
		
		for (n = 2; (encodedString.length() - n) != 0; n += 2) {
			int charactere = Integer.parseInt(encodedString.substring(n, n + 2), 16) ^ r;
			email += charactere;
			// Logger.info("encodedString.substring(n, 2): " + encodedString.substring(n, n + 2) + ", n=" + n + ", encodedString: " + encodedString + ", char= " + c + ", email=" + email);
		}
		
		return email;
	}
	
}