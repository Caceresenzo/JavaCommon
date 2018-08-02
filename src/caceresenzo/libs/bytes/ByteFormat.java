package caceresenzo.libs.bytes;

import caceresenzo.libs.math.MathUtils;

public class ByteFormat {
	
	public static String toHumanBytes(long bytes) {
		return toHumanBytes(bytes, 2);
	}
	
	public static String toHumanBytes(long bytes, int prescision) {
		String result = String.valueOf(bytes);
		if (bytes < 1024L) {
			result = bytes + " B";
		} else if (bytes < 1048576L) {
			result = MathUtils.round(((float) bytes / 1024L), prescision) + " KiB";
		} else if (bytes < 1073741824L) {
			result = MathUtils.round(((float) bytes / 1048576L), prescision) + " MiB";
		} else if (bytes < 1073741824L * 1024L) {
			result = MathUtils.round(((float) bytes / 1073741824L), prescision) + " GiB";
		} else {
			result = MathUtils.round(((float) bytes / 1073741824L), prescision) + " GiB";
		}
		return result;
	}
	
}