package ch.hsr.smartoven.api.appliance;

import ch.hsr.smartoven.api.Client;

/*
 *       {
        "brand": "BOSCH",
        "vib": "HNG6764B6",
        "connected": true,
        "type": "Oven",
        "enumber": "HNG6764B6/09",
        "haId": "BOSCH-HNG6764B6-0000000011FF"
      }
 */

public abstract class HomeAppliance {

	protected Client client;
	
	protected String brand;
	protected String vib;
	protected boolean connected;
	protected HomeApplianceType type;
	protected String enumber;
	protected String haId;
	
	public HomeAppliance(Client client, String brand, String vib, boolean connected, HomeApplianceType type, String enumber, String haId){
		this.client = client;
		this.brand = brand;
		this.vib = vib;
		this.connected = connected;
		this.type = type;
		this.enumber = enumber;
		this.haId = haId;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public String getHaId() {
		return haId;
	}
	
	public void stopProgram(){
		client.StopProgram();
	}
}
