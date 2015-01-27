package com.ftfl.icareprofile.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.icareprofile.model.DietChart;

public class DietDataSource {
	
		// Database fields
		private SQLiteDatabase dietDatabase;
		private SQLiteHelper dietDbHelper;
		
		List<DietChart> dailyDietChart = new ArrayList<DietChart>();
		List<DietChart> todayDietChart = new ArrayList<DietChart>();
		List<String> upcomingDates = new ArrayList<String>();
		List<String> allDates = new ArrayList<String>();
		List<String> weeklyDates = new ArrayList<String>();
		List<String> monthlyDates = new ArrayList<String>();
		public String mCurrentDate = "";
		
		public DietDataSource(Context context) {
			dietDbHelper = new SQLiteHelper(context);
		}
		
		/*
		 * open a method for writable database
		 */
		public void open() throws SQLException {
			dietDatabase = dietDbHelper.getWritableDatabase();
		}
		
		/*
		 * close database connection
		 */
		public void close() {
			dietDbHelper.close();
		}
		
		public void cDate() {
			DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
			Date date = new Date();
			mCurrentDate = dateFormat.format(date);
		}
		
		/*
		 * insert data into the database.
		 */

		public boolean insert(DietChart diet) {

			this.open();

			ContentValues cv = new ContentValues();

			cv.put(SQLiteHelper.COL_DIET_DATE, diet.getDietDate());
			cv.put(SQLiteHelper.COL_DIET_TIME, diet.getDietTime());
			cv.put(SQLiteHelper.COL_DIET_FOOD_MENU,
					diet.getDietMenu());
			cv.put(SQLiteHelper.COL_DIET_EVENT_NAME,
					diet.getDietEventName());
			cv.put(SQLiteHelper.COL_DIET_ALARM,
					diet.getAlarm());
			cv.put(SQLiteHelper.COL_DIET_PROFILE_ID,
					diet.getProfileId());

			Long check = dietDatabase.insert(
					SQLiteHelper.TABLE_DIET, null, cv);
			dietDatabase.close();

			this.close();

			if (check < 0)
				return false;
			else
				return true;
		}
		
		// Updating database by id
		public boolean updateData(long eActivityId,
				DietChart diet) {
			this.open();
			ContentValues cvUpdate = new ContentValues();

			cvUpdate.put(SQLiteHelper.COL_DIET_DATE,
					diet.getDietDate());
			cvUpdate.put(SQLiteHelper.COL_DIET_TIME,
					diet.getDietTime());
			cvUpdate.put(SQLiteHelper.COL_DIET_FOOD_MENU,
					diet.getDietMenu());
			cvUpdate.put(SQLiteHelper.COL_DIET_EVENT_NAME,
					diet.getDietEventName());
			cvUpdate.put(SQLiteHelper.COL_DIET_ALARM,
					diet.getAlarm());

			int check = dietDatabase.update(SQLiteHelper.TABLE_DIET,
					cvUpdate, SQLiteHelper.COL_DIET_ID + "="
							+ eActivityId, null);
			dietDatabase.close();

			this.close();
			if (check == 0)
				return false;
			else
				return true;
		}
		
		
		// delete data form database.
		public boolean deleteData(long eActivityId) {
			this.open();
			try {
				dietDatabase.delete(SQLiteHelper.TABLE_DIET,
						SQLiteHelper.COL_DIET_ID + "=" + eActivityId,
						null);
			} catch (Exception ex) {
				Log.e("ERROR", "data insertion problem");
				return false;
			}
			this.close();
			return true;
		}
		
