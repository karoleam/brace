package com.flufighter.brace.dblayout;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = DatabaseHelper.class.getSimpleName();
	private static final String DATABASE_NAME = "brace.db";
	private static final int DATABASE_VERSION = 1; /*
													 * change it whenever making
													 * changes on db scheleton
													 */

	public DatabaseHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.i(TAG, "created instance");

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "onCreate");

		FoodTable.onCreate(db);

		Log.i(TAG, "Tables have been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "onUpgrade");

		FoodTable.onUpgrade(db, oldVersion, newVersion);

		Log.i(TAG, "Tables have been upgraded");

	}

}