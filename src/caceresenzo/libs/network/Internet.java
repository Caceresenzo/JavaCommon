package caceresenzo.libs.network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Internet {

	public static String getLocalIP() {
		String result = "";
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface current = interfaces.nextElement();
				if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
					continue;
				}
				Enumeration<InetAddress> addresses = current.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress currentAddress = addresses.nextElement();
					if (currentAddress.isLoopbackAddress()) {
						continue;
					}
					if (currentAddress instanceof Inet4Address) {
						result = result.concat(currentAddress.getHostAddress() + "\n");
					}
				}
			}
		} catch (Exception exception) {
			return null;
		}
		return result;
	}

}