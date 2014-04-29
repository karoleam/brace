package com.flufighter.brace.dblayout;

import java.util.ArrayList;

import com.flufighter.brace.entities.Building;

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

	
	/**
	 * Initializes the buildings table
	 */
	public void insertDefaultBuildings() {
		ArrayList<Building> buildings = new ArrayList<Building>();
		buildings.add(new Building("Golden Gate Bridge", 2737, "building_ggbridge"));
		buildings.add(new Building("Empire State", 381, "building_empire_state"));
		buildings.add(new Building("Brooklyn Bridge", 1825, "building_brooklyn_bridge"));
		buildings.add(new Building("Willis Tower", 442, "building_willis_tower"));

		insertBuildings(buildings);

	}

	
	/**
	 * Inserts buildings in the Building Table
	 * @param buildings an arraylist of Building
	 */
	public void insertBuildings(ArrayList<Building> buildings) {

		database = dbhelper.getWritableDatabase();
		for (Building building : buildings) {
			ContentValues values = new ContentValues();
			values.put(BuildingTable.COLUMN_NAME, building.getName());
			values.put(BuildingTable.COLUMN_LENGTH, building.getLength());
			values.put(BuildingTable.COLUMN_IMAGE_NAME, building.getImageName());

			database.insert(BuildingTable.TABLE_BUILDING, null, values);

		}
		dbhelper.close();

	}

	
	/**
	 * Reads buildings from the Building Table
	 * @return an arraylist of Building
	 */
	public ArrayList<Building> allBuildings() {

		database = dbhelper.getReadableDatabase();

		ArrayList<Building> buildings = new ArrayList<Building>();
		Cursor cursor = database.query(BuildingTable.TABLE_BUILDING,
				BuildingTable.ALL_COLUMNS_BUILDING, null, null, null, null,
				null);

		Log.i(TAG, "Returned" + cursor.getCount() + " rows");

		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String name = cursor.getString(cursor
						.getColumnIndex(BuildingTable.COLUMN_NAME));
				String imageName = cursor.getString(cursor
						.getColumnIndex(BuildingTable.COLUMN_IMAGE_NAME));
				int length = cursor.getInt(cursor
						.getColumnIndex(BuildingTable.COLUMN_LENGTH));
				int id = cursor.getInt(cursor
						.getColumnIndex(BuildingTable.COLUMN_BUILDING_ID));
				Building building = new Building(name, length, imageName);
				building.setId(id);
				buildings.add(building);

			}

		}
		cursor.close();
		Log.i(TAG, "Filled" + buildings.size() + " buildings in arraylist");
		dbhelper.close();
		return buildings;

	}

}
