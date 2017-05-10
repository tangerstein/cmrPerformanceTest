package clt.app.controller;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.jetty.util.ConcurrentHashSet;

import clt.app.computations.ITask;

public class TaskExecutionWorker implements Runnable {

	private TaskRegistry taskRegistry;

	private static AtomicLong executeCounter = new AtomicLong();

	public long result;

	public TaskExecutionWorker(TaskRegistry taskRegistry) {
		this.taskRegistry = taskRegistry;
	}

	public void executeTasks() {
		executeCounter.incrementAndGet();

		result = 0;
		ConcurrentHashSet<ITask> tasks = taskRegistry.getTasks();
		for (ITask task : tasks) {
			task.setDepth(ThreadLocalRandom.current().nextInt(100, 2000 + 1));
			result += task.execute(1, ThreadLocalRandom.current().nextInt(100, 2000 + 1));
		}
	}

	public static AtomicLong getExecuteCounter() {
		return executeCounter;
	}

	@Override
	public void run() {
		while (true) {
			executeTasks();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
