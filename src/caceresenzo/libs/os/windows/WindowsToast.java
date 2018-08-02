package caceresenzo.libs.os.windows;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import caceresenzo.libs.os.OS;
import caceresenzo.libs.os.OSUtils;
import caceresenzo.libs.os.exception.InvalidOSException;
import caceresenzo.libs.os.exception.UnsupportedOSActionException;

public class WindowsToast {
	
	private String title = "Information", message = "Message", toolTip = "SystemTray Icon";
	private MessageType messageType = MessageType.INFO;
	private Image icon = Toolkit.getDefaultToolkit().createImage("icon.png");
	
	/**
	 * 
	 * @param title
	 * @param message
	 * @param toolTip
	 * @param messageType
	 * @param icon
	 *            Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png")); (if in classpath)
	 */
	public WindowsToast(String title, String message, String toolTip, MessageType messageType, Image icon) {
		if (title != null) {
			this.title = title;
		}
		if (message != null) {
			this.message = message;
		}
		if (toolTip != null) {
			this.toolTip = toolTip;
		}
		if (messageType != null) {
			this.messageType = messageType;
		}
		if (icon != null) {
			this.icon = icon;
		}
	}
	
	public void show() throws UnsupportedOSActionException, InvalidOSException, AWTException {
		if (!SystemTray.isSupported()) {
			throw new UnsupportedOSActionException("SystemTray not supported!");
		}
		if (OSUtils.checkOSType() != OS.WINDOWS) {
			throw new InvalidOSException("Supported only on windows!");
		}
		
		SystemTray tray = SystemTray.getSystemTray();
		TrayIcon trayIcon = new TrayIcon(icon, toolTip);
		trayIcon.setImageAutoSize(true);
		tray.add(trayIcon);
		trayIcon.displayMessage(title, message, messageType);
	}
	
	public static WindowsToast make(String message) {
		return new WindowsToast(null, message, null, null, null);
	}
	
	public static WindowsToast make(String title, String message) {
		return new WindowsToast(title, message, null, null, null);
	}
	
	public static WindowsToast make(String title, String message, MessageType messageType) {
		return new WindowsToast(title, message, null, messageType, null);
	}
	
	public static WindowsToast make(String title, String message, MessageType messageType, Image icon) {
		return new WindowsToast(title, message, null, messageType, icon);
	}
	
	public static WindowsToast make(String title, String message, Image icon) {
		return new WindowsToast(title, message, null, null, icon);
	}
	
	public static WindowsToast make(String message, Image icon) {
		return new WindowsToast(null, message, null, null, icon);
	}
	
}