package com.flufighter.brace.dblayout;

import java.util.ArrayList;

import com.flufighter.brace.entities.Building;
import com.flufighter.brace.entities.Food;
import com.flufighter.brace.entities.Sleep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SleepDataSource {

	public static final String TAG = SleepDataSource.class.getSimpleName();

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	public SleepDataSource(Context context) {
		dbhelper = new DatabaseHelper(context);

	}

	public long inserSleep(Sleep sleep) {
		return 0;

	}

	public void deleteSleep(Sleep sleep) {

	}

	public void updateSleep(Sleep sleep) {

	}

	public Food selectSleep(int id) {

		return null;
	}

	public ArrayList<Sleep> allSleeps() {

		return null;

	}

}
