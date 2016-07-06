package ch.hsr.smartoven.api.appliance;

public enum HomeApplianceType {
	OVEN("Oven"), DISHWASHER("Dishwasher");
	
	private String value;
	
	HomeApplianceType(String value){
		this.value = value;
	}
	
	String getValue(){
		return this.value;
	}
}
