package com.flufighter.brace.util;

import android.support.v4.app.Fragment;

import com.flufighter.brace.ui.detailFragments.*;
import com.flufighter.brace.util.Constants;

public class MyFragmentManager {

	/**
	 * Returns a fragment instance corresponding to the given fragment id type
	 * given
	 * 
	 * @param type
	 *            an integer that indicates the fragment type. Fragment type id
	 *            can be found in the Constants class
	 * 
	 * 
	 * */
	public static Fragment getFragmentByType(int type) {
		switch (type) {
		case Constants.ID_EXERCISE:
			return new ItemRunFragment();
		case Constants.ID_FOOD:
			return new ItemFoodFragment();

		case Constants.ID_FORECAST:
			return new ItemForecastFragment();
		case Constants.ID_SLEEP:
		default:
			return new ItemSleepFragment();
		}

	}

}
