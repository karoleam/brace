package com.flufighter.brace.ui.detailFragments;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.flufighter.brace.R;
import com.flufighter.brace.entities.Weather;
import com.flufighter.brace.ui.ItemDetailActivity;
import com.flufighter.brace.ws.remote.OpenWeatherAPI;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemForecastFragment extends Fragment {

	private static String TAG = ItemForecastFragment.class.getSimpleName();
	LocationManager locationManager;
	LocationListener locationListener;
	Location location;
	TextView textViewTemperature;
	TextView textViewCity;
	TextView textViewDescription;
	TextView textViewSuggestion;

	private RequestQueue mQueue;
	String locationProvider = LocationManager.NETWORK_PROVIDER;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemForecastFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		mQueue = Volley.newRequestQueue(getActivity());
		location = locationManager.getLastKnownLocation(locationProvider);

		if (location != null) {
			updateWeather();

		} else
			Toast.makeText(getActivity(), "Location is not available",
					Toast.LENGTH_SHORT).show();

		locationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					ItemForecastFragment.this.location = location;
					updateWeather();
				}

			}

			@Override
			public void onProviderDisabled(String arg0) {

			}

			@Override
			public void onProviderEnabled(String arg0) {

			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

			}

		};

	}

	@Override
	public void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);

	}

	@Override
	public void onResume() {
		super.onResume();

		locationManager.requestLocationUpdates(locationProvider,
				1000 * 60 * 10, 0, locationListener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.right_fragment_forecast,
				container, false);

		textViewDescription = (TextView) rootView
				.findViewById(R.id.textViewForecastDescription);
		textViewTemperature = (TextView) rootView
				.findViewById(R.id.textViewForecastTempeature);
		textViewSuggestion = (TextView) rootView
				.findViewById(R.id.textViewForecastSuggestion);
		textViewCity = (TextView) rootView
				.findViewById(R.id.textViewForecastCity);

		return rootView;
	}

	
	/**
	 * Performs an  API call to get weather information.
	 */
	private void updateWeather() {
		Log.i(TAG, "updateWeather");
		OpenWeatherAPI.getWeatherData(location, mQueue,
				new OpenWeatherAPI.Callback() {
					@Override
					public void onWeatherData(Weather weather) {
						ItemForecastFragment.this.textViewTemperature.setText(

						+weather.getTemperature() + "¡ Fahrenheit");

						ItemForecastFragment.this.textViewDescription
								.setText(weather.getDescription());

						ItemForecastFragment.this.textViewCity.setText(weather
								.getCity());

						// weather condition codes
						// http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
						if (weather.getConditionCode() >= 800
								&& weather.getConditionCode() < 900)
							ItemForecastFragment.this.textViewSuggestion
									.setText("So you might want to take 100 extra steps to take advantage of the weather!");
						else
							ItemForecastFragment.this.textViewSuggestion
									.setText("It's ok to relax a little bit today, maybe tomorrow weather will be in our favor");

					}
				});
	}
}
