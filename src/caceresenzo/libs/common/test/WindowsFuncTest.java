package caceresenzo.libs.common.test;

import java.awt.AWTException;
import java.awt.TrayIcon.MessageType;

import caceresenzo.libs.os.exception.InvalidOSException;
import caceresenzo.libs.os.exception.UnsupportedOSActionException;
import caceresenzo.libs.os.windows.WindowsToast;

public class WindowsFuncTest {
	
	public static void main(String[] args) throws UnsupportedOSActionException, AWTException, InvalidOSException {
		WindowsToast.make("Error", "Hello World from Java", MessageType.WARNING).show();
		
		System.exit(0);
	}
	
}
