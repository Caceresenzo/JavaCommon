package caceresenzo.libs.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import caceresenzo.libs.exception.FileSystemException;
import caceresenzo.libs.os.OS;
import caceresenzo.libs.os.OSUtils;

public class FileSystemUtils {

	/**
	 * Split one file into several parts, and each part size not larger than max size set with param.<br/>
	 * Part file name is add a number to origin file name. Number start with 0.<br/>
	 * eg. "test.tmp" split to 3 parts, names are : test.tmp.0 , test.tmp.1 , test.tmp.2
	 *
	 * @param file
	 *            The file to split.
	 * @param partMaxSize
	 *            A part max size(byte).
	 * @return Split to how many parts.
	 * @throws FileSystemException
	 *             Read or write file error.
	 */
	public static int splitFile(File file, int partMaxSize) throws FileSystemException {
		return splitFileToFiles(file, partMaxSize).size();
	}

	/**
	 * Split one file into several parts, and each part size not larger than max size set with param.<br/>
	 * Part file name is add a number to origin file name. Number start with 0.<br/>
	 * eg. "test.tmp" split to 3 parts, names are : test.tmp.0 , test.tmp.1 , test.tmp.2
	 *
	 * @param file
	 *            The file to split.
	 * @param partMaxSize
	 *            A part max size(byte).
	 * @return Split file parts list.
	 * @throws FileSystemException
	 *             Read or write file error.
	 */
	public static List<File> splitFileToFiles(File file, int partMaxSize) throws FileSystemException {
		List<File> fileParts = new ArrayList<File>();

		String directoryPath = file.getParentFile().getAbsolutePath();
		String fileName = file.getName();
		String partFilePrefix = directoryPath + File.separator + fileName;
		int num = 0;
		InputStream inputStream = null;
		byte[] buffer = new byte[partMaxSize];
		try {
			inputStream = new FileInputStream(file);
			int realLen;

			while ((realLen = inputStream.read(buffer, 0, partMaxSize)) != -1) {
				OutputStream outputStream = null;
				try {
					File filePart = new File(partFilePrefix + "." + num);
					outputStream = new FileOutputStream(filePart);
					outputStream.write(buffer, 0, realLen);
					outputStream.flush();
					fileParts.add(filePart);
				} finally {
					if (outputStream != null) {
						try {
							outputStream.close();
						} catch (IOException ignored) {
						}
					}
				}
				num++;
			}

			return fileParts;
		} catch (IOException exception) {
			throw new FileSystemException(exception.toString());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	public static void combineFile(File targetCombineFile, int count) throws FileSystemException {
		String directoryPath = targetCombineFile.getParentFile().getAbsolutePath();
		String fileName = targetCombineFile.getName();
		String partFilePrefix = directoryPath + File.separator + fileName;

		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(targetCombineFile);

			for (int i = 0; i < count; i++) {
				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream(new File(partFilePrefix + "." + i));
					byte[] buffer = new byte[inputStream.available()];
					int length = inputStream.read(buffer);
					if (length >= 0) {
						outputStream.write(buffer, 0, length);
					}
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException ignored) {
						}
					}
				}
				if ((i + 1) % 5 == 0) {
					outputStream.flush();
				}
			}
			outputStream.flush();
		} catch (IOException exception) {
			throw new FileSystemException(exception.toString());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ignored) {
				}
			}
		}
	}
	
	   /**
     * Make one directory if it not exists.
     * And check if it can write(create sub-dir or sub-file).
     * If not, make it writable.
     *
     * @param directory The directory need to create and check.
     * @throws FileSystemException If create dir or make it writable error.
     */
    public static void makeWritableDir(File directory) throws FileSystemException {
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success) {
                throw new FileSystemException(directory, "Create directory failed.");
            }
        }
        OS osType = OSUtils.checkOSType();
        if (osType == OS.LINUX) {
            boolean success = directory.setWritable(true, false);
            if (!success) {
                throw new FileSystemException(directory, "Set directory writable failed.");
            }
        }
    }

}
