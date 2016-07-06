package ch.hsr.smartoven.api;

public enum CookingProgram {
	HOTAIR("Cooking.Oven.Program.HeatingMode.HotAir"),
    TOPBOTTOMHEATING("Cooking.Oven.Program.HeatingMode.TopBottomHeating"),
    PIZZASETTING("Cooking.Oven.Program.HeatingMode.PizzaSetting"),
    PREHEATING("Cooking.Oven.Program.HeatingMode.PreHeating");
    
    private String value;
    
    CookingProgram(String value){
    	this.value = value;
    }
    
    @Override
    public String toString(){
    	return value;
    }
}
