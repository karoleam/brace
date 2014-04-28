package com.flufighter.brace.ui.detailFragments;

import java.util.ArrayList;

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
import com.flufighter.brace.sample.oauth2.OAuth2Helper;
import com.flufighter.brace.sample.oauth2.Oauth2Params;
import com.flufighter.brace.tasks.GetFoodsAsyncTask;
import com.flufighter.brace.ui.ItemDetailActivity;
import com.flufighter.brace.ui.ItemMenuActivity;
import com.flufighter.brace.ui.adapter.ImageAdapter;
import com.flufighter.brace.util.Constants;
import com.flufighter.brace.util.MyFragmentManager;
import com.flufighter.brace.ws.remote.JawBoneAPIHelper;
import com.flufighter.brace.ws.remote.OpenWeatherAPI.Callback;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemFoodFragment extends Fragment {
	GridView gridview;
	private static String TAG = ItemFoodFragment.class.getSimpleName();
	private SharedPreferences prefs;
	private OAuth2Helper oAuth2Helper;
	boolean isTwoPanel;
	int caloriesBurnt = 0;
	TextView textViewCaloriesBurnt;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemFoodFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		isTwoPanel = prefs.getBoolean(ItemMenuActivity.TWO_PANEL, false);
		View rootView = inflater.inflate(R.layout.right_fragment_food,
				container, false);
		gridview = (GridView) rootView.findViewById(R.id.gridview);
		textViewCaloriesBurnt = (TextView) rootView
				.findViewById(R.id.textViewFoodCaloriesBurnt);

		updateUI();

		oAuth2Helper = new OAuth2Helper(this.prefs);
		com.flufighter.brace.sample.oauth2.Constants.OAUTH2PARAMS = Oauth2Params.FOURSQUARE_OAUTH2;
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

	private class ApiCallExecutor extends AsyncTask<Uri, Void, Float> {

		String apiResponse = null;

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
			// txtApiResponse.setText(String.valueOf(parseJsonMovesApiCall(
			// apiResponse, "calories")));

			caloriesBurnt = result.intValue();
			updateUI();
		}

	}

	private void updateUI() {
		textViewCaloriesBurnt.setText("You burnt " + caloriesBurnt
				+ " calories, so you can eat:");

		GetFoodsAsyncTask task = new GetFoodsAsyncTask(this,
				new GetFoodsAsyncTask.Callback() {

					@Override
					public void onFoodData(ArrayList<Food> foods) {

						for (Food food : foods) {
							food.setQuantity(caloriesBurnt / food.getCalories());

						}

						ImageAdapter imageAdapter = new ImageAdapter(
								getActivity());
						imageAdapter.setFoods(foods);
						gridview.setAdapter(imageAdapter);
						gridview.setColumnWidth(600);
						gridview.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView<?> parent,
									View v, int position, long id) {

								// Toast.makeText(getActivity(), "" + position,
								// Toast.LENGTH_SHORT).show();
								// Bundle bundle = new Bundle();
								// bundle.putInt(ItemFoodResultFragment.FOOD_ID,
								// 10);
								// bundle.putInt(
								// ItemDetailActivity.ARG_ITEM_ID,
								// Constants.ID_FOOD_RESULT);
								// bundle.putString("imageURL", ((ImageView)
								// v).getDrawable().getT);
								// bundle.putInt(
								// ItemFoodResultFragment.FOOD_ID, 10);
								//
								// if (isTwoPanel) {
								//
								// Fragment fragment = MyFragmentManager
								// .getFragmentByType(Constants.ID_FOOD_RESULT);
								// fragment.setArguments(bundle);
								// getActivity()
								// .getSupportFragmentManager()
								// .beginTransaction()
								// .replace(
								// R.id.item_detail_container,
								// fragment).commit();
								//
								// } else {
								// Intent detailIntent = new Intent(
								// getActivity(),
								// ItemDetailActivity.class);
								// detailIntent.putExtras(bundle);
								//
								// startActivity(detailIntent);
								// }

							}
						});

					}
				});

		task.execute();

	}

}
