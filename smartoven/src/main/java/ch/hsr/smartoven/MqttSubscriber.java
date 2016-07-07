package ch.hsr.smartoven;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import ch.hsr.smartoven.client.OvenClient;
import ch.hsr.smartoven.timer.InactivityTimer;

public class MqttSubscriber implements MqttCallback {
	
	private OvenClient ovenClient;
	private InactivityTimer inactivityTimer;
	
	public MqttSubscriber(OvenClient client, InactivityTimer inactivityTimer){
		this.ovenClient = client;
		this.inactivityTimer = inactivityTimer;
	}

	public void connectionLost(Throwable cause) {
		
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String messagePayload = new String(message.getPayload());
		System.out.println(String.format("Message received on %s: %s", topic, messagePayload));
		System.out.println(ovenClient.getCurrentState() != null?ovenClient.getCurrentState().getStatename() : "NULL");
		switch(messagePayload){
		case "UP": 
			if(ovenClient.getCurrentState()==null){
				ovenClient.setCurrentState(ovenClient.getMainState());
				ovenClient.getCurrentState().enter();
				inactivityTimer.resetTimer(ovenClient);
			} else {
				ovenClient.setCurrentState(ovenClient.getCurrentState().moveUp());
				inactivityTimer.resetTimer(ovenClient);
			}
			break;
		case "DOWN": 
			if(ovenClient.getCurrentState()==null){
				ovenClient.setCurrentState(ovenClient.getMainState());
				ovenClient.getCurrentState().enter();
				inactivityTimer.resetTimer(ovenClient);
			} else {
				ovenClient.setCurrentState(ovenClient.getCurrentState().moveDown());
				inactivityTimer.resetTimer(ovenClient);
			}
			break;
		case "LEFT": 
			if(ovenClient.getCurrentState()==null){
				ovenClient.setCurrentState(ovenClient.getMainState());
				ovenClient.getCurrentState().enter();
				inactivityTimer.resetTimer(ovenClient);
			} else {
				ovenClient.setCurrentState(ovenClient.getCurrentState().moveLeft());
				inactivityTimer.resetTimer(ovenClient);
			}
			break;
		case "RIGHT": 
			if(ovenClient.getCurrentState()==null){
				ovenClient.setCurrentState(ovenClient.getMainState());
				ovenClient.getCurrentState().enter();
				inactivityTimer.resetTimer(ovenClient);
			} else {
				ovenClient.setCurrentState(ovenClient.getCurrentState().moveRight());
				inactivityTimer.resetTimer(ovenClient);
			}
			break;
		default:
			break;
		}
	}

}
