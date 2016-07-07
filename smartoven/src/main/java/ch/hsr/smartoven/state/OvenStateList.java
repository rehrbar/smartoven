package ch.hsr.smartoven.state;

import java.util.List;

import ch.hsr.smartoven.http.ExecuteCommand;
import ch.hsr.smartoven.speaking.SpeechUtil;


public class OvenStateList extends OvenState {

	List<OvenOption> options;
	int selectedOption = 0;

	public OvenStateList(String statename, String messageText) {
		super(statename, messageText, null);
	}
	public OvenStateList(String statename, String messageText, ExecuteCommand sendFunction) {
		super(statename, messageText, sendFunction);
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
		this.repeatOption();
		return this;
	}

	@Override
	public OvenState moveRight() {
		executeCallable();
		OvenState newState = options.get(selectedOption).navigateForward();
		newState.setPrevious(this);
		newState.enter();
		return newState;
		
	}
	
	@Override
	public void enter(){
		SpeechUtil.talkMessage(messageText+". Is "+options.get(selectedOption));
		System.out.println(messageText+". Option is "+options.get(selectedOption));
	}
	
	public void repeatOption(){
		SpeechUtil.talkMessage("Selected is "+options.get(selectedOption));
		System.out.println("Selected is "+options.get(selectedOption));
	}

	@Override
	public String getSelectedValue() {
		return options.get(selectedOption).toString();
	}
	
}
