package caceresenzo.libs.thread.queue;

import java.util.concurrent.CountDownLatch;

/**
 * A utility class that wraps an existing runnable and updates the latch when the task has finished.
 */
public class CountingRunnable implements Runnable {
	
	/**
	 * The task to execute
	 */
	private final Runnable task;
	
	/**
	 * The latch to update once the task has finished
	 */
	private final CountDownLatch latch;
	
	public CountingRunnable(Runnable task, CountDownLatch latch) {
		this.task = task;
		this.latch = latch;
	}
	
	/**
	 * Executes the task and count down once finished.
	 */
	public void run() {
		try {
			task.run();
		} finally {
			latch.countDown();
		}
	}
	
}