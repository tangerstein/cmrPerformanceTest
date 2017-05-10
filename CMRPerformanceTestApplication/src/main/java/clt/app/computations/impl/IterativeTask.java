package clt.app.computations.impl;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import clt.app.computations.ITask;
import clt.app.controller.TaskRegistry;

@Component
public class IterativeTask implements ITask {

	private long depth;

	@Autowired
	private TaskRegistry taskManager;

	public long execute(long n, long numberOfRuns) {
		System.out.println("Iterative execution starts");
		long sum = 0;
		for (int i = 0; i <= numberOfRuns; i++) {
			sum += doSomethingIterative(n);
		}
		return sum;
	}

	private long doSomethingIterative(long n) {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10 + 1));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n + 1;
	}

	public void setDepth(long depth) {
		this.depth = depth;
	}

	@PostConstruct
	public void addToTaskList() {
		taskManager.registerTask(this);
	}

}