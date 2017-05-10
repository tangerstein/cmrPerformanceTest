package clt.app.controller;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.springframework.stereotype.Component;

import clt.app.computations.ITask;

@Component
public class TaskRegistry {

	private ConcurrentHashSet<ITask> tasks = new ConcurrentHashSet<ITask>();

	public void registerTask(ITask task) {
		System.out.println("Task " + task.getClass().getCanonicalName() + " is registered");
		tasks.add(task);
	}

	public ConcurrentHashSet<ITask> getTasks() {
		return tasks;
	}
}
