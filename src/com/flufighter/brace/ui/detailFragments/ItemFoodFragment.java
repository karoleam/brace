package com.flufighter.brace.ui.detailFragments;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flufighter.brace.R;
import com.flufighter.brace.R.layout;
import com.flufighter.brace.entities.Food;
import com.flufighter.brace.tasks.GetFoodsAsyncTask;
import com.flufighter.brace.ui.ItemDetailActivity;
import com.flufighter.brace.ui.ItemMenuActivity;
import com.flufighter.brace.ui.adapter.ImageAdapter;
import com.flufighter.brace.util.Constants;
import com.flufighter.brace.util.MyFragmentManager;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemFoodFragment extends Fragment {
	GridView gridview;
	private SharedPreferences prefs;
	boolean isTwoPanel;
	int calories = 1000;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemFoodFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		isTwoPanel = prefs.getBoolean(ItemMenuActivity.TWO_PANEL, false);
		View rootView = inflater.inflate(R.layout.right_fragment_food,
				container, false);
		gridview = (GridView) rootView.findViewById(R.id.gridview);

		// int resID = getResources().getIdentifier(mDrawableName , "drawable",
		// getPackageName());

		// Show the dummy content as text in a TextView.
		// if (mItem != null) {
		// ((TextView)
		// rootView.findViewById(R.id.item_detail)).setText(mItem.content);
		// }

		// imageButtonFood1=(ImageButton)
		// rootView.findViewById(R.id.imageButtonFood1);
		// imageButtonFood1.setImageDrawable(getResources().getDrawable(R.drawable.food_hamburguer));
		GetFoodsAsyncTask task = new GetFoodsAsyncTask(this,
				new GetFoodsAsyncTask.Callback() {

					@Override
					public void onFoodData(ArrayList<Food> foods) {
						
						for(Food food:foods)
						{
							food.setQuantity(calories/food.getCalories());
							
						}
						
						ImageAdapter imageAdapter = new ImageAdapter(
								getActivity());
						imageAdapter.setFoods(foods);
						gridview.setAdapter(imageAdapter);
						gridview.setColumnWidth(600);
						gridview.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView<?> parent,
									View v, int position, long id) {

								// Toast.makeText(getActivity(), "" + position,
								// Toast.LENGTH_SHORT).show();
								// Bundle bundle = new Bundle();
								// bundle.putInt(ItemFoodResultFragment.FOOD_ID,
								// 10);
								// bundle.putInt(
								// ItemDetailActivity.ARG_ITEM_ID,
								// Constants.ID_FOOD_RESULT);
								// bundle.putString("imageURL", ((ImageView)
								// v).getDrawable().getT);
								// bundle.putInt(
								// ItemFoodResultFragment.FOOD_ID, 10);
								//
								// if (isTwoPanel) {
								//
								// Fragment fragment = MyFragmentManager
								// .getFragmentByType(Constants.ID_FOOD_RESULT);
								// fragment.setArguments(bundle);
								// getActivity()
								// .getSupportFragmentManager()
								// .beginTransaction()
								// .replace(
								// R.id.item_detail_container,
								// fragment).commit();
								//
								// } else {
								// Intent detailIntent = new Intent(
								// getActivity(),
								// ItemDetailActivity.class);
								// detailIntent.putExtras(bundle);
								//
								// startActivity(detailIntent);
								// }

							}
						});

					}
				});
		task.execute();

		return rootView;
	}
}
