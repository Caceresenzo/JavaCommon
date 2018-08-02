package caceresenzo.libs.thread;

public class ThreadUtils {
	
	public static boolean sleep(long time) {
		try {
			Thread.sleep(time);
			return true;
		} catch (InterruptedException ignored) {
			return false;
		}
	}
	
}