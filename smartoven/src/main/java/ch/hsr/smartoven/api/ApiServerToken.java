package ch.hsr.smartoven.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class ApiServerToken {
	public static void GetToken() throws OAuthSystemException, IOException {

		try {
			final String redirectUrl = "http://example.com";
			final String apiKey = "072b7d29a73c6a2cde7c31055d90be1ce85950f6c02752376c605ecea8132a9c";
			
			OAuthClientRequest request = OAuthClientRequest
					.authorizationLocation("https://api.home-connect.com/security/oauth/authorize")
					.setClientId(apiKey)
					.setRedirectURI(redirectUrl)
					.setScope("IdentifyAppliance MonitorAppliance ControlAppliance")
					.setResponseType("code")
					.buildQueryMessage();

			// in web application you make redirection to uri:
			System.out.println("Visit: " + request.getLocationUri() + "\nand grant permission");

			System.out.print("Now enter the OAuth code you have received in redirect uri ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String code = br.readLine();

			request = OAuthClientRequest
					.tokenLocation("https://api.home-connect.com/security/oauth/token")
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setClientId(apiKey)
					.setRedirectURI(redirectUrl)
					.setCode(code)
					.buildBodyMessage();

			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

			// Facebook is not fully compatible with OAuth 2.0 draft 10, access
			// token response is
			// application/x-www-form-urlencoded, not json encoded so we use
			// dedicated response class for that
			// Own response class is an easy way to deal with oauth providers
			// that introduce modifications to
			// OAuth specification
			OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);
			//GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);

			System.out.println("Access Token: " + oAuthResponse.getAccessToken() + ", Expires in: "
					+ oAuthResponse.getExpiresIn());
		} catch (OAuthProblemException e) {
			System.out.println("OAuth error: " + e.getError());
			System.out.println("OAuth error description: " + e.getDescription());
		}
	}

}
