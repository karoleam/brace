package com.flufighter.brace.ui.detailFragments;

import java.util.ArrayList;
import java.util.Random;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import android.widget.ImageView;

import com.flufighter.brace.R;
import com.flufighter.brace.entities.Building;

import com.flufighter.brace.tasks.GetBuildingAsyncTask;
import com.flufighter.brace.tasks.RunApiCallTask;
import com.flufighter.brace.ui.ItemDetailActivity;

import com.flufighter.brace.util.Helpers;

import com.flufighter.brace.ws.remote.oauth2.OAuth2Helper;


/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemRunFragment extends Fragment {
	private SharedPreferences prefs;
	private OAuth2Helper oAuth2Helper;
	private int distanceWalked = 0;
	private static String TAG = ItemRunFragment.class.getSimpleName();
	private ImageView imageView;
	private TextView textViewLengthWalked;

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

		this.prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		imageView = (ImageView) rootView.findViewById(R.id.imageViewRun);
		textViewLengthWalked = (TextView) rootView.findViewById(R.id.result);

		oAuth2Helper = new OAuth2Helper(this.prefs);
		performApiCall();
		return rootView;
	}

	/**
	 * Performs an authorized API call to get walked distance information.
	 */
	private void performApiCall() {
		new RunApiCallTask(oAuth2Helper, new RunApiCallTask.Callback() {

			@Override
			public void onAPIResponse(int distanceWalked) {
				if (distanceWalked < 0) {
					Helpers.reAuthenticate(getActivity());
					return;
				}
				ItemRunFragment.this.distanceWalked = distanceWalked;
				performGetBuildingTask();
			}
		}).execute();
	}

	/**
	 * Reads buildings items from DB and updates UI when information is available
	 */
	private void performGetBuildingTask() {

		new GetBuildingAsyncTask(this, new GetBuildingAsyncTask.Callback() {

			@Override
			public void onBuildingsData(ArrayList<Building> buildings) {

				Random rand = new Random();
				Building building = buildings.get(rand.nextInt(buildings.size()));
				float fraction = distanceWalked / building.getLength();
				textViewLengthWalked.setText(fraction
						+ " times the length of the " + building.getName());

				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setImageResource(Helpers.getResId(
						building.getImageName(), R.drawable.class));

			}
		}).execute();

	}
}
