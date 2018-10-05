package caceresenzo.libs.thread.queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import caceresenzo.libs.logger.Logger;

/**
 * A utility class that receives a collection of tasks to execute internally and then distributes the tasks among a thread pool. This class offers to methods of use. In the first, a user can pass in a collection of tasks to run and then wait until the tasks are finished.
 * 
 * <code>
 * Collection<Runnable> tasks = new LinkedList<Runnable>();
 * WorkQueue queue = new WorkQueue();
 * for (int i = 0; i < numTasks; ++i)
 * 	tasks.add(new Runnable() {
 * 	}); // job to do goes here
 * queue.run(tasks);
 * </code>
 * 
 * <br>
 *
 * Alternately, a use may register a task group identifier and then iteratively add new tasks associated with that identifier. At some point in the future, the user can then wait for all the tasks associated with that identifier to finish. This second method allows for the iterative construction of tasks, or for cases where not all of the data for the tasks is availabe at once (although the number of tasks is known).
 * 
 * <code>
 * WorkQueue queue = new WorkQueue();
 * Object taskGroupId = Thread.currentThread(); // a unique id
 * queue.registerTaskGroup(taskGroupId, numTasks);
 * for (int i = 0; i < numTasks; ++i)
 * 	queue.add(taskGroupId, new Runnable() {
 * 	}); // job to do goes here
 * queue.await(taskGroupId);
 * </code>
 *
 * In the above example, the current thread is used as the group identifier, which ensures that any other thread executing the same code won't use the same identifier, which could result in either thread returning prematurely before its tasks have finished. However, a <i>shared</i> group identifier can allow multiple threads to add tasks for a common goal, with each being able await until all the tasks are finished.
 */
public class WorkQueue {
	
	/**
	 * The list of all threads drawing work from the queue.
	 */
	private final List<Thread> threads;
	
	/**
	 * The queue from which worker threads run word-word comparisons
	 */
	private final BlockingQueue<Runnable> workQueue;
	
	/**
	 * A mapping from a group identifier to the associated latch.
	 */
	private final ConcurrentMap<Object, CountDownLatch> taskKeyToLatch;
	
	/**
	 * Creates a new work queue with the number of threads executing tasks the same as the number as processors on the system.
	 */
	public WorkQueue() {
		this(Runtime.getRuntime().availableProcessors());
	}
	
	/**
	 * Creates a new work queue with the specified number of threads executing tasks.
	 */
	public WorkQueue(int numberThreads) {
		workQueue = new LinkedBlockingQueue<Runnable>();
		threads = new ArrayList<Thread>();
		taskKeyToLatch = new ConcurrentHashMap<Object, CountDownLatch>();
		for (int i = 0; i < numberThreads; ++i) {
			Thread thread = new QueueWorkerThread(workQueue);
			threads.add(thread);
			thread.start();
		}
	}
	
	/**
	 * Adds the provided task to the work queue on behalf of the task group identifier. Note that unlike the {@link #run(Collection) run} method, this method returns immediately without waiting for the task to finish.
	 *
	 * @param taskGroupId
	 *            an identifier associated with a set of tasks.
	 * @param task
	 *            a task to run
	 *
	 * @throws IllegalArgumentException
	 *             if the {@code taskGroupId} is not currently associated with any active taskGroup
	 */
	public void add(Object taskGroupId, Runnable task) {
		CountDownLatch latch = taskKeyToLatch.get(taskGroupId);
		if (latch == null) {
			throw new IllegalArgumentException("Unknown task id: " + taskGroupId);
		}
		workQueue.offer(new CountingRunnable(task, latch));
	}
	
