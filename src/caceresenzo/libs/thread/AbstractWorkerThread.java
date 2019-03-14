package caceresenzo.libs.thread;

import java.lang.reflect.Method;

/**
 * Class more advanced that a simple thread with some useful function.
 * 
 * @author Enzo CACERES
 */
@Deprecated
public abstract class AbstractWorkerThread extends Thread {
	
	/* Variables */
	private boolean running, cancelled, locked;
	
	@Override
	public void run() {
		initialize();
		
		running(true);
		
		execute();
		
		if (running) {
			running(false);
		}
	}
	
	/**
	 * Abstract function called before the thread start.
	 */
	protected abstract void initialize();
	
	/**
	 * Abstract function called when the thread start.
	 */
	protected abstract void execute();
	
	/**
	 * Abstract function called when the thread notifify a progress update.
	 */
	protected abstract void publishProgress(int max, int value);
	
	/**
	 * Tell you if the thread is running or not.
	 * 
	 * @return Actual state.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Set a new running state for this thread.<br>
	 * If false, and is not already cancelled, wil call {@link #done()}.
	 * 
	 * @param state
	 *            New state.
	 * @return <code>this</code> for method chaining (fluent API).
	 */
	private AbstractWorkerThread running(boolean state) {
		running = state;
		
		if (!state && !cancelled) {
			done();
		}
		
		return this;
	}
	
	/**
	 * Tell you is this thread has been cancelled.
	 * 
	 * @return Cancelled state.
	 */
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Cancel a thread, that will not stop the execution by itself, but il will call {@link #cancel()}.
	 * 
	 * @return <code>this</code> for method chaining (fluent API).
	 */
	public AbstractWorkerThread terminate() {
		cancelled = true;
		running = false;
		
		cancel();
		
		return this;
	}
	
	/**
	 * Called before a {@link #forceStop()}'s killing instruction.
	 */
	public abstract void onForcedStop();
	
	/**
	 * Force this worker to stop.<br>
	 * The function {@link #onForcedStop()} will be called before the stop instruction.
	 * 
	 * @return If the thread successfully stop.
	 */
	@Deprecated
	public boolean forceStop() {
		try {
			onForcedStop();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		try {
			Method method = Thread.class.getDeclaredMethod("stop0", new Class[] { Object.class });
			method.setAccessible(true);
			method.invoke(this, new ThreadDeath());
		} catch (Exception exception) {
			exception.printStackTrace();
			
			try {
				stop();
			} catch (Exception exception2) {
				exception2.printStackTrace();
				
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Tell you if the thread has been locked.
	 * 
	 * @return Locked state.
	 */
	public boolean isLocked() {
		return locked;
	}
	
	/**
	 * Set a new locked state for this thread, this will not lock the execution.
	 * 
	 * @param locked
	 *            New locked state.
	 * @return <code>this</code> for method chaining (fluent API).
	 */
	public AbstractWorkerThread setLocked(boolean locked) {
		this.locked = locked;
		
		if (!locked) {
			safeInterrupt();
		}
		
		return this;
	}
	
	/**
	 * Quick call to lock the thread, see {@link #setLocked(boolean)}.
	 * 
	 * @return <code>this</code> for method chaining (fluent API).
	 */
	public AbstractWorkerThread lock() {
		return setLocked(true);
	}
	
	/**
	 * Quick call to unlock the thread, see {@link #setLocked(boolean)}.
	 * 
	 * @return <code>this</code> for method chaining (fluent API).
	 */
	public AbstractWorkerThread unlock() {
		return setLocked(false);
	}
	
	/**
	 * Wait until {@link #interrupt()} or {@link #safeInterrupt()} function has been called.<br>
	 * This will do {@link Thread#sleep(long)} with {@link Long#MAX_VALUE}.
	 * 
	 * @return <code>this</code> for method chaining (fluent API).
	 */
	public AbstractWorkerThread waitUntilUnlock() {
		ThreadUtils.sleep(Long.MAX_VALUE);
		
		return this;
	}
	
	/**
	 * Call {@link #interrupt()} in a try-catch block and ignore the thrown {@link Exception}.
	 * 
	 * @return <code>this</code> for method chaining (fluent API).
	 */
	public AbstractWorkerThread safeInterrupt() {
		try {
			interrupt();
		} catch (Exception exception) {
			;
		}
		
		return this;
	}
	
	/**
	 * Abstract function called when the thread has finished, and has not been cancel.
	 */
	protected abstract void done();
	
	/**
	 * Abstract function called when a thread has been cancelled.
	 */
	protected abstract void cancel();
	
	public static class WorkerThreadException extends RuntimeException {
		
	}
	
	public static class WorkerThreadCancelledException extends WorkerThreadException {
		
	}
	
}