package com.flufighter.brace.ui.detailFragments;

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
import com.flufighter.brace.sample.oauth2.Constants;
import com.flufighter.brace.sample.oauth2.OAuth2Helper;
import com.flufighter.brace.sample.oauth2.Oauth2Params;
import com.flufighter.brace.ui.ItemDetailActivity;

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

	private static double[] VALUES = new double[] { 10, 11, 12, 13 };

	private static String[] NAME_LIST = new String[] { "A", "B", "C", "D" };

	private CategorySeries mSeries = new CategorySeries("");

	private DefaultRenderer mRenderer = new DefaultRenderer();

	private GraphicalView mChartView;
	private SharedPreferences prefs;
	private TextView txtApiResponse;
	private OAuth2Helper oAuth2Helper;

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
		View rootView = inflater.inflate(R.layout.right_fragment_sleep,
				container, false);

		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setStartAngle(90);

		for (int i = 0; i < VALUES.length; i++) {
			mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[(mSeries.getItemCount() - 1)
					% COLORS.length]);
			mRenderer.addSeriesRenderer(renderer);
		}

		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) rootView
					.findViewById(R.id.chart);
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

		
		
		this.txtApiResponse = (TextView) rootView.findViewById(R.id.result);

		this.prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		oAuth2Helper = new OAuth2Helper(this.prefs);
		Constants.OAUTH2PARAMS = Oauth2Params.FOURSQUARE_OAUTH2;
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

	private class ApiCallExecutor extends AsyncTask<Uri, Void, Void> {

		String apiResponse = null;

		@Override
		protected Void doInBackground(Uri... params) {

			try {
				apiResponse = oAuth2Helper.executeSleepApiCall();
				Log.i(Constants.TAG, "Received response from API : "
						+ apiResponse);
			} catch (Exception ex) {
				ex.printStackTrace();
				apiResponse = ex.getMessage();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			txtApiResponse.setText(apiResponse);
		}

	}

}
