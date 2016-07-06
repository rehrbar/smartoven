package ch.hsr.smartoven;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ApplicationStart {

	private static final String topic = "/button";

	public static void main(String[] args) throws IOException {
		System.out.println("SmartOven Control for handicapped people\n\n");
		Configuration config = new Configuration();

		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient sampleClient = new MqttClient(config.getMqttBroker(), config.getClientId(), persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + config.getMqttBroker());
			sampleClient.connect(connOpts);
			System.out.println("Connected");
			sampleClient.setCallback(new MqttSubscriber());
			sampleClient.subscribe(topic);
			System.out.println("Press RETURN to close...");
			System.in.read();

			sampleClient.disconnect();
			System.out.println("Disconnected");
			System.exit(0);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}
}
