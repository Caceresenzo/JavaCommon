package caceresenzo.libs.filesystem;

public class FilenameUtils {
	
	private static final int NOT_FOUND = -1;
	private static final char UNIX_SEPARATOR = '/';
	private static final char WINDOWS_SEPARATOR = '\\';
	
	public static String getName(final String filename) {
		if (filename == null) {
			return null;
		}
		failIfNullBytePresent(filename);
		final int index = indexOfLastSeparator(filename);
		return filename.substring(index + 1);
	}
	
	private static void failIfNullBytePresent(final String path) {
		final int len = path.length();
		for (int i = 0; i < len; i++) {
			if (path.charAt(i) == 0) {
				throw new IllegalArgumentException("Null byte present in file/path name. There are no " + "known legitimate use cases for such data, but several injection attacks may use it");
			}
		}
	}
	
	public static int indexOfLastSeparator(final String filename) {
		if (filename == null) {
			return NOT_FOUND;
		}
		final int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
		final int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
		return Math.max(lastUnixPos, lastWindowsPos);
	}
	
}