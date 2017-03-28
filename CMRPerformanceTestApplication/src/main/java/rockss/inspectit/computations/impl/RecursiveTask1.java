package rockss.inspectit.computations.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rockss.inspectit.computations.IRecursiveTask;
import rockss.inspectit.controller.TaskRegistry;

@Component
public class RecursiveTask1 implements IRecursiveTask {
	private long depth;

	@Autowired
	private TaskRegistry taskManager;

	public long recursiveMethod(long n, long numberOfRuns) {
		if (numberOfRuns >= depth) {
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