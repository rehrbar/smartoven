package ch.hsr.sjost.oven.state;

import ch.hsr.sjost.oven.http.HTTPClient;

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
