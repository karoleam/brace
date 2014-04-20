package com.flufighter.brace.ws.remote;

import android.location.Location;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flufighter.brace.entities.Weather;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mark on 4/19/14.
 */
public class OpenWeatherAPI {
	/**
	 * Open Weather Map API Endpoint
	 */
	public static final String URL = "http://api.openweathermap.org/data/2.1/forecast/city?units=imperial&";

	/**
	 * Used by
	 * {@link #getWeatherData(String, com.android.volley.RequestQueue, com.marfay.weatherreport.app.OpenWeatherAPI.Callback)}
	 * to return an initialized
	 * {@link com.marfay.weatherreport.app.OpenWeatherAPI.WeatherData} object,
	 * due to async nature of making requests
	 */
	public interface Callback {
		void onWeatherData(Weather weather);
	}

	/**
	 * Makes a {@link com.android.volley.toolbox.JsonObjectRequest} with given
	 * city name and executes callback.
	 * 
	 * @param cityName
	 *            The name of the city that was passed from the voice prompt
	 * @param queue
	 *            A {@link com.android.volley.RequestQueue}
	 * @param callback
	 *            An anonymous class to handle response containing
	 *            {@link com.marfay.weatherreport.app.OpenWeatherAPI.WeatherData}
	 */
	public static void getWeatherData(Location location, RequestQueue queue,
			final Callback callback) {
		queue.add(new JsonObjectRequest(URL + "lat=" + location.getLatitude()
				+ "&lon=" + location.getLongitude(), null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Weather weather = new Weather();

							JSONObject today = (JSONObject) response
									.getJSONArray("list").get(0);
							weather.setDescription(today.getJSONObject(
									"weather").getString("description"));
							weather.setCity(response.getJSONObject("city")
									.getString("name"));

							weather.setTemperature(today.getJSONObject("main")
									.getInt("temp"));
							weather.setConditionCode(today.getJSONObject("weather")
									.getInt("id"));

							callback.onWeatherData(weather);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("onErrorResponse", error.getMessage());
					}
				}));
	}
}