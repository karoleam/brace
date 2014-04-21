package com.flufighter.brace.ui.adapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.flufighter.brace.R;
import com.flufighter.brace.entities.Food;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	private ArrayList<Food> foods;
	private static String TAG = ImageAdapter.class.getSimpleName();

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public void updateFoods(ArrayList<Food> foods) {
		this.foods = foods;
		notifyDataSetChanged();
	}

	public int getCount() {
		return foods.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.right_fragment_food_item, parent, false);
		}
		// ImageView imageView;

		TextView textView = (TextView) convertView
				.findViewById(R.id.textViewFoodQuantity);
		textView.setText(String.valueOf(foods.get(position).getQuantity()));
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.imageViewFoodImage);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageResource(getResId(foods.get(position).getImageName(),
				R.drawable.class));
		convertView.setLayoutParams(new GridView.LayoutParams(600, 600));
		convertView.setPadding(8, 8, 8, 8);

		// imageView = new ImageView(mContext);
		// imageView.setLayoutParams(new GridView.LayoutParams(600, 600));
		// imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		// imageView.setPadding(8, 8, 8, 8);

		// imageView.setImageResource(mThumbIds[position]);
		return convertView;
	}

	public static int getResId(String variableName, Class<?> c) {

		try {
			Field idField = c.getDeclaredField(variableName);
			return idField.getInt(idField);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return -1;
		}
	}

	// references to our images
	// private Integer[] mThumbIds = { R.drawable.food_fried_egg,
	// R.drawable.food_hamburguer, R.drawable.food_pancake,
	// R.drawable.food_hamburguer
	//
	// };

	public ArrayList<Food> getFoods() {
		return foods;
	}

	public void setFoods(ArrayList<Food> foods) {
		this.foods = foods;
	}
}