		/*
		 * using cursor for display data from database.
		 */
		public List<DietChart> dailyDietChart(String eDate) {
			this.open();
			this.cDate();

			Cursor mCursor = dietDatabase.query(
					SQLiteHelper.TABLE_DIET, new String[] {
							SQLiteHelper.COL_DIET_ID,
							SQLiteHelper.COL_DIET_DATE,
							SQLiteHelper.COL_DIET_TIME,
							SQLiteHelper.COL_DIET_FOOD_MENU,
							SQLiteHelper.COL_DIET_EVENT_NAME,
							SQLiteHelper.COL_DIET_ALARM,

					},
					SQLiteHelper.COL_DIET_DATE + "= '" + eDate + "' ",
					null, null, null, null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {

						String mActivityId = mCursor.getString(mCursor
								.getColumnIndex("diet_id"));
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("date"));
						String mActivityTime = mCursor.getString(mCursor
								.getColumnIndex("time"));
						String mDietFoodMenu = mCursor.getString(mCursor
								.getColumnIndex("food_menu"));
						String mDietEventName = mCursor.getString(mCursor
								.getColumnIndex("event_name"));
						String mSetAlarm = mCursor.getString(mCursor
								.getColumnIndex("set_alarm"));

						dailyDietChart.add(new DietChart(mActivityId,
								mActivityDate, mActivityTime, mDietFoodMenu,
								mDietEventName, mSetAlarm));

					} while (mCursor.moveToNext());
				}
				mCursor.close();
			}
			this.close();
			return dailyDietChart;
		}
		
		/*
		 * using cursor for display upcoming data from database.
		 */
		public List<String> upcomingDates() {
			this.open();
			this.cDate();

			Cursor mCursor = dietDatabase.rawQuery(
					"SELECT DISTINCT date FROM diet  WHERE date > '"
							+ mCurrentDate + "' ORDER BY date ASC", null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("date"));
						upcomingDates.add(mActivityDate);

					} while (mCursor.moveToNext());
				}
				mCursor.close();
			}
			this.close();
			return upcomingDates;
		}
		
		/*
		 * using cursor for display weekly data from database.
		 */
		public List<String> weeklyDates() {
			this.open();
			this.cDate();

			Cursor mCursor = dietDatabase.rawQuery(
					"SELECT DISTINCT date FROM diet  WHERE date <= '"
							+ mCurrentDate + "' ORDER BY date DESC Limit 7", null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("date"));
						weeklyDates.add(mActivityDate);

					} while (mCursor.moveToNext());
				}
				mCursor.close();
			}
			this.close();
			return weeklyDates;
		}
		
		/*
		 * using cursor for display monthly data from database.
		 */
		public List<String> monthlyDates() {
			this.open();
			this.cDate();

			Cursor mCursor = dietDatabase.rawQuery(
					"SELECT DISTINCT date FROM diet  WHERE date <='"
							+ mCurrentDate + "'", null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("date"));
						monthlyDates.add(mActivityDate);

					} while (mCursor.moveToNext());
				}
				mCursor.close();
			}
			this.close();
			return monthlyDates;
		}
		
		/*
		 * using cursor for display all data from database.
		 */
		public List<String> allDates() {
			this.open();
			this.cDate();

			Cursor mCursor = dietDatabase.rawQuery(
					"SELECT DISTINCT date FROM diet", null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("date"));
						allDates.add(mActivityDate);

					} while (mCursor.moveToNext());
				}
				mCursor.close();
			}
			this.close();
			return allDates;
		}
		
		
		
		/*
		 * create a Diet chart of ICareProfile. Here the data of the database according
		 * to the given id is set to the Diet chart and return a Diet chart object.
		 */

		public DietChart updateActivityData(long eActivityId) {
			this.open();
			DietChart UpdateActivity;

			Cursor mUpdateCursor = dietDatabase.query(
					SQLiteHelper.TABLE_DIET, new String[] {
							SQLiteHelper.COL_DIET_ID,
							SQLiteHelper.COL_DIET_DATE,
							SQLiteHelper.COL_DIET_TIME,
							SQLiteHelper.COL_DIET_FOOD_MENU,
							SQLiteHelper.COL_DIET_EVENT_NAME,
							SQLiteHelper.COL_DIET_ALARM,

					}, SQLiteHelper.COL_DIET_ID + "=" + eActivityId,
					null, null, null, null);

			mUpdateCursor.moveToFirst();

			String mActivityId = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("diet_id"));
			String mActivityDate = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("date"));
			String mActivityTime = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("time"));
			String mDietFoodMenu = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("food_menu"));
			String mDietEventName = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("event_name"));
			String mSetAlarm = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("set_alarm"));

			mUpdateCursor.close();
			UpdateActivity = new DietChart(mActivityId,
					mActivityDate, mActivityTime, mDietFoodMenu, mDietEventName,
					mSetAlarm);

			this.close();
			return UpdateActivity;
		}
		
		
		/*
		 * using cursor for display data from database.
		 */
		public List<DietChart> todayDietChart() {
			this.open();
			this.cDate();

			Cursor mCursor = dietDatabase.query(
					SQLiteHelper.TABLE_DIET, new String[] {
							SQLiteHelper.COL_DIET_ID,
							SQLiteHelper.COL_DIET_DATE,
							SQLiteHelper.COL_DIET_TIME,
							SQLiteHelper.COL_DIET_FOOD_MENU,
							SQLiteHelper.COL_DIET_EVENT_NAME,
							SQLiteHelper.COL_DIET_ALARM,

					}, SQLiteHelper.COL_DIET_DATE + "= '" + mCurrentDate
							+ "' ", null, null, null, null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {

						String mActivityId = mCursor.getString(mCursor
								.getColumnIndex("diet_id"));
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("date"));
						String mActivityTime = mCursor.getString(mCursor
								.getColumnIndex("time"));
						String mDietFoodMenu = mCursor.getString(mCursor
								.getColumnIndex("food_menu"));
						String mDietEventName = mCursor.getString(mCursor
								.getColumnIndex("event_name"));
						String mSetAlarm = mCursor.getString(mCursor
								.getColumnIndex("set_alarm"));

						todayDietChart.add(new DietChart(mActivityId,
								mActivityDate, mActivityTime, mDietFoodMenu,
								mDietEventName, mSetAlarm));

					} while (mCursor.moveToNext());
				}
				mCursor.close();
			}
			this.close();
			return todayDietChart;
		}





}