	/**
	 * Waits until all the tasks associated with the group identifier have finished. Once a task group has been successfully waited upon, the group identifier is removed from the queue and is valid to be reused for a new task group.
	 *
	 * @throws IllegalArgumentException
	 *             if the {@code taskGroupId} is not currently associated with any active taskGroup
	 */
	public void await(Object taskGroupId) {
		CountDownLatch latch = taskKeyToLatch.get(taskGroupId);
		if (latch == null) {
			throw new IllegalArgumentException("Unknown task group: " + taskGroupId);
		}
		try {
			while (!latch.await(5, TimeUnit.SECONDS)) {
				System.out.println();
				Logger.debug("Current task count: " + latch.getCount());
			}
			// Once finished, remove the key so it can be associated with a new
			// task
			taskKeyToLatch.remove(taskGroupId);
		} catch (InterruptedException ie) {
			throw new IllegalStateException("Not all tasks finished", ie);
		}
	}
	
	/**
	 * Waits until all the tasks associated with the group identifier have finished. Once a task group has been successfully waited upon, the group identifier is removed from the queue and is valid to be reused for a new task group.
	 *
	 * @throws IllegalArgumentException
	 *             if the {@code taskGroupId} is not currently associated with any active taskGroup
	 */
	public boolean await(Object taskGroupId, long timeout, TimeUnit unit) {
		CountDownLatch latch = taskKeyToLatch.get(taskGroupId);
		if (latch == null) {
			throw new IllegalArgumentException("Unknown task group: " + taskGroupId);
		}
		try {
			if (latch.await(timeout, unit)) {
				// Once finished, remove the key so it can be associated with a new task
				taskKeyToLatch.remove(taskGroupId);
				return true;
			}
			return false;
		} catch (InterruptedException exception) {
			throw new IllegalStateException("Not all tasks finished", exception);
		}
	}
	
	/**
	 * Registers a new task group with the specified number of tasks to execute and returns a task group identifier to use when registering its tasks.
	 *
	 * @param numberTasks
	 *            the number of tasks that will be eventually run as a part of this group.
	 *
	 * @returns an identifier associated with a group of tasks
	 */
	public Object registerTaskGroup(int numberTasks) {
		Object key = new Object();
		taskKeyToLatch.putIfAbsent(key, new CountDownLatch(numberTasks));
		return key;
	}
	
	/**
	 * Registers a new task group with the specified number of tasks to execute, or returns {@code false} if a task group with the same identifier has already been registered. This identifier will remain valid in the queue until {@link #await(Object) await} has been called.
	 *
	 * @param taskGroupId
	 *            an identifier to be associated with a group of tasks
	 * @param numberTasks
	 *            the number of tasks that will be eventually run as a part of this group.
	 *
	 * @returns {@code true} if a new task group was registered or {@code false} if a task group with the same identifier had already been registered.
	 */
	public boolean registerTaskGroup(Object taskGroupId, int numberTasks) {
		return taskKeyToLatch.putIfAbsent(taskGroupId, new CountDownLatch(numberTasks)) == null;
	}
	
	/**
	 * Executes the tasks using a thread pool and returns once all tasks have finished.
	 *
	 * @throws IllegalStateException
	 *             if interrupted while waiting for the tasks to finish
	 */
	public void run(Runnable... tasks) {
		run(Arrays.asList(tasks));
	}
	
	/**
	 * Executes the tasks using a thread pool and returns once all tasks have finished.
	 *
	 * @throws IllegalStateException
	 *             if interrupted while waiting for the tasks to finish
	 */
	public void run(Collection<Runnable> tasks) {
		// Create a semphore that the wrapped runnables will execute
		int numerTasks = tasks.size();
		CountDownLatch latch = new CountDownLatch(numerTasks);
		for (Runnable runnable : tasks) {
			workQueue.offer(new CountingRunnable(runnable, latch));
		}
		try {
			// Wait until all the tasks have finished
			latch.await();
		} catch (InterruptedException exception) {
			throw new IllegalStateException("Not all tasks finished", exception);
		}
	}
	
	/**
	 * Returns the number of threads being used to process the enqueued tasks.
	 */
	public int numberThreads() {
		return threads.size();
	}
	
}