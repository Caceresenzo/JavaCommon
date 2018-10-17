package caceresenzo.libs.thread.implementations;

import caceresenzo.libs.thread.AbstractWorkerThread;

public class WorkerThread extends AbstractWorkerThread {
	
	@Override
	protected void execute() {
		;
	}
	
	@Override
	protected void done() {
		;
	}
	
	@Override
	protected void cancel() {
		;
	}
	
	public static boolean isWorkerFree(AbstractWorkerThread helpedThread) {
		return (helpedThread == null || !helpedThread.isRunning());
	}
	
}