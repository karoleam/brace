package util;

import android.support.v4.app.Fragment;

import com.flufighter.brace.ItemDetailFragment;

public class MyFragmentManager {

	public static Fragment getFragmentByType(int type) {
		switch (type) {
		case 0:
			return new ItemDetailFragment();
		default:
			return new ItemDetailFragment();

		}

	}

}
