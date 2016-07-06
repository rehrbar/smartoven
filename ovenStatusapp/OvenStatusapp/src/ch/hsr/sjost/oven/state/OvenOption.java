package ch.hsr.sjost.oven.state;

public class OvenOption {
	private String optionName;
	protected OvenState next;
	
	public OvenOption(String optionName, OvenState next){
		this.optionName = optionName;
		this.next = next;
	} 
	
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public OvenState navigateForward() {
		return next;
	}
	public void setNext(OvenState next) {
		this.next = next;
	}
	@Override
	public String toString(){
		return optionName;
	}
	
}
