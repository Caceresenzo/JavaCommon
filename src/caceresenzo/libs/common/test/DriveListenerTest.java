package caceresenzo.libs.common.test;

import javax.swing.event.ChangeEvent;

import caceresenzo.libs.os.drive.DriveListener;
import caceresenzo.libs.os.drive.WinDriveDetector;

public class DriveListenerTest {
	
	public static void main(String[] args) {
		WinDriveDetector detector = new WinDriveDetector(new char[] { 'D', 'E' }, 2000);
		
		detector.addDriveListener(new DriveListener() {
			@Override
			public void driveRemoved(ChangeEvent event) {
				System.out.println("Drive " + event.getSource() + " has been removed");
			}
			
			@Override
			public void driveInserted(ChangeEvent event) {
				System.out.println("Drive " + event.getSource() + " has been inserted");
			}
		});
		
		detector.start();
	}
	
}