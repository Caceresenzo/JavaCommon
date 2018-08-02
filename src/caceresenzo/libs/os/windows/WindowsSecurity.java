package caceresenzo.libs.os.windows;

import java.io.File;
import java.io.IOException;

public class WindowsSecurity {
	
	public static boolean checkPrivileges() {
		File testPriv = new File("C:\\Program Files\\");
		if (!testPriv.canWrite()) {
			return false;
		}
		File fileTest = null;
		try {
			fileTest = File.createTempFile("test", ".dll", testPriv);
		} catch (IOException exception) {
			return false;
		} finally {
			if (fileTest != null) {
				fileTest.delete();
			}
		}
		return true;
	}
	
}
