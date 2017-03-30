/**
 * 
 */
package rocks.inspectit.clt.app.controller;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tan
 *
 */
@Component
public class InfluxController implements Runnable {
	@Autowired
	PropertyProvider propertyProvider;
	/**
	 * Influx access data
	 */
	private String influxUrl = "";
	private String influxUser = "";
	private String influxPassword = "";
	private String ID = UUID.randomUUID().toString();

	private InfluxDB influxDB;

	public void run() {
		try {
			Builder builder = Point.measurement("agent_statistics").tag("agent_uuid", ID)
				.addField("count", TaskExecutionWorker.getExecuteCounter().getAndSet(0));
		influxDB.write("inspectit", "autogen", builder.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void startController(){
		influxUrl = "http://" + propertyProvider.getProperties().getProperty("INFLUX_URL")
				+ ":" + propertyProvider.getProperties().getProperty("INFLUX_PORT");
		influxUser = propertyProvider.getProperties().getProperty("INFLUX_USER");
		influxPassword = propertyProvider.getProperties().getProperty("INFLUX_PASSWORD");
		influxDB = InfluxDBFactory.connect(influxUrl, influxUser, influxPassword);

		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this, 5, 5, TimeUnit.SECONDS);
	}

}
