package com.flufighter.brace.sample.oauth2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.flufighter.brace.sample.oauth2.store.SharedPreferencesCredentialStore;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialStore;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class OAuth2Helper {

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory(); 

	private final CredentialStore credentialStore;
	
	private AuthorizationCodeFlow flow;
private final String TAG=OAuth2Helper.class.getSimpleName();
	private Oauth2Params oauth2Params; 
	
	public OAuth2Helper(SharedPreferences sharedPreferences, Oauth2Params oauth2Params) {
		Log.i(TAG,"OAuth2Helper");
		this.credentialStore = new SharedPreferencesCredentialStore(sharedPreferences);
		this.oauth2Params = oauth2Params;
		
		this.flow = new AuthorizationCodeFlow.Builder(oauth2Params.getAccessMethod() , HTTP_TRANSPORT, JSON_FACTORY, new GenericUrl(oauth2Params.getTokenServerUrl()), new ClientParametersAuthentication(oauth2Params.getClientId(),oauth2Params.getClientSecret()), oauth2Params.getClientId(), oauth2Params.getAuthorizationServerEncodedUrl()).setCredentialStore(this.credentialStore).build();
		
		
//		try {
//		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
//		        new InputStreamReader(OAuth2Helper.class.getResourceAsStream("/client_secrets.json")));
//		
//		 this.flow = new GoogleAuthorizationCodeFlow.Builder(
//			        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
//			        Collections.singleton(PlusScopes.PLUS_ME)).setCredentialStore(credentialStore).build();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

	public OAuth2Helper(SharedPreferences sharedPreferences) {
		this(sharedPreferences,Constants.OAUTH2PARAMS);
	}
	
	public String getAuthorizationUrl() {
		String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(oauth2Params.getRederictUri()).setScopes(convertScopesToString(oauth2Params.getScope())).build();
		return authorizationUrl;
	}
	
	public void retrieveAndStoreAccessToken(String authorizationCode) throws IOException {
		Log.i(Constants.TAG,"retrieveAndStoreAccessToken for code " + authorizationCode);
		TokenResponse tokenResponse = flow.newTokenRequest(authorizationCode).setScopes(convertScopesToString(oauth2Params.getScope())).setRedirectUri(oauth2Params.getRederictUri()).execute();
		Log.i(Constants.TAG, "Found tokenResponse :");
		Log.i(Constants.TAG, "Access Token : " + tokenResponse.getAccessToken());
		Log.i(Constants.TAG, "Refresh Token : " + tokenResponse.getRefreshToken());
		flow.createAndStoreCredential(tokenResponse, oauth2Params.getUserId());
	}
	
	public static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	public static String get(String urlString, String accessToken) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// OutputStream out = null;
		InputStream in = null;
		try {
			// Write the request.
			connection.addRequestProperty("Content-Type:",
					"text/plain; charset=utf-8");
				connection.addRequestProperty("Authorization", 
						"Bearer "+accessToken);

			connection.setRequestMethod("GET");

			// Read the response.
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new IOException("Unexpected HTTP response: "
						+ connection.getResponseCode() + " "
						+ connection.getResponseMessage());
			}
			in = connection.getInputStream();
			return convertStreamToString(in);
		} finally {

			if (in != null)
				in.close();
		}
	}
	
//	public String executeApiCall() throws IOException {
//		String accessToken=flow.loadCredential(oauth2Params.getUserId()).getAccessToken();
//		Log.i(Constants.TAG,"access token" + accessToken);
/////nudge/api/v.1.1/users/@me/sleeps   https://jawbone.com/nudge/api/v.1.1/users/@me//sleeps
//		Log.i(Constants.TAG,"Executing API call at url " + this.oauth2Params.getApiUrl());
//		return get(" https://jawbone.com/nudge/api/v.1.1/users/@me//sleeps",accessToken);
//		//return get(this.oauth2Params.getApiUrl(),accessToken);
//
//		//return HTTP_TRANSPORT.createRequestFactory(loadCredential()).buildGetRequest(new GenericUrl(this.oauth2Params.getApiUrl())).execute().parseAsString();
//	}

	public String executeMovesApiCall() throws IOException {
		String accessToken=flow.loadCredential(oauth2Params.getUserId()).getAccessToken();
		return get("https://jawbone.com/nudge/api/v.1.1/users/@me/moves",accessToken);
		//return get(this.oauth2Params.getApiUrl(),accessToken);

		//return HTTP_TRANSPORT.createRequestFactory(loadCredential()).buildGetRequest(new GenericUrl(this.oauth2Params.getApiUrl())).execute().parseAsString();
	}
	public String executeSleepApiCall() throws IOException {
		String accessToken=flow.loadCredential(oauth2Params.getUserId()).getAccessToken();
		Log.i(Constants.TAG,"access token" + accessToken);
///nudge/api/v.1.1/users/@me/sleeps   https://jawbone.com/nudge/api/v.1.1/users/@me//sleeps
		Log.i(Constants.TAG,"Executing API call at url " + this.oauth2Params.getApiUrl());
		return get("https://jawbone.com/nudge/api/v.1.1/users/@me/sleeps",accessToken);
		//return get(this.oauth2Params.getApiUrl(),accessToken);

		//return HTTP_TRANSPORT.createRequestFactory(loadCredential()).buildGetRequest(new GenericUrl(this.oauth2Params.getApiUrl())).execute().parseAsString();
	}
//	public String executeApiPostCall() throws IOException {
//		Location loc = new Location("d");
//		loc.setLatitude(10);
//		loc.setLongitude(10);
//		HttpContent httpContent = new JsonHttpContent(JSON_FACTORY, loc).setWrapperKey("data"); 
//		return HTTP_TRANSPORT.createRequestFactory(loadCredential()).buildPostRequest(new GenericUrl(this.oauth2Params.getApiUrl()),httpContent).execute().parseAsString();
//	}

	
	public Credential loadCredential() throws IOException {
		return flow.loadCredential(oauth2Params.getUserId());
	}

	public void clearCredentials() throws IOException {
		 flow.getCredentialStore().delete(oauth2Params.getUserId(), null);		
	}
	
	private Collection<String> convertScopesToString(String scopesConcat) {
		String[] scopes = scopesConcat.split(",");
		Collection<String> collection = new ArrayList<String>();
		Collections.addAll(collection, scopes);
		return collection;
	}
}
