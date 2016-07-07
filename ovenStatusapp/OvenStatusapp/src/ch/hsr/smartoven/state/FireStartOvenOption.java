package ch.hsr.smartoven.state;

import ch.hsr.smartoven.http.HTTPClient;

public class FireStartOvenOption extends OvenOption {
	
	private HTTPClient httpClient;

	public FireStartOvenOption(String optionName, OvenState next, HTTPClient httpClient) {
		super(optionName, next);
		this.httpClient = httpClient;
	}
	
	@Override
	public OvenState navigateForward(){
//		httpClient.sendOvenStartProgram(mode, temperature, time);
		return next;
	}

}
