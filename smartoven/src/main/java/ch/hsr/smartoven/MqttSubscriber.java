package ch.hsr.smartoven;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscriber implements MqttCallback {

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(String.format("Message received on %s: %s", topic, new String(message.getPayload())));
	}

}
