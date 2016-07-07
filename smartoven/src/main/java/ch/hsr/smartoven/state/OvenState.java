package ch.hsr.smartoven.state;

import java.util.concurrent.Callable;

import ch.hsr.smartoven.http.ExecuteCommand;
import ch.hsr.smartoven.speaking.SpeechUtil;

public abstract class OvenState {

	private OvenState previous;
	private String statename;
	protected String messageText;
	private ExecuteCommand sendFunction;
	
	
	public OvenState(String statename, String messageText, ExecuteCommand sendFunction){
		this.statename = statename;
		this.messageText = messageText;
		this.sendFunction = sendFunction;
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
		System.out.println(messageText);
	}

	public OvenState getPrevious() {
		return previous;
	}
	
	public void setPrevious(OvenState prev) {
		this.previous = prev;
	}

	protected void executeCallable(){
		if(sendFunction != null){
			try {
				sendFunction.execute(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public String getStatename() {
		return statename;
	}

	public void setSendFunction(ExecuteCommand command){
		this.sendFunction = command;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}
	
	public abstract String getSelectedValue();
	
	
}
