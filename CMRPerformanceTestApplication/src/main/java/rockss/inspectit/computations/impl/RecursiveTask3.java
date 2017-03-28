package rockss.inspectit.computations.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rockss.inspectit.computations.IRecursiveTask;
import rockss.inspectit.controller.TaskRegistry;

@Component
public class RecursiveTask3 implements IRecursiveTask {
	@Autowired
	private TaskRegistry taskManager;
	public long recursiveMethod(long n, long numberOfRuns) {
		if (numberOfRuns >= 5) {
			return n;
		} else {
			return recursiveMethod(n + 1, numberOfRuns + 1) * recursiveMethod(n - 1, numberOfRuns + 1);
		}
	}

	public void setDepth(long depth) {
	}

	@PostConstruct
	public void addToTaskList() {
		taskManager.registerTask(this);
	}
}