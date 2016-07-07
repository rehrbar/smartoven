package ch.hsr.smartoven.client;

import ch.hsr.smartoven.state.OvenState;

public class OvenClient {

	OvenState currentState = null;
	OvenState mainState = null;
	String powerState = "Off";


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

}
