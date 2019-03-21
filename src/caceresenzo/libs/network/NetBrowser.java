package caceresenzo.libs.network;

import javax.swing.JOptionPane;

@Deprecated
public class NetBrowser {

	/**
	 * Opens the specified web page in the user's default browser
	 */
	public static void openURL(String url) {
		try { // attempt to use Desktop library from JDK 1.6+ (even if on 1.5)
			Class<?> desktopClass = Class.forName("java.awt.Desktop");
			desktopClass.getDeclaredMethod("browse", new Class[] { java.net.URI.class }).invoke(desktopClass.getDeclaredMethod("getDesktop").invoke(null), new Object[] { java.net.URI.create(url) });
			// java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
		} catch (Exception ignore) { // library not available or failed
			String osName = System.getProperty("os.name");
			try {
				if (osName.startsWith("Mac OS")) {
					Class.forName("com.apple.eio.FileManager").getDeclaredMethod("openURL", new Class[] { String.class }).invoke(null, new Object[] { url });
				} else if (osName.startsWith("Windows"))
					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
				else { // assume Unix or Linux
					boolean found = false;
					for (String browser : new String[] { "google-chrome", "firefox", "opera", "konqueror", "epiphany", "seamonkey", "galeon", "kazehakase", "mozilla" })
						if (!found) {
							found = Runtime.getRuntime().exec(new String[] { "which", browser }).waitFor() == 0;
							if (found) {
								Runtime.getRuntime().exec(new String[] { browser, url });
							}
						}
					if (!found) {
						throw new Exception("Browser not found");
					}
				}
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(null, "Error attempting to launch web browser" + "\n" + exception.toString());
			}
		}
	}

}
