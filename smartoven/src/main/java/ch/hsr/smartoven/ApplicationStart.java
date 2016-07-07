package ch.hsr.smartoven;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import ch.hsr.smartoven.api.Client;
import ch.hsr.smartoven.api.CookingProgram;
import ch.hsr.smartoven.api.appliance.Oven;
import ch.hsr.smartoven.client.OvenClient;
import ch.hsr.smartoven.http.ExecuteCommand;
import ch.hsr.smartoven.speaking.SpeechUtil;
import ch.hsr.smartoven.state.OvenOption;
import ch.hsr.smartoven.state.OvenState;
import ch.hsr.smartoven.state.OvenStateList;
import ch.hsr.smartoven.state.OvenStateNumber;
import ch.hsr.smartoven.timer.InactivityTimer;

public class ApplicationStart {

	private static final String topic = "/button";

	public static void main(String[] args) throws IOException {
		System.out.println("SmartOven Control for handicapped people\n\n");
		Configuration config = new Configuration();

		try {
			//TODO: Get Oven from Home Appliances
			String baseUri = "https://api.home-connect.com/api";
			String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0Njg0ODQ0NzgsImF6cCI6ImI5NjlkMWRlMGZlMzc0OTMzYzUwNWI3ZjUxZjAwNDA1YmZjZTc0NjIxNDc1YzVkYWI1MjUwMGM5MGRiYzBmZmIiLCJzdWIiOiJoY2FBcGkiLCJzY29wZSI6WyJJZGVudGlmeUFwcGxpYW5jZSIsIk1vbml0b3JBcHBsaWFuY2UiLCJDb250cm9sQXBwbGlhbmNlIl0sImFjY291bnRJZCI6IjUxZWJjYWVmLTViMjUtNDkzMS1iZDNjLTlmYmE4NzRiZGFlMCIsInRva2VuIjoiMGI1MzE1YWItYjU3Yy00MWE1LTk3NzktZWRjZmNkOTU2NDgwIiwiaXNzIjoiNTFlYmNhZWYtNWIyNS00OTMxLWJkM2MtOWZiYTg3NGJkYWUwIiwiaWF0IjoxNDY3ODc5Njc4fQ.KY9aSJqxM7gr1nmM9KzrF2jlMIUogcx8EL9jC46bgoE";
			Oven ovenImpl = new Oven(new Client(baseUri, accessToken), "SIEMENS", "CS658GRS6", true, "CS658GRS6/01", "SIEMENS-CS658GRS6-68A40E0037D7");
			
			OvenClient ovenClient = setupOvenClient(ovenImpl);
			
			InactivityTimer inactivityTimer = new InactivityTimer(ovenClient);

			MemoryPersistence persistence = new MemoryPersistence();
			MqttClient sampleClient = new MqttClient(config.getMqttBroker(), config.getClientId(), persistence);
			setupMqttReciever(config, sampleClient, ovenClient, inactivityTimer);
			
			
//			ovenClient.setCurrentState(ovenClient.getMainState());
//			ovenClient.setCurrentState(ovenClient.getCurrentState().moveRight());
//			inactivityTimer.resetTimer(ovenClient);
//			ovenClient.setCurrentState(ovenClient.getCurrentState().moveDown());
//			inactivityTimer.resetTimer(ovenClient);
//			ovenClient.setCurrentState(ovenClient.getCurrentState().moveRight());
//			inactivityTimer.resetTimer(ovenClient);
//			ovenClient.setCurrentState(ovenClient.getCurrentState().moveRight());
//			inactivityTimer.resetTimer(ovenClient);
//			ovenClient.setCurrentState(ovenClient.getCurrentState().moveRight());
//			inactivityTimer.resetTimer(ovenClient);
//			ovenClient.setCurrentState(ovenClient.getCurrentState().moveRight());
//			inactivityTimer.resetTimer(ovenClient);
			
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

	private static OvenClient setupOvenClient(final Oven oven) {
		String mainStatusString = oven.isBaking()?"Oven is Baking":"Oven is Not Baking";
		final OvenStateList mainStatus = new OvenStateList("Main State", mainStatusString);
		OvenStateList chooseActionStatus = new OvenStateList("Choose Action State", "Choose Action");
		OvenStateList chooseModeStatus = new OvenStateList("Choose Mode State", "Choose Mode");
		OvenStateNumber timeStatus = new OvenStateNumber("Time State", "Select Time", "Minutes", 1, 2, 1, 120, mainStatus);
		OvenStateNumber heatStatus = new OvenStateNumber("Heat State", "Select Heat", "Degrees", 10, 180, 100, 250, timeStatus);
		List<OvenOption> modeOptions = new ArrayList<OvenOption>();
		modeOptions.add(new OvenOption("Pizza", heatStatus));
		modeOptions.add(new OvenOption("Hotair", heatStatus));
		modeOptions.add(new OvenOption("Topbottomheat", heatStatus));
		modeOptions.add(new OvenOption("Preheat", heatStatus));
		chooseModeStatus.setOptions(modeOptions);
		
		List<OvenOption> actionOptions = new ArrayList<OvenOption>();
		actionOptions.add(new OvenOption("Stop Program", mainStatus));

		ExecuteCommand stopCommand = new ExecuteCommand() {
			
			public void execute(OvenState state) {
				if(state.getSelectedValue().equals("Stop Program") && state.getStatename().equals("Choose Action State")){
					oven.stopProgram();
					oven.setIsBaking(false);
					SpeechUtil.talkMessage("Stopping Program");
					mainStatus.setMessageText(oven.isBaking()?"Oven Status is Baking":"Oven Status is Not Baking");
				}
				
			}
		};
		actionOptions.add(new OvenOption("Start Program", chooseModeStatus));
		ExecuteCommand startCommand = new ExecuteCommand() {
			
			public void execute(OvenState state) {
					CookingProgram program = CookingProgram.mapProgramToString(state.getPrevious().getPrevious().getSelectedValue());
					int temperature = Integer.parseInt(state.getPrevious().getSelectedValue());
					int minutes = Integer.parseInt(state.getSelectedValue());
					oven.startProgram(program, temperature, minutes * 60);
					oven.setIsBaking(true);
					SpeechUtil.talkMessage("Starting Program now");
					mainStatus.setMessageText(oven.isBaking()?"Oven is Baking":"Oven is Not Baking");
				
			}
		};
		chooseActionStatus.setOptions(actionOptions);
		chooseActionStatus.setSendFunction(stopCommand);
		timeStatus.setSendFunction(startCommand);
		
		List<OvenOption> mainOptions = new ArrayList<OvenOption>();
		mainOptions.add(new OvenOption("Choose Action", chooseActionStatus));
		mainStatus.setOptions(mainOptions);
		
		OvenClient ovenClient = new OvenClient(mainStatus);
		return ovenClient;
	}

	private static void setupMqttReciever(Configuration config, MqttClient sampleClient, OvenClient ovenClient, InactivityTimer inactivityTimer)
			throws MqttSecurityException, MqttException {
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		System.out.println("Connecting to broker: " + config.getMqttBroker());
		sampleClient.connect(connOpts);
		System.out.println("Connected");
		sampleClient.setCallback(new MqttSubscriber(ovenClient, inactivityTimer));
		sampleClient.subscribe(topic);
	}
}
