package rocks.inspectit.clt.app.computations.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rocks.inspectit.clt.app.computations.IRecursiveTask;
import rocks.inspectit.clt.app.controller.TaskRegistry;

@Component
public class RecursiveTask4 implements IRecursiveTask {
	private long depth;
	@Autowired
	private TaskRegistry taskManager;
	public long recursiveMethod(long n, long numberOfRuns) {
		if (numberOfRuns >= depth) {
			return n;
		} else {
			return recursiveMethod(n + 4, numberOfRuns + 1);
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