package ch.hsr.smartoven.speaking;

import java.io.IOException;

public class SpeechUtil {
	

	public static void talkMessage(String message){
		
		try {
			message = message.replace(" ", "_");
			Process p = Runtime.getRuntime().exec("espeak \"" + message +"\"");
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
