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
public class Application {

	@Autowired
	private TaskRegistry taskRegistry;

	private ExecutorService executor = Executors.newFixedThreadPool(1);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void controllerConnection() throws Exception {
		executor.execute(new TaskExecutionWorker(taskRegistry));
	}
}