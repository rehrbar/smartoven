package ch.hsr.sjost.oven.state;

import java.util.List;

import ch.hsr.sjost.oven.speaking.SpeechUtil;

public class OvenStateList extends OvenState {

	List<OvenOption> options;
	int selectedOption = 0;

	public OvenStateList(String statename, String messageText) {
		super(statename, messageText);
	}
	
	public void setOptions(List<OvenOption> options) {
		this.options = options;
	}

	@Override
	public OvenState moveUp() {
		
		if(options != null && !options.isEmpty())
		{
			if(selectedOption == 0){
				selectedOption = options.size()-1;
			}
			else {
				selectedOption--;
			}
		}
		this.enter();
		return this;
	}
	
	@Override
	public OvenState moveDown() {
		
		if(options != null && !options.isEmpty())
		{
			if(selectedOption == options.size()-1){
				selectedOption = 0;
			}
			else {
				selectedOption++;
			}
		}
		this.enter();
		return this;
	}

	@Override
	public OvenState moveRight() {
		OvenState newState = options.get(selectedOption).navigateForward();
		newState.setPrevious(this);
		newState.enter();
		return newState;
		
	}
	
	@Override
	public void enter(){
		super.enter();
		SpeechUtil.talkMessage(messageText+". Is "+options.get(selectedOption));
	}
	
}
