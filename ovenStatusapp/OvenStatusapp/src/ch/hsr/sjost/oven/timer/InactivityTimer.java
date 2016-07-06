package ch.hsr.sjost.oven.timer;

import java.util.Timer;
import java.util.TimerTask;

import ch.hsr.sjost.oven.main.OvenMain;
import ch.hsr.sjost.oven.speaking.SpeechUtil;

public class InactivityTimer{

	private static final int TIMEOUT = 15;
	private Timer timer;
	
	public InactivityTimer(OvenMain ovenMain){
		 timer = new Timer();
		 timer.schedule(new ResetTask(ovenMain), TIMEOUT * 1000);
	}
	
	class ResetTask extends TimerTask {
		private OvenMain ovenMain;
	    public ResetTask(OvenMain ovenMain){
	    	this.ovenMain = ovenMain;
	    }
		public void run() {
			SpeechUtil.talkMessage("Time's up!");
	      ovenMain.reset();
	      timer.cancel();
	      resetTimer(ovenMain);
	    }
	  }
	
	public void resetTimer(OvenMain ovenMain){
		timer = new Timer();
		timer.schedule(new ResetTask(ovenMain), TIMEOUT * 1000);
	}
	
	
}
