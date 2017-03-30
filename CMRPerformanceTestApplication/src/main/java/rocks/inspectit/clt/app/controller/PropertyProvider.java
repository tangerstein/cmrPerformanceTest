package rocks.inspectit.clt.app.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertyProvider implements Runnable {
	private static final String PROPERTY_FILEPATH = System.getProperty("PROPERTY_FILEPATH", "properties.properties");
	private Properties properties;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String ID = "START_" + UUID.randomUUID().toString();

	@Autowired
	private TaskRegistry taskRegistry;

	@Override
	public void run() {
		try {
			// Load inputStream
			loadProperties();
			// Check wether start is triggered
			if (properties.getProperty(ID).equals("true")) {
				triggerExecution();
			} else if ((properties.getProperty(ID) == null)) {
				setStartIDtoFalse();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}

	}

	private void loadProperties() throws FileNotFoundException, IOException {
		inputStream = new FileInputStream(PROPERTY_FILEPATH);
		properties.load(inputStream);
		inputStream.close();
	}

	private void triggerExecution() throws IOException {
		setStartIDtoFalse();
		WorkerManager workerManager = new WorkerManager(taskRegistry, this);
		workerManager.startWorkers();
	}

	private void setStartIDtoFalse() throws FileNotFoundException, IOException {
		properties.setProperty(ID, "false");
		outputStream = new FileOutputStream(PROPERTY_FILEPATH);
		properties.store(outputStream, null);
		outputStream.close();
	}

	@PostConstruct
	public void startController() {
		properties = new Properties();

		try {
			loadProperties();
			setStartIDtoFalse();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this, 5, 5, TimeUnit.SECONDS);
	}

	public Properties getProperties() {
		return properties;
	}

}
