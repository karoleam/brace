package com.flufighter.brace.util;

import java.lang.reflect.Field;

import android.util.Log;

public class Helpers {
	private static String TAG = Helpers.class.getSimpleName();

	public static int getResId(String variableName, Class<?> c) {

		try {
			Field idField = c.getDeclaredField(variableName);
			return idField.getInt(idField);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return -1;
		}
	}
}
