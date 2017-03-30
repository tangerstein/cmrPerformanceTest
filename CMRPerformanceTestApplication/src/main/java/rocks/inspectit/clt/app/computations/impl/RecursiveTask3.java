package rocks.inspectit.clt.app.computations.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rocks.inspectit.clt.app.computations.IRecursiveTask;
import rocks.inspectit.clt.app.controller.TaskRegistry;

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