package ch.hsr.smartoven.speaking;

import java.io.IOException;

public class SpeechUtil {
	

	public static void talkMessage(String message){
		
		try {
			ProcessBuilder pb = new ProcessBuilder("espeak", message);
			Process p = pb.start();
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
