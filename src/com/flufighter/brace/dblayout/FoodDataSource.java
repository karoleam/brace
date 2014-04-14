package com.flufighter.brace.dblayout;

import java.util.ArrayList;

import com.flufighter.brace.entities.Food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FoodDataSource {

	public static final String TAG = FoodDataSource.class.getSimpleName();

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	public FoodDataSource(Context context) {
		dbhelper = new DatabaseHelper(context);

	}

	public long insertFood(Food food) {
		return 0;

	}

	public void deleteFood(Food food) {

	}

	public void updateFood(Food food) {

	}

	public Food selectFood(int id) {

		return null;
	}

	public ArrayList<Food> allFood() {

		return null;

	}

}
