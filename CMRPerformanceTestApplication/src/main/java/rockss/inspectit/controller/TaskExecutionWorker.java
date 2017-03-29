package rockss.inspectit.controller;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import rockss.inspectit.computations.IRecursiveTask;

public class TaskExecutionWorker extends Thread {

	private TaskRegistry taskRegistry;
	private int depth;
	private int workerSleep;
	private int numberOfExecutions;

	private static AtomicLong executeCounter = new AtomicLong();

	public TaskExecutionWorker(TaskRegistry taskRegistry, PropertyProvider propertyProvider) {
		this.taskRegistry = taskRegistry;
		this.workerSleep = 1000 / Integer.parseInt(propertyProvider.getProperties().getProperty("WORKER_RATE", "100"));
		this.depth = Integer.parseInt(propertyProvider.getProperties().getProperty("DEPTH", "100"));
		this.numberOfExecutions = Integer
				.parseInt(propertyProvider.getProperties().getProperty("NUMBER_OF_EXECUTIONS", "100"));
	}
	public long result;

	private void executeTasks(){
		result = 0;
		ArrayList<IRecursiveTask> tasks = taskRegistry.getTasks();
		for (IRecursiveTask task : tasks) {
			task.setDepth(depth);
			result += task.recursiveMethod(1, 1);
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < numberOfExecutions; i++) {
			// if we go again to the run and we are interrupted we will break;
			if (Thread.currentThread().isInterrupted()) {
				break;
			}

			executeTasks();

			executeCounter.incrementAndGet();
			try {
				Thread.sleep(workerSleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Worker thread: " + this.getName() + " stopped");
	}

	public static AtomicLong getExecuteCounter() {
		return executeCounter;
	}

}
