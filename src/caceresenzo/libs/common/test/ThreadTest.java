package caceresenzo.libs.common.test;

import java.util.ArrayList;
import java.util.List;

import caceresenzo.libs.logger.Logger;
import caceresenzo.libs.thread.ThreadUtils;
import caceresenzo.libs.thread.queue.WorkQueue;

public class ThreadTest {
	
	public static int taskId = 0;
	
	public static void main(String... strings) {
		WorkQueue queue = new WorkQueue(1);
		
		Runnable task = new Runnable() {
			@Override
			public void run() {
				ThreadUtils.sleep(200L);
				Logger.info("taskEnd: " + ++taskId);
			}
		};
		
		List<Runnable> tasks = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			tasks.add(task);
		}
		
		queue.run(tasks);
		queue.run(task);
	}
	
}
