package ch.hsr.smartoven.state;

import ch.hsr.smartoven.speaking.SpeechUtil;

public abstract class OvenState {

	private OvenState previous;
	private String statename;
	protected String messageText;
	
	public OvenState(String statename, String messageText){
		this.statename = statename;
		this.messageText = messageText;
	}
	
	
	
	public String getMessageText() {
		return messageText;
	}



	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}



	public abstract OvenState moveUp();
	public abstract OvenState moveDown();
	public OvenState moveLeft()
	{
		if(previous == null){
			this.enter();
			return this;
		} else {
			previous.enter();
			return previous;
		}
	}
	public abstract OvenState moveRight();
	
	public void enter(){
		SpeechUtil.talkMessage(messageText);
	}

	public OvenState getPrevious() {
		return previous;
	}
	
	public void setPrevious(OvenState prev) {
		this.previous = prev;
	}
	
	
}
