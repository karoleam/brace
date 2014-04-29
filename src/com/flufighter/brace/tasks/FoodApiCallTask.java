package com.flufighter.brace.tasks;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.flufighter.brace.ws.remote.JawBoneAPIHelper;
import com.flufighter.brace.ws.remote.oauth2.OAuth2Helper;

public class FoodApiCallTask extends AsyncTask<Uri, Void, Float> {
	public interface Callback {
		void onAPIResponse(int caloriesBurnt);
	}

	private static String TAG = FoodApiCallTask.class.getSimpleName();
	private Callback callback;
	private String apiResponse = null;
	private OAuth2Helper oAuth2Helper;

	public FoodApiCallTask(OAuth2Helper oAuth2Helper, Callback callback) {
		this.callback = callback;
		this.oAuth2Helper = oAuth2Helper;
	}

	@Override
	protected Float doInBackground(Uri... params) {
		float result = -1;
		try {
			apiResponse = oAuth2Helper.executeMovesApiCall();

			Log.i(TAG, "Received response from API : " + apiResponse);
			result = JawBoneAPIHelper.parseJsonMovesApiCall(apiResponse,
					"calories");
		} catch (Exception ex) {
			ex.printStackTrace();
			apiResponse = ex.getMessage();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Float result) {
		callback.onAPIResponse(result.intValue());
	}

}