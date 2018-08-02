/*
 * Some handy routines to help on everyday tasks
 */

package caceresenzo.libs.time;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Nuno Brito, 20th of March 2011 at Germany
 */
public class DateUtils {

	/**
	 * Converts a given quantity of time into a human readable format
	 */
	public static String timeNumberToHumanReadable(long ms) {
		String time;
		long seconds;
		long minutes;
		long hours;
		long days;

		seconds = ms / 1000;
		minutes = seconds / 60;
		seconds %= 60;
		hours = minutes / 60;
		minutes %= 60;
		days = hours / 24;
		hours %= 24;

		time = "";

		if (days > 0) {
			time = days + (days == 1 ? " day" : " days") + ", ";
		}
		if (hours > 0) {
			time = time + hours + (hours == 1 ? " hour" : " hours") + ", ";
		}
		if (minutes > 0) {
			time = time + minutes + (minutes == 1 ? " minute" : " minutes") + " and ";
		}
		time = time + seconds + (seconds == 1 ? " second" : " seconds");

		return time;
	}

	/**
	 * Converts a given date to a unique number
	 */
	public static long textDateToMilliseconds(int yearTo, int monthTo, int dayTo) {
		return textDateToMilliseconds("" + yearTo, "" + monthTo, "" + dayTo);
	}

	/**
	 * Convert from a normal date to correct milliseconds representation
	 */
	public static long textDateToMilliseconds(String yearTo, String monthTo, String dayTo) {
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
		Date date;
		try {
			date = dateFormat.parse(yearTo + "-" + monthTo + "-" + dayTo);
			return date.getTime();
		} catch (ParseException exception) {
			return -1;
		}
	}

	/**
	 * Get the current time and date in SPDX format
	 */
	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String textDate = dateFormat.format(date);
		return textDate;
	}

	/**
	 * Convert from a normal date to correct milliseconds representation
	 */
	public static Date getDate(int yearTo, int monthTo, int dayTo) {
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
		Date date;
		try {
			date = dateFormat.parse(yearTo + "-" + monthTo + "-" + dayTo);
			return date;
		} catch (ParseException exception) {
			return null;
		}
	}

	/**
	 * get the current time in a human readable manner
	 */
	public static String getDateTimeISO() {
		// code adapted from http://goo.gl/rZ716
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * get the current time in a human readable manner
	 */
	public static String getDateTime() {
		// code adapted from http://goo.gl/rZ716
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * get the current time in a human readable manner
	 * 
	 */
	public static String getTimeFromLong(long time) {
		// code adapted from http://goo.gl/rZ716
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(time);
	}

	public static String getTimeFromLongNoDate(long time) {
		// code adapted from http://goo.gl/rZ716
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(time);
	}

	/**
	 * get the current time in a human readable manner
	 */
	public static String getCurrentYear() {
		// code adapted from http://goo.gl/rZ716
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * Returns the age of a given file using days as value
	 * A result of "1" means today, "2" means more than 24 hours and so forth
	 */
	public static int getFileAge(File file) {
		long fileAge = System.currentTimeMillis() - file.lastModified();
		DateFormat dateFormat = new SimpleDateFormat("dd");
		int dateValue = Integer.parseInt(dateFormat.format(fileAge));
		return dateValue;
	}

	/**
	 * Get the current time and date in SPDX format
	 */
	public static String getDateSPDX() {
		// do the time calculation such as 2012-09-03T13:32:12Z
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String textDate = dateFormat.format(date);
		// TODO for some reason "T" and "Z" are not accepted as parameters
		return textDate.replace(" ", "T") + "Z";
	}

	/**
	 * Get the current time and date ready to be used as file name
	 */
	public static String getTimeStampAsFileName() {
		// do the time calculation such as 2012-09-03T13:32:12Z
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		final String textDate = dateFormat.format(date);
		return textDate;
	}

}
