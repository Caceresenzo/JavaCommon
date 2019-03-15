package caceresenzo.libs.timer;

import java.util.Date;

/**
 * Class for program event timing.<br>
 * <br>
 * Usage:<br>
 * <code>
 * InstructionTimer timer = new InstructionTimer();<br>
 * // Do your stuff<br>
 * System.out.println(timer); // Prints time elapsed since object was created.<br>
 * </code>
 */
public class InstructionTimer {
	
	/* Variables */
	private Date start;
	
	/** Start timer. */
	public InstructionTimer() {
		reset();
	}
	
	/** @return The exact number of milliseconds since timer was started. */
	public long getTime() {
		return new Date().getTime() - start.getTime();
	}
	
	/** Reset the timer. */
	public void reset() {
		start = new Date();
	}
	
	/** @return A formatted string showing the elaspsed time suince the instance was created. */
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