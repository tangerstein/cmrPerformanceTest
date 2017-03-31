package rocks.inspectit.clt.controller;

import java.io.ObjectOutputStream;
import java.util.Iterator;

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
	public String loadCommand(@RequestParam(value = "count", defaultValue = "10") int count, @RequestParam(value = "rate", defaultValue = "1") int rate,
			@RequestParam(value = "depth", defaultValue = "10") int depth) {
		System.out.println("Received load command. count: " + count + ", rate: " + rate);

		Iterator<ObjectOutputStream> iterator = cltController.getObjectOutputStreams().iterator();

		int receiverCount = 0;

		while (iterator.hasNext()) {
			ObjectOutputStream oos = iterator.next();
			try {
				receiverCount++;
				oos.writeObject(new Object[] { 0, count, rate, depth });
				oos.flush();
			} catch (Exception e) {
				e.printStackTrace();
				iterator.remove();
			}
		}

		System.out.println("receivre_count: " + receiverCount);

		return "{\"command\": \"load\", \"count\": " + count + ", \"rate\": " + rate + ", \"depth\": " + depth + ", \"receiver_count\": " + receiverCount + "}";
	}
}
