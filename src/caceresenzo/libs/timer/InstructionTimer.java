package caceresenzo.libs.timer;

import java.util.Date;

/**
 * Class for program event timing. Usage:
 *
 * <pre>
 * InstructionTimer timer = new InstructionTimer();
 * // do stuff
 * System.out.println(timer); // prints time elapsed since object was created.
 * </pre>
 */
public class InstructionTimer {
	private Date start;
	
	/**
	 * Start timer.
	 */
	public InstructionTimer() {
		reset();
	}
	
	/**
	 * Returns exact number of milliseconds since timer was started.
	 * 
	 * @return Number of milliseconds since timer was started.
	 */
	public long getTime() {		
		return new Date().getTime() - start.getTime();
	}
	
	/**
	 * Restarts the timer.
	 */
	public void reset() {
		start = new Date(); // now
	}
	
	/**
	 * Returns a formatted string showing the elaspsed time suince the instance was created.
	 * 
	 * @return Formatted time string.
	 */
	public String toString() {
		long millis = getTime();
		
		long hours = millis / 1000 / 60 / 60;
		millis -= hours * 1000 * 60 * 60;
		
		long minutes = millis / 1000 / 60;
		millis -= minutes * 1000 * 60;
		
		long seconds = millis / 1000;
		millis -= seconds * 1000;
		
		StringBuilder time = new StringBuilder();
		
		if (hours > 0) {
			time.append(hours + ":");
		}
		
		if (hours > 0 && minutes < 10) {
			time.append("0");
		}
		
		time.append(minutes + ":");
		
		if (seconds < 10) {
			time.append("0");
		}
		
		time.append(seconds).append(".");
		
		if (millis < 100) {
			time.append("0");
		}
		
		if (millis < 10) {
			time.append("0");
		}
		
		time.append(millis);
		
		return time.toString();
	}
	
}