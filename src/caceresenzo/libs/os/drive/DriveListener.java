package caceresenzo.libs.os.drive;

import javax.swing.event.ChangeEvent;

/**
 * The listener interface for receiving drive state events. The class that is interested in processing a drive state event implements this interface, and the object created with that class is registered with WinDriveDetector, using the addDriveListener() method.
 */
public interface DriveListener {
	
	/**
	 * Called when a drive has been inserted
	 *
	 * @param event
	 *            the event
	 */
	public void driveInserted(ChangeEvent event);
	
	/**
	 * Called when a drive has been removed
	 *
	 * @param event
	 *            the event
	 */
	public void driveRemoved(ChangeEvent event);
	
}