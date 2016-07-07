package ch.hsr.smartoven.timer;

import java.util.Timer;
import java.util.TimerTask;

import ch.hsr.smartoven.api.appliance.Oven;
import ch.hsr.smartoven.speaking.SpeechUtil;

public class BakingTimer{

	private Timer timer;
	Oven oven;
	
	public BakingTimer(int seconds, Oven oven){
		 timer = new Timer();
		 timer.schedule(new FinishedTask(), seconds * 1000);
		 this.oven = oven;
	}
	
	public void stop(){
		timer.cancel();
	}
	
	class FinishedTask extends TimerTask {
	    public FinishedTask(){
	    }
		public void run() {
			SpeechUtil.talkMessage("Baking program finished. Your Food is ready.");
			oven.setIsBaking(false);
			timer.cancel();
	    }
	}
}
