package com.flufighter.brace.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.flufighter.brace.R;
import com.flufighter.brace.util.Constants;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemMenuFragment extends Fragment implements OnClickListener {

	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(int id);
	}

	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */

	private Callbacks mCallbacks = sDummyCallbacks;

	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(int id) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemMenuFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// if (getArguments().containsKey(ARG_ITEM_ID)) {
		// // Load the dummy content specified by the fragment
		// // arguments. In a real-world scenario, use a Loader
		// // to load content from a content provider.
		// mItem =
		// DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
		// }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View rootView = inflater.inflate(R.layout.activity_item_detail,
		// container, false);
		View rootView = inflater.inflate(R.layout.left_menu_fragment,
				container, false);

		ImageButton imageButtonRun = (ImageButton) rootView
				.findViewById(R.id.imageButtonFood1);
		imageButtonRun.setOnClickListener(this);
		ImageButton imageButtonFood = (ImageButton) rootView
				.findViewById(R.id.imageButtonFood2);
		imageButtonFood.setOnClickListener(this);

		ImageButton imageButtonForeCast = (ImageButton) rootView
				.findViewById(R.id.imageButtonFood3);
		imageButtonForeCast.setOnClickListener(this);

		ImageButton imageButtonSleep = (ImageButton) rootView
				.findViewById(R.id.imageButtonFood4);
		imageButtonSleep.setOnClickListener(this);
		// Show the dummy content as text in a TextView.
		// if (mItem != null) {
		// ((TextView)
		// rootView.findViewById(R.id.item_detail)).setText(mItem.content);
		// }

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.imageButtonFood1:
			mCallbacks.onItemSelected(Constants.ID_EXERCISE);
			break;
		case R.id.imageButtonFood2:
			mCallbacks.onItemSelected(Constants.ID_FOOD);
			break;

		case R.id.imageButtonFood3:
			mCallbacks.onItemSelected(Constants.ID_FORECAST);
			break;

		case R.id.imageButtonFood4:
		default:
			mCallbacks.onItemSelected(Constants.ID_SLEEP);

		}
	}
}
