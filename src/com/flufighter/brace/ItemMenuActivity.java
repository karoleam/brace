package com.flufighter.brace;

import util.MyFragmentManager;
import backup.ItemListActivity;
import backup.ItemListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ItemDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details (if present) is a
 * {@link ItemDetailFragment}.
 * <p>
 * This activity also implements the required {@link ItemListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class ItemMenuActivity extends FragmentActivity implements
		ItemMenuFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private final static String TAG = ItemListActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_menu);
		Log.w(TAG, "onCreate");
		// Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

		if (findViewById(R.id.item_detail_container) != null) {
			Toast.makeText(this, "two fragments view", Toast.LENGTH_SHORT)
					.show();
			Log.w(TAG, "two fragments view");

			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			// ((ItemListFragment) getSupportFragmentManager().findFragmentById(
			// R.id.item_list)).setActivateOnItemClick(true);

			ItemDetailFragment fragment = new ItemDetailFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();
		} else
			Toast.makeText(this, "no two fragments view", Toast.LENGTH_SHORT)
					.show();

		// TODO: If exposing deep links into your app, handle intents here.
	}

	
	@Override
	public void onItemSelected(int id) {

		// put a switch here to define which fragment should be shwon

		switch (id) {
		case 0:
		default:
			if (mTwoPane)
			{
				
				Fragment fragment= MyFragmentManager.getFragmentByType(id) ;
				// fragment.setArguments(arguments);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.item_detail_container, fragment).commit();
				
				
			}
			else
			{
				Intent detailIntent = new Intent(this, ItemDetailActivity.class);
				detailIntent.putExtra(ItemDetailActivity.ARG_ITEM_ID, id);
				startActivity(detailIntent);
			}

		}

//		if (mTwoPane) {
//			// In two-pane mode, show the detail view in this activity by
//			// adding or replacing the detail fragment using a
//			// fragment transaction.
//			// Bundle arguments = new Bundle();
//			// arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
//			
//
//		} else {
//			// In single-pane mode, simply start the detail activity
//			// for the selected item ID.
//			
//		}
	}
	
}
