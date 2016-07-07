package ch.hsr.smartoven.state;

import ch.hsr.smartoven.http.ExecuteCommand;
import ch.hsr.smartoven.speaking.SpeechUtil;

public class OvenStateNumber extends OvenState {

	private final int interval;
	private final int lowerLimit;
	private final int upperLimit;
	private final OvenState nextState;
	private int value;
	private String unit;
	
	public OvenStateNumber(String statename, String messagetext, String unit, int interval, int startvalue, int lowerLimit, int upperLimit, OvenState nextState) {
		super(statename, messagetext, null);
		this.interval = interval;
		this.value = startvalue;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.nextState = nextState;
		this.unit = unit;
	}
	public OvenStateNumber(String statename, String messagetext, String unit, int interval, int startvalue, int lowerLimit, int upperLimit, OvenState nextState, ExecuteCommand sendFunction) {
		super(statename, messagetext, sendFunction);
		this.interval = interval;
		this.value = startvalue;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.nextState = nextState;
		this.unit = unit;
	}

	@Override
	public OvenState moveUp() {
		if(value<upperLimit)
			value = value+interval;
		repeatOption();
		return this;
	}

	@Override
	public OvenState moveDown() {
		if(value>lowerLimit)
		value = value-interval;
		repeatOption();
		return this;
	}

	@Override
	public OvenState moveRight() {
		executeCallable();
		nextState.setPrevious(this);
		nextState.enter();
		return nextState;
	}
	
	@Override
	public void enter(){
		SpeechUtil.talkMessage(messageText+". Value is "+value);
		System.out.println(messageText+". Value is "+value+" "+unit);
	}
	
	public void repeatOption(){
		SpeechUtil.talkMessage("Selected Value is "+value);
		System.out.println("Value is "+value+" "+unit);
	}

	@Override
	public String getSelectedValue() {
		return String.valueOf(value);
	}

}
