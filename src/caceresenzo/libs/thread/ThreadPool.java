package caceresenzo.libs.thread;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class ThreadPool {
	
	private String name;
	private HashSet<Worker> active = new HashSet<Worker>();
	private LinkedList<Worker> idle = new LinkedList<Worker>();
	private int idleCount;
	private int maxActive = 10;
	private int maxIdle = 3;
	private int lastThreadId;
	private boolean closed;
	
	public ThreadPool(String name) {
		this.name = name;
	}
	
	public int getMaxActive() {
		return maxActive;
	}
	
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	
	public int getMaxIdle() {
		return maxIdle;
	}
	
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	
	public synchronized int getActiveCount() {
		return active.size();
	}
	
	public synchronized int getIdleCount() {
		return idleCount;
	}
	
	public synchronized void close() {
		if (closed) {
			return;
		}
		closed = true;
		for (Iterator<Worker> iterator = idle.iterator(); iterator.hasNext();) {
			Worker worker = (Worker) iterator.next();
			worker.terminate();
		}
		idle = null;
		idleCount = 0;
		for (Iterator<Worker> iterator = active.iterator(); iterator.hasNext();) {
			Worker worker = (Worker) iterator.next();
			worker.terminate();
		}
		active = null;
	}
	
	public synchronized boolean execute(Runnable runnable, int timeoutMillis) {
		if (closed) {
			throw new IllegalStateException("Pool has been closed");
		}
		Worker worker;
		if (idleCount == 0) {
			for (; isFull();) {
				try {
					wait(timeoutMillis);
					if (isFull()) {
						return false;
					}
				} catch (InterruptedException ignore) {
					// ignore
				}
			}
			worker = new Worker();
		} else {
			worker = (Worker) idle.removeFirst();
			idleCount--;
		}
		active.add(worker);
		worker.execute(runnable);
		return true;
	}
	
	protected boolean isFull() {
		return active.size() >= maxActive;
	}
	
	private synchronized void finishedWork(Worker worker) {
		if (!closed) {
			active.remove(worker);
			if (idleCount >= maxIdle) {
				worker.terminate();
			} else {
				idle.addLast(worker);
				++idleCount;
			}
		}
	}
	
	private class Worker extends Thread {
		
		private boolean stopFlag;
		private Runnable runnable;
		
		public Worker() {
			super(name + " " + ++lastThreadId);
			setDaemon(true);
		}
		
		public void execute(Runnable runnable) {
			this.runnable = runnable;
			if (!isAlive()) {
				start();
			} else {
				synchronized (this) {
					notify();
				}
			}
		}
		
		public void terminate() {
			stopFlag = true;
			interrupt();
		}
		
		public void run() {
			for (; !stopFlag;) {
				try {
					runnable.run();
				} catch (Throwable throwable) {
					if (throwable instanceof ThreadDeath) {
						throw (ThreadDeath) throwable;
					}
				}
				runnable = null;
				finishedWork(this);
				if (stopFlag) {
					break;
				}
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException ignore) {
						// ignore
					}
				}
			}
		}
		
	}
	
}