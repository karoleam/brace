package com.flufighter.brace.ws.remote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JawBoneAPIHelper {
	public static float parseJsonMovesApiCall(String jsonString,
			String fieldName) {
		float result = 0;

		try {
			JSONObject data;
			data = new JSONObject(jsonString).getJSONObject("data");
			JSONArray items = data.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {
				JSONObject details = ((JSONObject) items.get(i))
						.getJSONObject("details");
				String fieldContent = details.getString(fieldName);
				result = Float.valueOf(fieldContent) + result;

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;

	}
}
