package ch.hsr.smartoven.timer;

import java.util.Timer;
import java.util.TimerTask;

import ch.hsr.smartoven.client.OvenClient;
import ch.hsr.smartoven.speaking.SpeechUtil;

public class InactivityTimer{

	private static final int TIMEOUT = 15;
	private Timer timer;
	
	public InactivityTimer(OvenClient ovenMain){
		 timer = new Timer();
		 timer.schedule(new ResetTask(ovenMain), TIMEOUT * 1000);
	}
	
	class ResetTask extends TimerTask {
		private OvenClient ovenMain;
	    public ResetTask(OvenClient ovenMain){
	    	this.ovenMain = ovenMain;
	    }
		public void run() {
			SpeechUtil.talkMessage("Time's up!");
	      ovenMain.reset();
	      timer.cancel();
	      resetTimer(ovenMain);
	    }
	  }
	
	public void resetTimer(OvenClient ovenMain){
		timer = new Timer();
		timer.schedule(new ResetTask(ovenMain), TIMEOUT * 1000);
	}
	
	
}
