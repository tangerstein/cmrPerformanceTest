package rocks.inspectit.clt.app.controller;

public class WorkerManager {

	TaskRegistry taskRegistry;

	PropertyProvider propertyProvider;

	private int workerSleep;

	public int numberOfWorkers;

	public WorkerManager(TaskRegistry taskRegistry, PropertyProvider propertyProvider) {
		super();
		this.taskRegistry = taskRegistry;
		this.propertyProvider = propertyProvider;
		this.numberOfWorkers = Runtime.getRuntime().availableProcessors();
		this.workerSleep = 1000 / Integer.parseInt(propertyProvider.getProperties().getProperty("workerRate", "100"))
				* numberOfWorkers;
	}

	public void startWorkers() {
		for (int i = 0; i < numberOfWorkers; i++) {
			TaskExecutionWorker worker = new TaskExecutionWorker(taskRegistry, propertyProvider);
			worker.start();
			System.out.println("Worker thread: " + worker.getName() + " started");
			try {
				Thread.sleep(workerSleep);
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
