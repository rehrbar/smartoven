package ch.hsr.smartoven.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import ch.hsr.smartoven.api.appliance.HomeAppliance;
import ch.hsr.smartoven.speaking.SpeechUtil;

public class Client {

	private String baseUri;
	private String accessToken;
	
	public Client(String baseUri, String accessToken){
		this.baseUri = baseUri;
		this.accessToken = accessToken;
	}

	/**
	 * Starts a specific program on the device.
	 */
	public boolean startProgram(String haId, String body){
		try {
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(baseUri+"/homeappliances/"+haId+"/programs/active")
			         .setAccessToken(this.accessToken).buildHeaderMessage();
			bearerClientRequest.setHeader("Accept", "application/vnd.bsh.sdk.v1+json");
			bearerClientRequest.setHeader("Content-Type", "application/vnd.bsh.sdk.v1+json");
			bearerClientRequest.setBody(body);
			
			oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.PUT, OAuthResourceResponse.class);
		} catch (OAuthSystemException | OAuthProblemException e) {
			SpeechUtil.talkMessage("Error occurred. Program not started");
			return false;
		}
		System.out.println(String.format("Starting program... haId: %s - %s",haId, body));
		return true;
	}
	

	/**
	 * Stops all programs on the device.
	 */
	public boolean stopProgram(String haId){
		try {
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(baseUri+"/homeappliances/"+haId+"/programs/active")
			         .setAccessToken(this.accessToken).buildHeaderMessage();
			bearerClientRequest.setHeader("Accept", "application/vnd.bsh.sdk.v1+json");
			
			oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.DELETE, OAuthResourceResponse.class);
		} catch (OAuthSystemException | OAuthProblemException e) {
			return false;
		}
		System.out.println("Stopping active program...");
		return true;
	}
	
	/**
	 * Gets the connected home appliances.
	 * @return Returns a list of all home appliances which are paired with the logged-in user account.
	 */
	public List<HomeAppliance> getApplicances(){
		// TODO implement this
		return null;
		
	}
}
