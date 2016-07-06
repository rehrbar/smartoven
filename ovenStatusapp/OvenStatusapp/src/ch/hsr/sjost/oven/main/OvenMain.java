package ch.hsr.sjost.oven.main;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.sjost.oven.state.OvenOption;
import ch.hsr.sjost.oven.state.OvenState;
import ch.hsr.sjost.oven.state.OvenStateList;
import ch.hsr.sjost.oven.state.OvenStateNumber;
import ch.hsr.sjost.oven.timer.InactivityTimer;

public class OvenMain {

	OvenState currentState = null;
	OvenState mainState = null;
	String powerState = "Off";
	
	public static void main(String[] args) {
		OvenStateList mainStatus = new OvenStateList("Main State", "Oven Status is");
		OvenStateList chooseActionStatus = new OvenStateList("Choose Action State", "Choose Action");
		OvenStateList chooseModeStatus = new OvenStateList("Choose Mode State", "Choose Mode");
		OvenStateNumber timeStatus = new OvenStateNumber("Time State", "Select Heat", 1, 2, 1, 120, mainStatus);
		OvenStateNumber heatStatus = new OvenStateNumber("Heat State", "Select Time", 10, 180, 100, 250, timeStatus);
		List<OvenOption> modeOptions = new ArrayList<OvenOption>();
		modeOptions.add(new OvenOption("Pizza", heatStatus));
		modeOptions.add(new OvenOption("Umluft", heatStatus));
		modeOptions.add(new OvenOption("Ober und Unterhitze", heatStatus));
		modeOptions.add(new OvenOption("Vorheizen", heatStatus));
		chooseModeStatus.setOptions(modeOptions);
		
		List<OvenOption> actionOptions = new ArrayList<OvenOption>();
		actionOptions.add(new OvenOption("Stop Program", mainStatus));
//		TODO: Add FireEventDecorator to this option
		actionOptions.add(new OvenOption("Start Program", chooseModeStatus));
		actionOptions.add(new OvenOption("Set Program", chooseModeStatus));
		chooseActionStatus.setOptions(actionOptions);
		
		List<OvenOption> mainOptions = new ArrayList<OvenOption>();
		mainOptions.add(new OvenOption("Choose Program", chooseActionStatus));
		mainStatus.setOptions(mainOptions);
		
		
		OvenMain exec = new OvenMain(mainStatus);
		new InactivityTimer(exec);
		try {
		exec.currentState = mainStatus;
		exec.currentState = exec.currentState.moveUp();
		Thread.sleep(2000);
		exec.currentState = exec.currentState.moveDown();
		Thread.sleep(2000);
		exec.currentState = exec.currentState.moveRight();
		Thread.sleep(2000);
		exec.currentState = exec.currentState.moveDown();
		Thread.sleep(2000);
		exec.currentState = exec.currentState.moveRight();
		Thread.sleep(2000);
		exec.currentState = exec.currentState.moveRight();
		Thread.sleep(2000);
		exec.currentState = exec.currentState.moveDown();
		Thread.sleep(2000);
		exec.currentState = exec.currentState.moveRight();
		Thread.sleep(2000);
		exec.currentState = exec.currentState.moveRight();
		Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public OvenMain(OvenState mainStatus){
		this.mainState = mainStatus;
	}
	
	public String getPowerState() {
		return powerState;
	}

	public void setPowerState(String powerState) {
		this.powerState = powerState;
	}

	public void reset() {
		this.currentState = mainState;
		this.currentState.enter();
	}

}
