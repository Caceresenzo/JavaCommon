package caceresenzo.libs.thread.implementations;

import caceresenzo.libs.thread.AbstractHelpedThread;

public class HelpedThread extends AbstractHelpedThread {
	
	@Override
	protected void onRun() {
		;
	}
	
	@Override
	protected void onFinished() {
		;
	}
	
	@Override
	protected void onCancelled() {
		;
	}
	
	public static boolean isWorkerFree(AbstractHelpedThread helpedThread) {
		return (helpedThread == null || !helpedThread.isRunning());
	}
	
}