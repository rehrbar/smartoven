package ch.hsr.smartoven.api.appliance;

import ch.hsr.smartoven.api.Client;
import ch.hsr.smartoven.api.CookingProgram;

public class Oven extends HomeAppliance {
	public Oven(Client client, String brand, String vib, boolean connected, String enumber, String haId){
		super(client, brand, vib, connected, HomeApplianceType.OVEN, enumber, haId);
	}
	
	private boolean isBaking = false;
	
	public void startProgram(CookingProgram program, int temperature, int duration){
		String body = String.format("{\"data\": {\"key\": \"%s\", \"options\": ["+
				"{\"key\": \"Cooking.Oven.Option.SetpointTemperature\",\"value\": %d,\"unit\": \"°C\"}"+
				",{\"key\": \"BSH.Common.Option.Duration\",\"value\": %d,\"unit\": \"seconds\"}"+
				"]}}", program, temperature, duration);
		
		this.client.startProgram(this.haId, body);
	}
	
	public void stopProgram(){
		this.client.stopProgram(this.haId);
	}
	
	public void setIsBaking(boolean isBaking){
		this.isBaking = isBaking;
	}
	
	public boolean isBaking(){
		return this.isBaking;
	}
}
