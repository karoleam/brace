package com.flufighter.brace.ui;

import com.flufighter.brace.R;
import com.flufighter.brace.ui.detailFragments.ItemFoodFragment;
import com.flufighter.brace.ui.detailFragments.ItemFoodResultFragment;
import com.flufighter.brace.util.Constants;
import com.flufighter.brace.util.MyFragmentManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 * An activity representing a single Item detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link ItemListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ItemFoodFragment}.
 */
public class ItemDetailActivity extends FragmentActivity {

	public static final String ARG_ITEM_ID = "item_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			// Bundle arguments = new Bundle();
			// arguments.putString(ItemMenuActivity.ARG_ITEM_ID,
			// getIntent().getStringExtra(ItemMenuActivity.ARG_ITEM_ID));

			// if (getArguments().containsKey(ARG_ITEM_ID)) {
			// // Load the dummy content specified by the fragment
			// // arguments. In a real-world scenario, use a Loader
			// // to load content from a content provider.
			// // mItem =
			// DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
			// }

			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				int fragmentId = extras.getInt(ARG_ITEM_ID);
				Fragment fragment = MyFragmentManager
						.getFragmentByType(fragmentId);

				switch (fragmentId) {
				case Constants.ID_FOOD_RESULT:
					int foodId = extras.getInt(ItemFoodResultFragment.FOOD_ID);
					Bundle bundle = new Bundle();
					bundle.putInt(
							ItemFoodResultFragment.FOOD_ID, foodId);
					fragment.setArguments(bundle);
					break;
				default:

				}
				getSupportFragmentManager().beginTransaction()
						.add(R.id.item_detail_container, fragment).commit();

			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// NavUtils.navigateUpTo(this,
			// new Intent(this, ItemListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
