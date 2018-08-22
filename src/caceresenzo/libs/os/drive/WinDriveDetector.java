package caceresenzo.libs.os.drive;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

/**
 * Lists the available disk drives and detects the insertion and/or removal of Removable drives on Windows Systems. This includes the insertion and removal of CD's and DVD's from their drives.
 */
public class WinDriveDetector {
	
	private final static char[] DRIVE_LETTERS = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private static final int DELAY = 1000;
	
	/**
	 * Gets all drives that are currently available on the local Windows system
	 *
	 * @return an array of File objects representing the root of each available drive
	 */
	public static File[] getAvailableDrives() {
		return getAvailableDrives(DRIVE_LETTERS);
	}
	
	/**
	 * Gets the drives that are currently available on the local Windows system from this list of drive letters.
	 *
	 * @param driveLetters
	 *            the drive letters to look for
	 * @return an array of File objects representing the root of each available drive
	 */
	public static File[] getAvailableDrives(char[] driveLetters) {
		List<File> drives = new ArrayList<>();
		
		for (int i = 0; i < driveLetters.length; ++i) {
			File drive = new File(driveLetters[i] + ":/");
			
			if (drive.canRead()) {
				drives.add(drive);
			}
		}
		
		return drives.toArray(new File[drives.size()]);
	}
	
	private File[] drives;
	private Timer timer;
	private List<DriveListener> driveListeners = new ArrayList<DriveListener>();
	private boolean[] state;
	
	/**
	 * Create a WinDriveDetector to monitor the insertion and/or removal of all drives. This polls the list of drives once every second.
	 * <p>
	 * To receive notification of changes add an ActionListener().
	 * </p>
	 * <p>
	 * To start the monitor call start().
	 */
	public WinDriveDetector() {
		this(DRIVE_LETTERS, DELAY);
	}
	
	/**
	 * Create a WinDriveDetector to monitor the insertion and/or removal of the specified drives. This polls the list of drives once every second.
	 * </p>
	 * <p>
	 * To receive notification of changes add an ActionListener().
	 * </p>
	 * <p>
	 * To start the monitor call start().
	 *
	 * @param driveLetters
	 *            the drive letters to monitor
	 */
	public WinDriveDetector(char[] driveLetters) {
		this(driveLetters, DELAY);
	}
	
	/**
	 * Create a WinDriveDetector to monitor the insertion and/or removal of all drives. This polls the list of drives once every 'time' milliseconds.
	 * </p>
	 * <p>
	 * To receive notification of changes add an ActionListener().
	 * </p>
	 * <p>
	 * To start the monitor call start().
	 *
	 * @param time
	 *            the time to wait between each check
	 */
	public WinDriveDetector(int time) {
		this(DRIVE_LETTERS, time);
	}
	
	/**
	 * Create a WinDriveDetector to monitor the insertion and/or removal of the specified drives. This polls the list of drives once every 'time' milliseconds.
	 * </p>
	 * <p>
	 * To receive notification of changes add an ActionListener().
	 * </p>
	 * <p>
	 * To start the monitor call start().
	 *
	 * @param driveLetters
	 *            the drive letters to monitor
	 * @param time
	 *            the time to wait between each check
	 */
	public WinDriveDetector(char[] driveLetters, int time) {
		drives = new File[driveLetters.length];
		state = new boolean[driveLetters.length];
		
		// Create File objects for each drive
		for (int j = 0; j < driveLetters.length; j++) {
			drives[j] = new File(driveLetters[j] + ":/");
			state[j] = drives[j].canRead();
		}
		
		// Create the timer
		timer = new Timer(time, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findChanges();
			}
		});
		
		timer.setRepeats(true);
	}
	
	/**
	 * Starts the monitor.
	 */
	public void start() {
		timer.restart();
	}
	
	/**
	 * Stops this monitor. Once stopped it can be restarted by calling start().
	 */
	public void stop() {
		timer.stop();
	}
	
	/**
	 * Sets the time to wait between polling the list of drives
	 *
	 * @param time
	 *            the time to wait
	 */
	public void setTime(int time) {
		timer.setDelay(time);
	}
	
	/**
	 * Adds a drive listener. This will be called when
	 *
	 * @param listener
	 *            - the listener to add
	 */
	public void addDriveListener(DriveListener listener) {
		driveListeners.add(listener);
	}
	
	/**
	 * Removes the drive listener
	 *
	 * @param listener
	 *            - the listener to remove
	 */
	public void removeDriveListener(DriveListener listener) {
		driveListeners.remove(listener);
	}
	
	/**
	 * Notifies registered listener of the change of state
	 *
	 * @param drive
	 *            - the drive that has changed state
	 * @param available
	 *            - the new state, true for the drive is available else false
	 */
	protected void fireDriveEvent(File drive, boolean available) {
		ChangeEvent event = new ChangeEvent(drive);
		
		for (DriveListener listener : driveListeners) {
			if (available) {
				listener.driveInserted(event);
			} else {
				listener.driveRemoved(event);
			}
		}
	}
	
	/**
	 * Checks each drive to see if it's state has changed
	 */
	private void findChanges() {
		for (int i = 0; i < drives.length; i++) {
			boolean currentState = drives[i].canRead();
			
			if (state[i] != currentState) {
				state[i] = currentState;
				
				fireDriveEvent(drives[i], currentState);
			}
		}
	}
	
}