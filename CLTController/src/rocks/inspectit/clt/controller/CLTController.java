package rocks.inspectit.clt.controller;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Marius Oehler
 *
 */
@SpringBootApplication
public class CLTController {

	private static final int portNumber = 9080;

	private List<Socket> socketList = new ArrayList<>();

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(CLTController.class, args);
	}

	@PostConstruct
	public void start() {
		System.out.println("## CMR Load Test - Controller ##");

		executor.execute(new Runnable() {
			@Override
			public void run() {
				try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
					while (true) {
						System.out.println("Waiting for incoming connection..");
						Socket clientSocket = serverSocket.accept();

						System.out.println("Connection accepted.");
						socketList.add(clientSocket);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Gets {@link #socketList}.
	 *
	 * @return {@link #socketList}
	 */
	public List<Socket> getSocketList() {
		return this.socketList;
	}
}
