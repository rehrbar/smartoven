package ch.hsr.smartoven.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import ch.hsr.smartoven.api.appliance.HomeAppliance;

public class Client {

	private String baseUri;
	
	public Client(String baseUri){
		this.baseUri = baseUri;
	}

	/**
	 * Starts a specific program on the device.
	 */
	public void startProgram(String haId, String body){
		ProcessBuilder pb = new ProcessBuilder("curl","-X PUT","--header 'Content-Type: application/vnd.bsh.sdk.v1+json'","--header 'Accept: application/vnd.bsh.sdk.v1+json'","--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0Njg0ODAwMDksImF6cCI6ImI5NjlkMWRlMGZlMzc0OTMzYzUwNWI3ZjUxZjAwNDA1YmZjZTc0NjIxNDc1YzVkYWI1MjUwMGM5MGRiYzBmZmIiLCJzdWIiOiJoY2FBcGkiLCJzY29wZSI6WyJJZGVudGlmeUFwcGxpYW5jZSIsIk1vbml0b3JBcHBsaWFuY2UiLCJDb250cm9sQXBwbGlhbmNlIl0sImFjY291bnRJZCI6IjUxZWJjYWVmLTViMjUtNDkzMS1iZDNjLTlmYmE4NzRiZGFlMCIsInRva2VuIjoiMDJhNTFhMDUtNmNiMC00MWY0LTllYzAtMDNmMDk0YTE0NmM3IiwiaXNzIjoiNTFlYmNhZWYtNWIyNS00OTMxLWJkM2MtOWZiYTg3NGJkYWUwIiwiaWF0IjoxNDY3ODc1MjA5fQ.T9z_yTyClHTPD8ZZNXYyVBaQJ8gPNUQ1bcE72OBC8MU'" ,"-d '{\"data\": {\"key\": \"Cooking.Oven.Program.HeatingMode.HotAir\",\"options\": [{\"key\": \"Cooking.Oven.Option.SetpointTemperature\", \"value\": 230, \"unit\": \"°C\" }, { \"key\": \"BSH.Common.Option.Duration\", \"value\": 1200, \"unit\": \"seconds\" } ] } }", "'https://api.home-connect.com/api/homeappliances/SIEMENS-CS658GRS6-68A40E0037D7/programs/active");
		try {
			BufferedReader reader = 
	                new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
	StringBuilder builder = new StringBuilder();
	String line = null;
	while ( (line = reader.readLine()) != null) {
	   builder.append(line);
	   builder.append(System.getProperty("line.separator"));
	}
	String result = builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(String.format("Starting program... haId: %s - %s",haId, body));
	}
	

	/**
	 * Stops all programs on the device.
	 */
	public void stopProgram(String haId){
		// HTTP DELETE
		// TODO implement this
		System.out.println("Stopping active program...");
	}
	
	/**
	 * Gets the connected home appliances.
	 * @return Returns a list of all home appliances which are paired with the logged-in user account.
	 */
	public List<HomeAppliance> getApplicances(){
		// TODO implement this
		return null;
		
	}
}
