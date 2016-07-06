package ch.hsr.smartoven.api;

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
	public void StartProgram(String haId, String body){
		// HTTP PUT
		// TODO implement this
		System.out.println(String.format("Starting program... haId: %s - %s",haId, body));
	}
	

	/**
	 * Stops all programs on the device.
	 */
	public void StopProgram(){
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
