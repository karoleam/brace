package com.flufighter.brace.dblayout;

import java.util.ArrayList;

import com.flufighter.brace.entities.Building;
import com.flufighter.brace.entities.Food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BuildingDataSource {

	public static final String TAG = BuildingDataSource.class.getSimpleName();

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	public BuildingDataSource(Context context) {
		dbhelper = new DatabaseHelper(context);

	}

	public long inserBuilding(Building building) {
		return 0;

	}

	public void deleteBuilding(Building building) {

	}

	public void updateBuilding(Building building) {

	}

	public Food selectBuilding(int id) {

		return null;
	}

	public ArrayList<Building> allBuildings() {

		return null;

	}

}
