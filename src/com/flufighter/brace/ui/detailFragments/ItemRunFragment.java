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

import com.flufighter.brace.R;
import com.flufighter.brace.R.layout;
import com.flufighter.brace.sample.oauth2.OAuth2Helper;
import com.flufighter.brace.sample.oauth2.Oauth2Params;
import com.flufighter.brace.ui.ItemDetailActivity;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemRunFragment extends Fragment {
	private SharedPreferences prefs;
	private TextView txtApiResponse;
	private OAuth2Helper oAuth2Helper;
	private static String TAG = ItemRunFragment.class.getSimpleName();

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

		TextView currentRankText = (TextView) rootView
				.findViewById(R.id.TextView02);
		currentRankText.setText("200"); // <--------------make this dynamic

		// Show the dummy content as text in a TextView.
		// if (mItem != null) {
		// ((TextView)
		// rootView.findViewById(R.id.item_detail)).setText(mItem.content);
		// }
		this.txtApiResponse = (TextView) rootView.findViewById(R.id.result);

		this.prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
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

	private class ApiCallExecutor extends AsyncTask<Uri, Void, Void> {

		String apiResponse = null;

		@Override
		protected Void doInBackground(Uri... params) {

			try {
				apiResponse = oAuth2Helper.executeMovesApiCall();
				Log.i(TAG, "Received response from API : " + apiResponse);
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
