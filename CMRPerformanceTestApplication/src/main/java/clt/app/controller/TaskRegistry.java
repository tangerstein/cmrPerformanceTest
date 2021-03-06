package clt.app.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import clt.app.computations.IRecursiveTask;

@Component
public class TaskRegistry {

	private ArrayList<IRecursiveTask> tasks = new ArrayList<IRecursiveTask>();

	public void registerTask(IRecursiveTask task) {
		System.out.println("Task " + task.getClass().getCanonicalName() + " is registered");
		tasks.add(task);
	}

	public ArrayList<IRecursiveTask> getTasks() {
		return tasks;
	}
}
