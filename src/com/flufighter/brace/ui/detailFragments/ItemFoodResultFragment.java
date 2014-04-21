package com.flufighter.brace.ui.detailFragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.flufighter.brace.R;
import com.flufighter.brace.R.layout;
import com.flufighter.brace.entities.Food;
import com.flufighter.brace.tasks.GetFoodsAsyncTask;
import com.flufighter.brace.ui.ItemDetailActivity;
import com.flufighter.brace.ui.adapter.ImageAdapter;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemFoodResultFragment extends Fragment {

	public static final String FOOD_ID = "food_id";
	private TextView textViewResult;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemFoodResultFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.right_fragment_food_result,
				container, false);
		textViewResult = (TextView) rootView
				.findViewById(R.id.textViewFoodResult);
		int foodId = getArguments().getInt(FOOD_ID);
		textViewResult.setText(foodId + "");
		return rootView;
	}
}
