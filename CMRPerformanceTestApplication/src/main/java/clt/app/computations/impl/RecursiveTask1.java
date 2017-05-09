package clt.app.computations.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import clt.app.computations.IRecursiveTask;
import clt.app.controller.TaskRegistry;

@Component
public class RecursiveTask1 implements IRecursiveTask {
	private long depth;

	@Autowired
	private TaskRegistry taskManager;

	public long recursiveMethod(long n, long numberOfRuns) {
		if (numberOfRuns >= depth) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return n;
		} else {
			return recursiveMethod(n * 5, numberOfRuns + 1);
		}
	}

	public void setDepth(long depth) {
		this.depth = depth;
	}

	@PostConstruct
	public void addToTaskList() {
		taskManager.registerTask(this);
	}

}