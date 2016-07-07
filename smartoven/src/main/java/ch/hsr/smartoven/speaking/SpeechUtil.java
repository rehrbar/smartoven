package ch.hsr.smartoven.speaking;

import java.io.IOException;

public class SpeechUtil {
	

	public static void talkMessage(String message){
		
		try {
			message = message.replace(" ", "_");
			Process p = Runtime.getRuntime().exec("espeak \"" + message +"\"");
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
