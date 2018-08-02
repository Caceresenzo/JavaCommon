package caceresenzo.libs.logger;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Logger {
	
	protected static SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss"), fileDateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
	
	protected static int maxLength = 10, staticLength = 1;
	protected static boolean useStaticLength = false, useClassOnly = true, addMethodName = false, exceptionFileReport = true;
	
	private static void log(LogLevel level, Object format, Object... args) {
		int depth = 3; // Thread.currentThread().getStackTrace().length - 1;
		String stackTrace = "NULL";
		if (useClassOnly) {
			stackTrace = Thread.currentThread().getStackTrace()[depth].getFileName().replace(".java", "");
		} else {
			stackTrace = Thread.currentThread().getStackTrace()[depth].getClassName();
		}
		if (addMethodName) {
			stackTrace += "[" + Thread.currentThread().getStackTrace()[depth].getMethodName() + "]";
		}
		
		if (useStaticLength) {
			maxLength = staticLength;
		}
		
		if (stackTrace.length() > maxLength) {
			if (!useClassOnly) {
				stackTrace = stackTrace.substring(stackTrace.length() - maxLength);
			} else {
				stackTrace = stackTrace.substring(0, maxLength - 3) + "...";
			}
		}
		
		// Help: https://stackoverflow.com/questions/3243721/how-to-get-the-last-characters-in-a-string-in-java-regardless-of-string-size
		
		System.out.println(String.format("%s | %-8s | %-" + maxLength + "s | %s", hourFormat.format(new Date()), level.getDisplayText(), stackTrace, args == null || args.length == 0 ? String.valueOf(format) : String.format(String.valueOf(format), args)));
		if (level == LogLevel.CRITICAL) {
			System.exit(99);
		}
	}
	
	public static void $(Object format, Object... args) {
		log(LogLevel.VOID, format, args);
	}
	
	public static void $(Object object) {
		log(LogLevel.VOID, object);
	}
	
	public static void success(Object format, Object... args) {
		log(LogLevel.SUCCESS, format, args);
	}
	
	public static void success(Object object) {
		log(LogLevel.SUCCESS, object);
	}
	
	public static void info(Object format, Object... args) {
		log(LogLevel.INFO, format, args);
	}
	
	public static void info(Object object) {
		log(LogLevel.INFO, object);
	}
	
	public static void warning(Object format, Object... args) {
		log(LogLevel.WARNING, format, args);
	}
	
	public static void warning(Object object) {
		log(LogLevel.WARNING, object);
	}
	
	public static void error(Object format, Object... args) {
		log(LogLevel.ERROR, format, args);
	}
	
	public static void error(Object object) {
		log(LogLevel.ERROR, object);
	}
	
	public static void critical(Object format, Object... args) {
		log(LogLevel.CRITICAL, format, args);
	}
	
	public static void critical(Object object) {
		log(LogLevel.CRITICAL, object);
	}
	
	public static void fatal(Object format, Object... args) {
		log(LogLevel.FATAL, format, args);
	}
	
	public static void fatal(Object object) {
		log(LogLevel.FATAL, object);
	}
	
	public static void debug(Object format, Object... args) {
		log(LogLevel.DEBUG, format, args);
	}
	
	public static void debug(Object object) {
		log(LogLevel.DEBUG, object);
	}
	
	public static void raw(Object object) {
		System.out.println(String.valueOf(object));
	}
	
	public static Path logDir = Paths.get("logs").toAbsolutePath();
	
	public static void exception(Throwable throwable, String format, Object... args) {
		log(LogLevel.ERROR, "Exception caught %s: '%s'", throwable.getClass().getName(), throwable.getMessage());
		if (format != null) {
			log(LogLevel.ERROR, format, args);
		}
		
		StringWriter stringWriter = new StringWriter();
		throwable.printStackTrace(new PrintWriter(stringWriter));
		if (exceptionFileReport) {
			try {
				Files.createDirectory(logDir);
			} catch (FileAlreadyExistsException ingored) {
				
			} catch (IOException ioException) {
				ioException.printStackTrace();
				Logger.error(ioException.getMessage());
			}
			
			Path path = logDir.resolve(fileDateFormat.format(new Date()) + " (" + UUID.randomUUID().toString() + ").txt").toAbsolutePath();
			Logger.error("Write stack trace to '%s'", path.toString());
			try (PrintStream stream = new PrintStream(Files.newOutputStream(path, StandardOpenOption.CREATE))) {
				throwable.printStackTrace(stream);
			} catch (IOException ioException) {
				Logger.error(ioException.getMessage());
				ioException.printStackTrace();
			}
		}
		
		Logger.error("Raw stack trace : \n%s", stringWriter.toString());
	}
	
	public static void exception(Throwable throwable) {
		exception(throwable, null);
	}
	
	public static void exception(Exception exception) {
		exception(exception, null);
	}
	
	public static void clear() {
		clear(100);
	}
	
	public static void clear(int repetition) {
		Logger.info("Clearing: repetition=" + repetition);
		for (int i = 0; i < repetition; i++) {
			System.out.println("");
		}
	}
	
	public static void setStaticLength(int length) {
		setStaticLength(length, false);
	}
	
	public static void setStaticLength(int length, boolean postingInConsole) {
		useStaticLength = true;
		staticLength = length;
		
		if (postingInConsole) {
			info("Logger's static length has been set to: " + length);
		}
	}
	
	public static void resetStaticLength() {
		useStaticLength = false;
		staticLength = 1;
	}
	
	public static void setClassOnly(boolean classOnly) {
		useClassOnly = classOnly;
	}
	
	public static void setAddMethodName(boolean addName) {
		addMethodName = addName;
	}
	
	public static void setExceptionFileReport(boolean report) {
		exceptionFileReport = report;
	}
	
}