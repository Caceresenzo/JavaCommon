package caceresenzo.libs.thread;

/**
 * Class more advanced that a simple thread with some useful function
 * 
 * @author Enzo CACERES
 */
public abstract class AbstractHelpedThread extends Thread {
	
	private boolean running, cancelled, locked;
	
	@Override
	public void run() {
		running(true);
		
		onRun();
		
		if (running) {
			running(false);
		}
	}
	
	/**
	 * Abstract function called when the thread start
	 */
	protected abstract void onRun();
	
	/**
	 * Tell you if the thread is running or not
	 * 
	 * @return Actual state
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Set a new running state for this thread
	 * 
	 * If false, and is not already cancelled, wil call {@link #onFinished()}
	 * 
	 * @param state
	 *            New state
	 * @return Itself
	 */
	private AbstractHelpedThread running(boolean state) {
		running = state;
		
		if (!state && !cancelled) {
			onFinished();
		}
		
		return this;
	}
	
	/**
	 * Tell you is this thread has been cancelled
	 * 
	 * @return Cancelled state
	 */
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Cancel a thread, that will not stop the execution by itself, but il will call {@link #onCancelled()}
	 * 
	 * @return Itself
	 */
	public AbstractHelpedThread cancel() {
		cancelled = true;
		running = false;
		
		onCancelled();
		
		return this;
	}
	
	/**
	 * Tell you if the thread has been locked
	 * 
	 * @return Locked state
	 */
	public boolean isLocked() {
		return locked;
	}
	
	/**
	 * Set a new locked state for this thread, this will not lock the execution
	 * 
	 * @param locked
	 *            New locked state
	 * @return Itself
	 */
	public AbstractHelpedThread setLocked(boolean locked) {
		this.locked = locked;
		return this;
	}
	
	/**
	 * Quick call to lock the thread, see {@link #setLocked(boolean)}
	 * 
	 * @return Itself
	 */
	public AbstractHelpedThread lock() {
		setLocked(true);
		
		return this;
	}
	
	/**
	 * Quick call to unlock the thread, see {@link #setLocked(boolean)}
	 * 
	 * @return Itself
	 */
	public AbstractHelpedThread unlock() {
		setLocked(false);
		
		return this;
	}
	
	/**
	 * Will lock the thread until a {@link #unlock()} has been called or if {@link #setLocked(boolean)} is call with false
	 * 
	 * Cancel or finish the thread will also broke the loop and restore flow
	 * 
	 * @param timeBewteenCheck
	 *            Time between the locked state will be check
	 * @return Itself
	 */
	public AbstractHelpedThread waitUntilUnlock(long timeBewteenCheck) {
		while (locked && !cancelled && running) {
			ThreadUtils.sleep(timeBewteenCheck);
		}
		
		return this;
	}
	
	/**
	 * See {@link #waitUntilUnlock(long)}
	 * 
	 * This automaticly set the time bewteen check to 50 ms
	 * 
	 * @return Itself
	 */
	public AbstractHelpedThread waitUntilUnlock() {
		return waitUntilUnlock(50);
	}
	
	/**
	 * Abstract function called when the thread has finished, and has not been cancel
	 */
	protected abstract void onFinished();
	
	/**
	 * Abstract function called when a thread has been cancelled
	 */
	protected abstract void onCancelled();
	
	public static class HelpedThreadException extends RuntimeException {
		
	}
	
	public static class HelpedThreadCancelledException extends HelpedThreadException {
		
	}
	
}