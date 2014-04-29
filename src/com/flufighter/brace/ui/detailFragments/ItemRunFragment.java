package com.flufighter.brace.ui.detailFragments;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flufighter.brace.R;
import com.flufighter.brace.R.layout;
import com.flufighter.brace.entities.Food;
import com.flufighter.brace.entities.Weather;
import com.flufighter.brace.tasks.GetFoodsAsyncTask;
import com.flufighter.brace.ui.ItemDetailActivity;
import com.flufighter.brace.ui.ItemMenuActivity;
import com.flufighter.brace.ui.adapter.ImageAdapter;
import com.flufighter.brace.util.Constants;
import com.flufighter.brace.util.MyFragmentManager;
import com.flufighter.brace.ws.remote.JawBoneAPIHelper;
import com.flufighter.brace.ws.remote.OpenWeatherAPI.Callback;
import com.flufighter.brace.ws.remote.oauth2.OAuth2Helper;
import com.flufighter.brace.ws.remote.oauth2.Oauth2Params;

import com.flufighter.brace.R;
import com.flufighter.brace.R.layout;
import com.flufighter.brace.ui.ItemDetailActivity;
import com.flufighter.brace.ws.remote.JawBoneAPIHelper;
import com.flufighter.brace.ws.remote.oauth2.OAuth2Helper;
import com.flufighter.brace.ws.remote.oauth2.Oauth2Params;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemRunFragment extends Fragment {
	private SharedPreferences prefs;
	private TextView txtApiResponse;
	private OAuth2Helper oAuth2Helper;
	int distanceWalked = 0;
	int lengthOfGoldenGateBridgeMeters =2737;
	private static String TAG = ItemRunFragment.class.getSimpleName();
	
	TextView textViewLengthWalked;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemRunFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.right_fragment_run,
				container, false);
//
//		TextView currentRankText = (TextView) rootView.findViewById(R.id.TextView02);
//		currentRankText.setText("200"); // <--------------make this dynamic

		// Show the dummy content as text in a TextView.
		// if (mItem != null) {
		// ((TextView)
		// rootView.findViewById(R.id.item_detail)).setText(mItem.content);
		// }
		this.txtApiResponse = (TextView) rootView.findViewById(R.id.result);

		this.prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		
		
		textViewLengthWalked = (TextView) rootView.findViewById(R.id.TextView02);
		
		updateUI();
		
		oAuth2Helper = new OAuth2Helper(this.prefs);
		// Performs an authorized API call.
		performApiCall();
		return rootView;
	}

	/**
	 * Performs an authorized API call.
	 */
	private void performApiCall() {
		new ApiCallExecutor().execute();
	}

	private class ApiCallExecutor extends AsyncTask<Uri, Void, Integer> {

		String apiResponse = null;

		@Override
		protected Integer doInBackground(Uri... params) {
			int result = -1;
			try {
				apiResponse = oAuth2Helper.executeMovesApiCall();

				Log.i(TAG, "Received response from API : " + apiResponse);
				result = (int) JawBoneAPIHelper.parseJsonMovesApiCall(apiResponse,"distance");
			} catch (Exception ex) {
				ex.printStackTrace();
				apiResponse = ex.getMessage();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
//			txtApiResponse.setText(apiResponse);
			distanceWalked = result.intValue();
			updateUI();
		}

	}
	
	private void updateUI() {
		float fraction = distanceWalked/lengthOfGoldenGateBridgeMeters;
		textViewLengthWalked.setText(fraction + " times the length of the the Golden Gate Bridge ");
	}
	
}
