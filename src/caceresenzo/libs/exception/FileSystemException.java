package caceresenzo.libs.exception;

import java.io.File;

@SuppressWarnings("serial")
public class FileSystemException extends Exception {

	public FileSystemException(String message) {
		super(message);
	}

	public FileSystemException(File errorFile, String message) {
		super(message + " (" + errorFile.getAbsolutePath() + ")");
	}

	public FileSystemException(String errorFile, String message) {
		super(message + " (" + errorFile + ")");
	}

	public FileSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileSystemException(Throwable cause) {
		super(cause);
	}

}