package ch.hsr.smartoven.state;

import ch.hsr.smartoven.speaking.SpeechUtil;

public class OvenStateNumber extends OvenState {

	private final int interval;
	private final int lowerLimit;
	private final int upperLimit;
	private final OvenState nextState;
	private int value;
	
	public OvenStateNumber(String statename, String messagetext, int interval, int startvalue, int lowerLimit, int upperLimit, OvenState nextState) {
		super(statename, messagetext);
		this.interval = interval;
		this.value = startvalue;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.nextState = nextState;
	}

	@Override
	public OvenState moveUp() {
		if(value<upperLimit)
			value = value+interval;
		enter();
		return this;
	}

	@Override
	public OvenState moveDown() {
		if(value>lowerLimit)
		value = value-interval;
		enter();
		return this;
	}

	@Override
	public OvenState moveRight() {
		nextState.setPrevious(this);
		nextState.enter();
		return nextState;
	}
	
	@Override
	public void enter(){
		super.enter();
		SpeechUtil.talkMessage("The selected Number is "+value);
	}

}
