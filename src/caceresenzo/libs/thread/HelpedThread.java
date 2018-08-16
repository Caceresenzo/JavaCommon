package caceresenzo.libs.thread;

public abstract class HelpedThread extends Thread {
	
	private boolean running, cancelled;
	
	@Override
	public void run() {
		running(true);
		
		onRun();
		
		if (running) {
			running(false);
		}
	}
	
	protected abstract void onRun();
	
	public boolean isRunning() {
		return running;
	}
	
	private void running(boolean state) {
		running = state;
		
		if (!state && !cancelled) {
			onFinished();
		}
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void cancel() {
		cancelled = true;
		running = false;
		onCancelled();
	}
	
	protected abstract void onFinished();
	
	protected abstract void onCancelled();
	
}