package com.flufighter.brace.ui.detailFragments;

import java.util.ArrayList;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flufighter.brace.R;
import com.flufighter.brace.R.layout;
import com.flufighter.brace.ui.ItemDetailActivity;
import com.flufighter.brace.ws.remote.JawBoneAPIHelper;
import com.flufighter.brace.ws.remote.oauth2.OAuth2Helper;
import com.flufighter.brace.ws.remote.oauth2.Oauth2Params;

import android.app.Activity;
import android.view.Menu;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemSleepFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	//

	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,
			Color.MAGENTA, Color.CYAN };

	public static double[] VALUES = new double[] { 0, 0, 0 };

	private static String[] NAME_LIST = new String[] { "Light Sleep",
			"Deep Sleep", "Awake" };

	private CategorySeries mSeries = new CategorySeries("");

	private DefaultRenderer mRenderer = new DefaultRenderer();

	private GraphicalView mChartView;
	private SharedPreferences prefs;
	private TextView txtApiResponse;
	private OAuth2Helper oAuth2Helper;
	private static String TAG = ItemSleepFragment.class.getSimpleName();

	LinearLayout layout;
	int lightSleep = 0;
	int deepSleep = 0;
	int awakeSleep = 0;

	/**
	 * The dummy content this fragment is presenting.
	 */
	// private DummyContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemSleepFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		this.prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());

		oAuth2Helper = new OAuth2Helper(this.prefs);
		// Performs an authorized API call.
		performApiCall();

		View rootView = inflater.inflate(R.layout.right_fragment_sleep,
				container, false);

		layout = (LinearLayout) rootView.findViewById(R.id.chart);

		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setStartAngle(90);

		this.txtApiResponse = (TextView) rootView.findViewById(R.id.result);

		// updateUI();

		return rootView;
	}

	/**
	 * Performs an authorized API call.
	 */
	private void performApiCall() {
		new ApiCallExecutor().execute();
	}

	private class ApiCallExecutor extends
			AsyncTask<Uri, Void, ArrayList<Integer>> {

		String apiResponse = null;

		@Override
		protected ArrayList<Integer> doInBackground(Uri... params) {
			ArrayList<Integer> result = new ArrayList<Integer>();
			try {
				apiResponse = oAuth2Helper.executeSleepApiCall();
				Log.i(TAG, "Received response from API : " + apiResponse);
				result.add((int) JawBoneAPIHelper.parseJsonMovesApiCall(
						apiResponse, "light"));
				result.add((int) JawBoneAPIHelper.parseJsonMovesApiCall(
						apiResponse, "sound"));
				result.add((int) JawBoneAPIHelper.parseJsonMovesApiCall(
						apiResponse, "awake"));

			} catch (Exception ex) {
				ex.printStackTrace();
				apiResponse = ex.getMessage();
			}
			// return null;
			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<Integer> result) {
			lightSleep = result.get(0)/60;
			deepSleep = result.get(1)/60;
			awakeSleep = result.get(2)/60;
			VALUES = new double[] { lightSleep, deepSleep, awakeSleep };
			updateUI();
		}

	}

	private void updateUI() {

		// textViewLightSleep.setText("Light Sleep = "+ lightSleep + " sec ");

		for (int i = 0; i < VALUES.length; i++) {
			mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[(mSeries.getItemCount() - 1)
					% COLORS.length]);
			mRenderer.addSeriesRenderer(renderer);
		}

		if (mChartView == null) {
			// layout = (LinearLayout) rootView.findViewById(R.id.chart);
			mChartView = ChartFactory.getPieChartView(getActivity(), mSeries,
					mRenderer);
			mRenderer.setClickEnabled(true);
			mRenderer.setSelectableBuffer(10);

			mChartView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();

					if (seriesSelection == null) {
						Toast.makeText(
								getActivity(), // <--------------------------------------
								"No chart element was clicked",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(
								getActivity(), // <--------------------------------------
								"Chart element data point index "
										+ (seriesSelection.getPointIndex() + 1)
										+ " was clicked" + " point value="
										+ seriesSelection.getValue(),
								Toast.LENGTH_SHORT).show();
					}
				}
			});

			mChartView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();
					if (seriesSelection == null) {
						Toast.makeText(
								getActivity(), // <--------------------------------------
								"No chart element was long pressed",
								Toast.LENGTH_SHORT);
						return false;
					} else {
						Toast.makeText(
								getActivity(), // <--------------------------------------
								"Chart element data point index "
										+ seriesSelection.getPointIndex()
										+ " was long pressed",
								Toast.LENGTH_SHORT);
						return true;
					}
				}
			});

			layout.addView(mChartView, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		} else {
			mChartView.repaint();
		}

	}

}
