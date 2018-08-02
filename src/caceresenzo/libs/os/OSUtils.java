package caceresenzo.libs.os;

public class OSUtils {

	/**
	 * Check current using witch os type.
	 */
	public static OS checkOSType() {
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().startsWith("win")) {
			return OS.WINDOWS;
		} else if (osName.toLowerCase().startsWith("mac")) {
			return OS.MAC;
		} else if (osName.toLowerCase().startsWith("lin")) {
			return OS.LINUX;
		} else {
			return OS.UNKNOWN;
		}
	}

}