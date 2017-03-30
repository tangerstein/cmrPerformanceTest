package rocks.inspectit.clt.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marius Oehler
 *
 */
@RestController
public class CommandController {

	@Autowired
	private CLTController cltController;

	@RequestMapping("/load")
	public String loadCommand(@RequestParam(value = "count", defaultValue = "10") int count, @RequestParam(value = "rate", defaultValue = "1") int rate) {
		System.out.println("Received load command. count: " + count + ", rate: " + rate);

		List<Socket> socketList = cltController.getSocketList();

		for (Socket socket : socketList) {
			ObjectOutputStream oos = getObjectOutputStream(socket);
			if (oos != null) {
				try {
					oos.writeObject(new int[] { 0, count, rate });
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return "{\"command\": \"load\", \"count\": " + count + ", \"rate\": " + rate + "}";
	}

	private ObjectOutputStream getObjectOutputStream(Socket socket) {
		if (!socket.isClosed()) {
			try {
				return new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}
}
