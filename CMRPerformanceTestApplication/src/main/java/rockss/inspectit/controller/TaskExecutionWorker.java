package rockss.inspectit.controller;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import rockss.inspectit.computations.IRecursiveTask;

public class TaskExecutionWorker extends Thread {

	private TaskRegistry taskRegistry;
	private int workerSleep;

	private static AtomicLong executeCounter = new AtomicLong();

	public TaskExecutionWorker(TaskRegistry taskRegistry) {
		this.taskRegistry = taskRegistry;
		this.workerSleep = Integer.parseInt(System.getProperty("workerSleep", "500"));
	}
	public long result;

	private void executeTasks(){
		result = 0;
		ArrayList<IRecursiveTask> tasks = taskRegistry.getTasks();
		for (IRecursiveTask task : tasks) {
			task.setDepth(Integer.parseInt(System.getProperty("depth", "100")));
			result += task.recursiveMethod(1, 1);
		}
	}

	@Override
	public void run() {
		while (true) {
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
	}

	public static AtomicLong getExecuteCounter() {
		return executeCounter;
	}

}
