package caceresenzo.libs.os;

public class OSUtils {
	
	/**
	 * Check current using witch os type.
	 * 
	 * @return Current OS, {@link OS#UNKNOWN} if not found.
	 */
	public static OS checkOSType() {
		String name = System.getProperty("os.name");
		
		if (name.toLowerCase().startsWith("win")) {
			return OS.WINDOWS;
		} else if (name.toLowerCase().startsWith("mac")) {
			return OS.MAC;
		} else if (name.toLowerCase().startsWith("lin")) {
			return OS.LINUX;
		} else {
			return OS.UNKNOWN;
		}
	}
	
}