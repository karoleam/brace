package com.flufighter.brace.dblayout;

import android.database.sqlite.SQLiteDatabase;

public class BuildingTable {

	public static String TAG = BuildingTable.class.getSimpleName();
	public static final String TABLE_BUILDING = "building";
	public static final String COLUMN_BUILDING_ID = "building_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_LENGTH = "length";

	public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_BUILDING + " ( " + COLUMN_BUILDING_ID
			+ " INTEGER PRIMARY KEY, " + COLUMN_NAME + " STRING, "
			+ COLUMN_LENGTH + " INTEGER" + ")";

	public static final String[] ALL_COLUMNS_BUILDING = { COLUMN_BUILDING_ID,
			COLUMN_NAME, COLUMN_LENGTH };

	public static void onCreate(SQLiteDatabase db) {

		db.execSQL(TABLE_CREATE);

	}

	public static void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUILDING);

		onCreate(db);

	}

}
