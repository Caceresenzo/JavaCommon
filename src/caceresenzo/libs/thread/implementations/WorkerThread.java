package caceresenzo.libs.thread.implementations;

import caceresenzo.libs.thread.AbstractWorkerThread;

public class WorkerThread extends AbstractWorkerThread {
	
	private ProgressObserver progressObserver;
	private boolean shouldStop;
	
	@Override
	protected void initialize() {
		;
	}
	
	@Override
	protected void execute() {
		;
	}
	
	@Override
	protected void publishProgress(int max, int value) {
		if (progressObserver != null) {
			progressObserver.onProgress(this, max, value);
		}
	}
	
	@Override
	protected void done() {
		;
	}
	
	@Override
	protected void cancel() {
		;
	}
	
	@Override
	public void onForcedStop() {
		;
	}
	
	public WorkerThread observe(ProgressObserver progressObserver) {
		this.progressObserver = progressObserver;
		
		return this;
	}
	
	public WorkerThread removeObserver() {
		return observe(null);
	}
	
	public static boolean isWorkerFree(AbstractWorkerThread helpedThread) {
		return (helpedThread == null || !helpedThread.isRunning());
	}
	
	public interface ProgressObserver {
		
		void onProgress(WorkerThread worker, int max, int value);
		
	}

	public void shouldStop() {
		shouldStop = true;
		interrupt();
	}
	
	public boolean threadShouldStop() {
		return shouldStop;
	}
	
}