package caceresenzo.libs.thread.queue;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * A daemon thread that continuously dequeues {@code Runnable} instances from a queue and executes them. This class is intended to be used with a {@link java.util.concurrent.Semaphore Semaphore}, whereby work is added the to the queue and the semaphore indicates when processing has finished.
 */
class QueueWorkerThread extends Thread {
	
	/**
	 * A static variable to indicate which instance of the class the current thread is in its name.
	 */
	private static int threadInstanceCount;
	
	/**
	 * The queue from which work items will be taken
	 */
	private final BlockingQueue<Runnable> workQueue;
	
	/**
	 * An internal queue that holds thread-local tasks. This queue is intended to hold multiple tasks to avoid thread contention on the work queue.
	 */
	private final Queue<Runnable> internalQueue;
	
	/**
	 * The number of items that should be queued to run by this thread at once.
	 */
	private final int threadLocalItems;
	
	/**
	 * Creates a thread that continuously dequeues from the {@code workQueue} at once and excutes each item.
	 */
	public QueueWorkerThread(BlockingQueue<Runnable> workQueue) {
		this(workQueue, 1);
	}
	
	/**
	 * Creates a thread that continuously dequeues {@code threadLocalItems} from {@code workQueue} at once and excutes them sequentially.
	 *
	 * @param threadLocalItems
	 *            the number of items this thread should dequeue from the work queue at one time. Setting this value too high can result in a loss of concurrency; setting it too low can result in high contention on the work queue if the time per task is also low.
	 */
	public QueueWorkerThread(BlockingQueue<Runnable> workQueue, int threadLocalItems) {
		this.workQueue = workQueue;
		this.threadLocalItems = threadLocalItems;
		internalQueue = new ArrayDeque<Runnable>();
		setDaemon(true);
		synchronized (QueueWorkerThread.class) {
			setName("WorkerThread-" + (threadInstanceCount++));
		}
	}
	
	/**
	 * Continuously dequeues {@code Runnable} instances from the work queue and execute them.
	 */
	public void run() {
		Runnable runnable = null;
		while (true) {
			// Try to drain the maximum capacity of thread-local items, checking
			// whether any were available
			if (workQueue.drainTo(internalQueue, threadLocalItems) == 0) {
				// block until a work item is available
				try {
					internalQueue.offer(workQueue.take());
				} catch (InterruptedException exception) {
					throw new Error(exception);
				}
			}
			// Execute all of the thread-local items
			while ((runnable = internalQueue.poll()) != null) {
				runnable.run();
			}
		}
	}
	
}