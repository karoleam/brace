package com.flufighter.brace.ui;

import com.flufighter.brace.R;
import com.flufighter.brace.ui.detailFragments.ItemFoodFragment;
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

	
		if (savedInstanceState == null) {
		

			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				int fragmentId = extras.getInt(ARG_ITEM_ID);
				Fragment fragment = MyFragmentManager
						.getFragmentByType(fragmentId);

				
				getSupportFragmentManager().beginTransaction()
						.add(R.id.item_detail_container, fragment).commit();

			}
		}
	}

	
}
