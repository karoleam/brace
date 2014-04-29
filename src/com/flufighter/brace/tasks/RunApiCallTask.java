package com.flufighter.brace.tasks;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.flufighter.brace.ws.remote.JawBoneAPIHelper;
import com.flufighter.brace.ws.remote.oauth2.OAuth2Helper;

public class RunApiCallTask extends AsyncTask<Uri, Void, Integer> {
	public interface Callback {
		void onAPIResponse(int distanceWalked);
	}

	private static String TAG = RunApiCallTask.class.getSimpleName();
	private Callback callback;
	private String apiResponse = null;
	private OAuth2Helper oAuth2Helper;

	public RunApiCallTask(OAuth2Helper oAuth2Helper, Callback callback) {
		this.callback = callback;
		this.oAuth2Helper = oAuth2Helper;
	}


	@Override
	protected Integer doInBackground(Uri... params) {
		int result = -1;
		try {
			apiResponse = oAuth2Helper.executeMovesApiCall();

			Log.i(TAG, "Received response from API : " + apiResponse);
			result = (int) JawBoneAPIHelper.parseJsonMovesApiCall(
					apiResponse, "distance");
		} catch (Exception ex) {
			ex.printStackTrace();
			apiResponse = ex.getMessage();
		}
		return result;
	}

	

	@Override
	protected void onPostExecute(Integer result) {
		callback.onAPIResponse(result.intValue());
	}

}