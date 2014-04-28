package com.flufighter.brace.ws.remote.oauth2;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;



public class ProcessTokenTask extends AsyncTask<Uri, Void, Void> {

	public interface Callback {
		void onAuthentication(boolean handled, boolean startActivity);
	}

	private static String TAG = ProcessTokenTask.class.getSimpleName();
	String url;
	boolean startActivity = false;
	OAuth2Helper oAuth2Helper;
	boolean handled = false;
	Callback callback;

	public ProcessTokenTask(String url, OAuth2Helper oAuth2Helper,
			Callback callback) {
		this.url = url;
		this.oAuth2Helper = oAuth2Helper;
		this.callback = callback;
	}

	@Override
	protected Void doInBackground(Uri... params) {

		if (url.startsWith(Oauth2Params.JAWBONE_OAUTH2.getRederictUri())) {
			Log.i(TAG, "Redirect URL found" + url);
			handled = true;
			try {
				if (url.indexOf("code=") != -1) {
					String authorizationCode = oAuth2Helper
							.extractCodeFromUrl(url);

					Log.i(TAG, "Found code = " + authorizationCode);

					oAuth2Helper.retrieveAndStoreAccessToken(authorizationCode);
					startActivity = true;

				} else if (url.indexOf("error=") != -1) {
					startActivity = true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			Log.i(TAG, "Not doing anything for url " + url);
		}
		return null;
	}

	/**
	 * When we're done and we've retrieved either a valid token or an error from
	 * the server, we'll return to the ItemMenuActivity
	 */
	@Override
	protected void onPostExecute(Void result) {
		if (startActivity) {
			Log.i(TAG, " ++++++++++++ Starting mainscreen again");
			callback.onAuthentication(handled, startActivity);
			
		}

	}
}