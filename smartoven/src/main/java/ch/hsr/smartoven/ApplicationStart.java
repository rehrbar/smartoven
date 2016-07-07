package ch.hsr.smartoven;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import ch.hsr.smartoven.api.ApiServerToken;

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
			Oven ovenImpl = new Oven(new Client("http://api.home-connect.com/api"), "SIEMENS", "1234", true, "5678", "SIEMENS-blah");
			
			OvenClient ovenClient = setupOvenClient(ovenImpl);
			
			InactivityTimer inactivityTimer = new InactivityTimer(ovenClient);
			
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
			
			@Override
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
			
			@Override
			public void execute(OvenState state) {
					oven.startProgram(CookingProgram.mapProgramToString(state.getPrevious().getPrevious().getSelectedValue()), Integer.parseInt(state.getPrevious().getSelectedValue()), Integer.parseInt(state.getSelectedValue()));
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
