package ch.hsr.smartoven.client;

import ch.hsr.smartoven.api.appliance.Oven;
import ch.hsr.smartoven.state.OvenState;
import ch.hsr.smartoven.timer.BakingTimer;

public class OvenClient {

	OvenState currentState = null;
	OvenState mainState = null;
	String powerState = "Off";
	BakingTimer bakingTimer;


	public OvenClient(OvenState mainStatus){
		this.mainState = mainStatus;
	}
	
	public String getPowerState() {
		return powerState;
	}

	public void setPowerState(String powerState) {
		this.powerState = powerState;
	}

	public void reset() {
		this.currentState = null;
	}

	public OvenState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(OvenState currentState) {
		this.currentState = currentState;
	}

	public OvenState getMainState() {
		return mainState;
	}

	public void setMainState(OvenState mainState) {
		this.mainState = mainState;
	}
	
	public void startBakingTimer(int seconds, Oven oven){
		if(bakingTimer == null){
			bakingTimer = new BakingTimer(seconds, oven);
		}
	}
	
	public void stopBakingTimer(){
		if(bakingTimer != null){
			bakingTimer.stop();
			bakingTimer = null;
		}
	}

}
