package clt.app.computations.impl;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import clt.app.computations.ITask;
import clt.app.controller.TaskRegistry;

@Component
public class RecursiveTask implements ITask {
	private long depth;

	@Autowired
	private TaskRegistry taskManager;

	private long executeRecursiveMethod(long n, long numberOfRuns) {
		if (numberOfRuns >= depth) {

			return n;
		} else {
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10 + 1));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return executeRecursiveMethod(n++, numberOfRuns + 1);
		}
	}

	public void setDepth(long depth) {
		this.depth = depth;
	}

	@PostConstruct
	public void addToTaskList() {
		taskManager.registerTask(this);
	}

	@Override
	public long execute(long n, long numberOfRuns) {
		System.out.println("Recursive execution starts");
		return executeRecursiveMethod(1, 1);
	}

}