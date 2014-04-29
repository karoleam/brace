package com.flufighter.brace.tasks;

import java.util.ArrayList;

import com.flufighter.brace.dblayout.FoodDataSource;
import com.flufighter.brace.entities.Food;
import com.flufighter.brace.ui.detailFragments.ItemFoodFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetFoodsAsyncTask extends
		AsyncTask<Integer, Integer, ArrayList<Food>> {

	public interface Callback {
		void onFoodData(ArrayList<Food> foods);
	}

	Context context;
	String TAG = GetFoodsAsyncTask.class.getSimpleName();
	Callback callback;
	private ProgressDialog dialog;

	public GetFoodsAsyncTask(ItemFoodFragment itemFoodFragment,
			Callback callback) {
		this.callback = callback;
		this.context = itemFoodFragment.getActivity();

		/* i never detach the fragment, so its safe to call getActivity().. */
		dialog = new ProgressDialog(itemFoodFragment.getActivity());
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}

	@Override
	protected void onPreExecute() {
		dialog.setMessage("Doing something, please wait.");
		dialog.show();
	}

	protected ArrayList<Food> doInBackground(Integer... ints) {
		Log.i(TAG, "doInBackground");
		FoodDataSource foodDataSource = new FoodDataSource(context);
		ArrayList<Food> foods = foodDataSource.allFood();
		Log.i(TAG, "foods" + foods.size());

		return foods;

	}

	@Override
	protected void onProgressUpdate(Integer... progress) {

	}

	protected void onPostExecute(ArrayList<Food> foods) {
		callback.onFoodData(foods);

		if (dialog.isShowing()) {
			dialog.dismiss();
		}

	}

}