/**
 *
 */
package clt.app.controller;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;

/**
 * @author tan
 *
 */
public class InfluxController implements Runnable {
	/**
	 * Influx access data
	 */
	private String influxUrl = "";
	private String influxUser = "";
	private String influxPassword = "";
	private String ID = UUID.randomUUID().toString();

	private InfluxDB influxDB;

	@Override
	public void run() {
		try {
			Builder builder = Point.measurement("agent_statistics").tag("agent_uuid", ID).addField("count", TaskExecutionWorker.getExecuteCounter().getAndSet(0));
			influxDB.write("inspectit", "autogen", builder.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		influxUrl = "http://" + System.getProperty("INFLUX_URL") + ":" + System.getProperty("INFLUX_PORT");
		influxUser = System.getProperty("INFLUX_USER");
		influxPassword = System.getProperty("INFLUX_PASSWORD");
		influxDB = InfluxDBFactory.connect(influxUrl, influxUser, influxPassword);

		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this, 5, 5, TimeUnit.SECONDS);
	}
}
