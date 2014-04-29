package com.flufighter.brace.dblayout;

import android.database.sqlite.SQLiteDatabase;

public class FoodTable {

	public static String TAG = FoodTable.class.getSimpleName();
	public static final String TABLE_FOOD = "food";
	public static final String COLUMN_FOOD_ID = "food_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_IMAGE_NAME = "image_name";

	public static final String COLUMN_CALORIES = "calories";

	public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_FOOD + " ( " + COLUMN_FOOD_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_NAME + " STRING, " + COLUMN_IMAGE_NAME + " STRING, "
			+ COLUMN_CALORIES + " INTEGER" +

			")";

	public static final String[] ALL_COLUMNS_FOOD = { COLUMN_FOOD_ID,
			COLUMN_NAME, COLUMN_CALORIES, COLUMN_IMAGE_NAME };

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);

	}

	public static void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);

		onCreate(db);

	}

}
