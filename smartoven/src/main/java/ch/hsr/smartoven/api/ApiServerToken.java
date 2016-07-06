package ch.hsr.smartoven.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class ApiServerToken {
	public static void GetToken() throws OAuthSystemException, IOException {

		try {
			OAuthClientRequest request = OAuthClientRequest
					.authorizationLocation("https://api.home-connect.com/security/oauth/authorize")
					.setClientId("0d6254051cba5975f916a4004b9e04a5e39baaca348c2a7868b3a1865ac05d8b")
					.setRedirectURI("https://www.example.com/redirect")
					.setScope("IdentifyAppliance")
					.setResponseType("code")
					.buildQueryMessage();

			// in web application you make redirection to uri:
			System.out.println("Visit: " + request.getLocationUri() + "\nand grant permission");

			System.out.print("Now enter the OAuth code you have received in redirect uri ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String code = br.readLine();

			request = OAuthClientRequest
					.tokenLocation("https://graph.facebook.com/oauth/access_token")
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setClientSecret("3acb294b071c9aec86d60ae3daf32a93")
					.setRedirectURI("https://www.example.com/redirect")
					.setCode(code).buildBodyMessage();

			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

			// Facebook is not fully compatible with OAuth 2.0 draft 10, access
			// token response is
			// application/x-www-form-urlencoded, not json encoded so we use
			// dedicated response class for that
			// Own response class is an easy way to deal with oauth providers
			// that introduce modifications to
			// OAuth specification
			GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);

			System.out.println("Access Token: " + oAuthResponse.getAccessToken() + ", Expires in: "
					+ oAuthResponse.getExpiresIn());
		} catch (OAuthProblemException e) {
			System.out.println("OAuth error: " + e.getError());
			System.out.println("OAuth error description: " + e.getDescription());
		}
	}

}
