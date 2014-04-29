package com.flufighter.brace.tasks;

import java.util.ArrayList;

import com.flufighter.brace.dblayout.BuildingDataSource;
import com.flufighter.brace.entities.Building;
import com.flufighter.brace.ui.detailFragments.ItemRunFragment;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetBuildingAsyncTask extends
		AsyncTask<Integer, Integer, ArrayList<Building>> {

	public interface Callback {
		void onBuildingsData(ArrayList<Building> buildings);
	}

	Context context;
	String TAG = GetBuildingAsyncTask.class.getSimpleName();
	Callback callback;

	public GetBuildingAsyncTask(ItemRunFragment itemRunFragment,
			Callback callback) {
		this.callback = callback;
		this.context = itemRunFragment.getActivity();

		/* i never detach the fragment, so its safe to call getActivity().. */

	}

	protected ArrayList<Building> doInBackground(Integer... ints) {
		Log.i(TAG, "doInBackground");
		BuildingDataSource buildingDataSource = new BuildingDataSource(context);
		ArrayList<Building> buildings = buildingDataSource.allBuildings();
		Log.i(TAG, "buildings" + buildings.size());

		return buildings;

	}

	protected void onPostExecute(ArrayList<Building> buildings) {
		callback.onBuildingsData(buildings);

	}

}