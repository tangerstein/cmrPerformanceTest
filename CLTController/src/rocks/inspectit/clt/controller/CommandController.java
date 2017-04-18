package rocks.inspectit.clt.controller;

import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

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
			@RequestParam(value = "depth", defaultValue = "10") int depth, @RequestParam(value = "agents", defaultValue = "0") int agents) {

		load(count, rate, depth, agents);

		return "{\"command\": \"load\", \"count\": " + count + ", \"rate\": " + rate + ", \"depth\": " + depth + "}";
	}

	private void load(int count, int rate, int depth, int agents) {
		System.out.println("Received load command. count: " + count + ", rate: " + rate);

		Iterator<ObjectOutputStream> iterator = cltController.getObjectOutputStreams().iterator();

		int receiverCount = 0;

		while (iterator.hasNext()) {
			ObjectOutputStream oos = iterator.next();
			try {
				oos.writeObject(new Object[] { 0, count, rate, depth });
				oos.flush();
				receiverCount++;

				if ((agents > 0) && (receiverCount >= agents)) {
					break;
				}
			} catch (Exception e) {
				System.out.println("connection removed..");
				iterator.remove();
			}
		}

		System.out.println("receivre_count: " + receiverCount);
	}

	@RequestMapping("/start")
	public String start() {
		new Thread() {
			@Override
			public void run() {
				int[] rates = new int[] { 10, 25, 50, 75, 100 };
				int duration = 15;
				int delay = 5;

				for (int x = 1; x <= 5; x++) {
					for (int rate : rates) {
						System.out.println(new Date() + " - starting " + x + " agents with rate of " + rate);

						int count = (int) TimeUnit.MINUTES.toSeconds(duration) * rate;

						load(count, rate, 10, x);

						try {
							Thread.sleep(TimeUnit.MINUTES.toMillis(delay) + TimeUnit.MINUTES.toMillis(duration));
						} catch (InterruptedException e) {
						}
					}
				}
			};
		}.start();

		return "ok";
	}
}
