package rockss.inspectit.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkerManager {
	@Autowired
	TaskRegistry taskRegistry;

	public int numberOfWorkers;

	@PostConstruct
	private void setNumberOfWorkers() {
		numberOfWorkers = Integer
				.parseInt(System.getProperty("numberOfWorkers", Runtime.getRuntime().availableProcessors() + ""));
		startWorkers();
	}
	private void startWorkers() {
		for (int i = 0; i < numberOfWorkers; i++) {
			TaskExecutionWorker worker = new TaskExecutionWorker(taskRegistry);
			worker.start();
			System.out.println("Worker thread started");
			try {
				Thread.sleep(1000 / Integer.parseInt(System.getProperty("workerRate", "100")) * numberOfWorkers);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
