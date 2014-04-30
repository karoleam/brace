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

	private SQLiteOpenHelper dbhelper;
	private SQLiteDatabase database;

	public FoodDataSource(Context context) {
		dbhelper = new DatabaseHelper(context);

	}

	/**
	 * Initializes the food table
	 */
	public void insertDefaultFoods() {
		ArrayList<Food> foods = new ArrayList<Food>();
		foods.add(new Food("Cupcake", 100, "food_cupcake"));
		foods.add(new Food("Hamburguer", 300, "food_hamburguer"));
		foods.add(new Food("Banana", 250, "food_banana"));
		foods.add(new Food("Apple", 250, "food_apple"));

		insertFoods(foods);

	}

	/**
	 * Inserts foods in the Food Table
	 * 
	 * @param fods
	 *            an arraylist of Food
	 */
	public void insertFoods(ArrayList<Food> foods) {

		database = dbhelper.getWritableDatabase();
		for (Food food : foods) {
			ContentValues values = new ContentValues();
			values.put(FoodTable.COLUMN_NAME, food.getName());
			values.put(FoodTable.COLUMN_CALORIES, food.getCalories());
			values.put(FoodTable.COLUMN_IMAGE_NAME, food.getImageName());

			database.insert(FoodTable.TABLE_FOOD, null, values);

		}
		dbhelper.close();

	}

	/**
	 * Reads foods from the Food Table
	 * 
	 * @return an arraylist of Food
	 */
	public ArrayList<Food> allFood() {

		database = dbhelper.getReadableDatabase();

		ArrayList<Food> foods = new ArrayList<Food>();
		Cursor cursor = database.query(FoodTable.TABLE_FOOD,
				FoodTable.ALL_COLUMNS_FOOD, null, null, null, null, null);

		Log.i(TAG, "Returned" + cursor.getCount() + " rows");

		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String name = cursor.getString(cursor
						.getColumnIndex(FoodTable.COLUMN_NAME));
				String imageName = cursor.getString(cursor
						.getColumnIndex(FoodTable.COLUMN_IMAGE_NAME));
				int calories = cursor.getInt(cursor
						.getColumnIndex(FoodTable.COLUMN_CALORIES));
				int id = cursor.getInt(cursor
						.getColumnIndex(FoodTable.COLUMN_FOOD_ID));
				Food food = new Food(name, calories, imageName);
				food.setId(id);
				foods.add(food);

			}

		}
		cursor.close();
		Log.i(TAG, "Filled" + foods.size() + " foods in arraylist");
		dbhelper.close();
		return foods;

	}

}
