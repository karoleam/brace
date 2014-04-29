package com.flufighter.brace.ui.detailFragments;

import java.util.ArrayList;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.GridView;

import android.widget.TextView;

import com.flufighter.brace.R;
import com.flufighter.brace.entities.Food;
import com.flufighter.brace.tasks.FoodApiCallTask;
import com.flufighter.brace.tasks.GetFoodsAsyncTask;
import com.flufighter.brace.ui.ItemDetailActivity;
import com.flufighter.brace.ui.ItemMenuActivity;
import com.flufighter.brace.ui.adapter.ImageAdapter;
import com.flufighter.brace.ws.remote.oauth2.OAuth2Helper;

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

		oAuth2Helper = new OAuth2Helper(this.prefs);
		performApiCall();
		return rootView;
	}

	/**
	 * Performs an authorized API call to get burnt calories information.
	 */
	private void performApiCall() {
		new FoodApiCallTask(oAuth2Helper, new FoodApiCallTask.Callback() {

			@Override
			public void onAPIResponse(int caloriesBurnt) {
				ItemFoodFragment.this.caloriesBurnt = caloriesBurnt;
				textViewCaloriesBurnt.setText("You burnt " + caloriesBurnt
						+ " calories, so you can eat:");
				performGetFoodTask();
			}
		}).execute();
	}

	/**
	 * Reads food items from DB and updates UI when information is available
	 */
	private void performGetFoodTask() {
		new GetFoodsAsyncTask(this, new GetFoodsAsyncTask.Callback() {

			@Override
			public void onFoodData(ArrayList<Food> foods) {

				for (Food food : foods) {
					food.setQuantity(caloriesBurnt / food.getCalories());

				}

				ImageAdapter imageAdapter = new ImageAdapter(getActivity());
				imageAdapter.setFoods(foods);
				gridview.setAdapter(imageAdapter);
				gridview.setColumnWidth(600);

			}
		}).execute();

	}

}
