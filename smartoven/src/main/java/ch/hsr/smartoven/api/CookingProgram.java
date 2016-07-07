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
    
    public static CookingProgram mapProgramToString(String str){
    	str = str.toLowerCase();
    	if(str.contains("pizza")){
    		return PIZZASETTING;
    	} else if(str.contains("preheating")){
    		return PREHEATING;
    	} else if(str.contains("hotair")){
    		return HOTAIR;
    	} else if(str.contains("topbottom")){
    		return TOPBOTTOMHEATING;
    	}
    	return PIZZASETTING;
    }
}
