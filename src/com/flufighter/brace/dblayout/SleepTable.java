package com.flufighter.brace.dblayout;

import android.database.sqlite.SQLiteDatabase;

public class SleepTable {

	public static String TAG = SleepTable.class.getSimpleName();
	public static final String TABLE_SLEEP = "sleep";
	public static final String COLUMN_SLEEP_ID = "sleep_id";
	public static final String COLUMN_DEPTH = "depth";
	public static final String COLUMN_TIME_START = "time_start";
	

	public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_SLEEP + " ( " + COLUMN_SLEEP_ID
			+ " INTEGER PRIMARY KEY, " + COLUMN_DEPTH + " INTEGER, "
			+ COLUMN_TIME_START + " DATE " +
			")";

	public static final String[] ALL_COLUMNS_QUIZ = { COLUMN_SLEEP_ID,
		COLUMN_DEPTH, COLUMN_TIME_START };

	public static void onCreate(SQLiteDatabase db) {

		db.execSQL(TABLE_CREATE);

	}

	public static void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLEEP);

		onCreate(db);

	}

}
