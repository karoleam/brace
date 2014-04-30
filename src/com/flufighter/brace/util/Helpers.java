package com.flufighter.brace.util;

import java.lang.reflect.Field;

import com.flufighter.brace.ui.ItemMenuActivity;
import com.flufighter.brace.ui.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Helpers {
	private static String TAG = Helpers.class.getSimpleName();

	/**
	 * returns a resource id given the name and the resource type
	 * 
	 * 
	 * */
	public static int getResId(String variableName, Class<?> c) {

		try {
			Field idField = c.getDeclaredField(variableName);
			return idField.getInt(idField);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return -1;
		}
	}

	public static void reAuthenticate(Context context) {

		Intent intent = new Intent().setClass(context, LoginActivity.class);
		Bundle bundle = new Bundle();
		bundle.putBoolean(LoginActivity.ARG_AUTHENTICATE, true);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
}
