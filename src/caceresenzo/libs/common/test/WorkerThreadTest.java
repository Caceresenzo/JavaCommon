package caceresenzo.libs.common.test;

import caceresenzo.libs.logger.Logger;
import caceresenzo.libs.test.SimpleTestUnits;
import caceresenzo.libs.thread.ThreadUtils;
import caceresenzo.libs.thread.implementations.WorkerThread;

public class WorkerThreadTest extends SimpleTestUnits {
	
	private static class ForceStopTest extends WorkerThreadTest {
		
		public static void main(String[] args) {
			WorkerThread worker = new WorkerThread() {
				@Override
				protected void execute() {
					while (true) {
						ThreadUtils.sleep(100L);
						Logger.info("Looping");
					}
				}
			};
			
			worker.start();
			ThreadUtils.sleep(1000L);
			worker.forceStop();
			
		}
		
	}
	
}