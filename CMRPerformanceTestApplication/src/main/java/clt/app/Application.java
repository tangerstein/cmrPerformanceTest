package clt.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import clt.app.controller.TaskExecutionWorker;
import clt.app.controller.TaskRegistry;

@SpringBootApplication
public class Application implements Runnable {

	@Autowired
	private TaskRegistry taskRegistry;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void controllerConnection() throws Exception {
		executor.execute(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {

		TaskExecutionWorker worker = new TaskExecutionWorker(taskRegistry, 10);

		while (true) {
			worker.executeTasks();

			try {
				Thread.sleep(1, 0);
			} catch (InterruptedException e) {
			}
		}
		// try {
		// String controllerHost = System.getProperty("CONTROLLER_HOST");
		// String controllerPort = System.getProperty("CONTROLLER_PORT");
		//
		// System.out.println(String.format("Connecting to %s:%s..", controllerHost,
		// controllerPort));
		//
		// new InfluxController().start();
		//
		// try (Socket socket = new Socket(controllerHost, Integer.parseInt(controllerPort))) {
		// try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
		//
		// while (true) {
		// Object[] input = (Object[]) ois.readObject();
		//
		// int cmdId = (int) input[0];
		//
		// if (cmdId == 0) {
		// executor.execute(new Runnable() {
		// @Override
		// public void run() {
		// load(input);
		// }
		// });
		// }
		// }
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// System.exit(1);
		// }
	}

	/**
	 * @param input
	 * @throws InterruptedException
	 */
	private void load(Object[] input) {
		int count = (int) input[1];
		int rate = (int) input[2];
		int depth = (int) input[3];

		System.out.println("Load - count:" + count + " rate:" + rate + " depth:" + depth);

		TaskExecutionWorker worker = new TaskExecutionWorker(taskRegistry, depth);

		for (int i = 0; i < count; i++) {
			worker.executeTasks();

			try {
				Thread.sleep(1000L / rate);
			} catch (InterruptedException e) {
			}
		}
	}
}