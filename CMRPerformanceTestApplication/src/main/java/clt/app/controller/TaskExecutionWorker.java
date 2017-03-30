package clt.app.controller;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import clt.app.computations.IRecursiveTask;

public class TaskExecutionWorker extends Thread {

	private TaskRegistry taskRegistry;

	public int depth;

	private static AtomicLong executeCounter = new AtomicLong();

	public long result;

	public TaskExecutionWorker(TaskRegistry taskRegistry, int depth) {
		this.taskRegistry = taskRegistry;
		this.depth = depth;
	}

	public void executeTasks() {
		executeCounter.incrementAndGet();

		result = 0;
		ArrayList<IRecursiveTask> tasks = taskRegistry.getTasks();
		for (IRecursiveTask task : tasks) {
			task.setDepth(depth);
			result += task.recursiveMethod(1, 1);
		}
	}

	public static AtomicLong getExecuteCounter() {
		return executeCounter;
	}
}
