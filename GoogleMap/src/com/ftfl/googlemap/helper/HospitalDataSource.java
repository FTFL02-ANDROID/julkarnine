package com.ftfl.googlemap.helper;

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

import com.ftfl.googlemap.model.Hospital;

public class HospitalDataSource {
	
		// Database fields
		private SQLiteDatabase hospitalDatabase;
		private SQLiteHelper hospitalDbHelper;
		
		List<Hospital> dailyDietChart = new ArrayList<Hospital>();
		List<Hospital> todayDietChart = new ArrayList<Hospital>();
		List<String> upcomingDates = new ArrayList<String>();
		List<String> allDates = new ArrayList<String>();
		List<String> weeklyDates = new ArrayList<String>();
		List<String> monthlyDates = new ArrayList<String>();
		public String mCurrentDate = "";
		
		public HospitalDataSource(Context context) {
			hospitalDbHelper = new SQLiteHelper(context);
		}
		
		/*
		 * open a method for writable database
		 */
		public void open() throws SQLException {
			hospitalDatabase = hospitalDbHelper.getWritableDatabase();
		}
		
		/*
		 * close database connection
		 */
		public void close() {
			hospitalDbHelper.close();
		}
		
		public void cDate() {
			DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
			Date date = new Date();
			mCurrentDate = dateFormat.format(date);
		}
		
		/*
		 * insert data into the database.
		 */

		public boolean insert(Hospital hospital) {

			this.open();

			ContentValues cv = new ContentValues();

			cv.put(SQLiteHelper.COL_HOSPITAL_NAME, hospital.getHospitalName());
			cv.put(SQLiteHelper.COL_HOSPITAL_ADDRESS, hospital.getHospitalAddress());
			cv.put(SQLiteHelper.COL_HOSPITAL_LATITUDE,
					hospital.getHospitalLatitude());
			cv.put(SQLiteHelper.COL_HOSPITAL_LONGITUDE,
					hospital.getHospitalLongitude());
			cv.put(SQLiteHelper.COL_HOSPITAL_DESCRIPTION,
					hospital.getHospitalDescription());
			
			Long check = hospitalDatabase.insert(
					SQLiteHelper.TABLE_HOSPITAL, null, cv);
			hospitalDatabase.close();

			this.close();

			if (check < 0)
				return false;
			else
				return true;
		}
		
		// Updating database by id
		public boolean updateData(long eActivityId,
				Hospital hospital) {
			this.open();
			ContentValues cvUpdate = new ContentValues();

			cvUpdate.put(SQLiteHelper.COL_HOSPITAL_NAME,
					hospital.getHospitalName());
			cvUpdate.put(SQLiteHelper.COL_HOSPITAL_ADDRESS,
					hospital.getHospitalAddress());
			cvUpdate.put(SQLiteHelper.COL_HOSPITAL_LATITUDE,
					hospital.getHospitalLatitude());
			cvUpdate.put(SQLiteHelper.COL_HOSPITAL_LONGITUDE,
					hospital.getHospitalLongitude());
			cvUpdate.put(SQLiteHelper.COL_HOSPITAL_DESCRIPTION,
					hospital.getHospitalDescription());

			int check = hospitalDatabase.update(SQLiteHelper.TABLE_HOSPITAL,
					cvUpdate, SQLiteHelper.COL_HOSPITAL_ID + "="
							+ eActivityId, null);
			hospitalDatabase.close();

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
				hospitalDatabase.delete(SQLiteHelper.TABLE_HOSPITAL,
						SQLiteHelper.COL_HOSPITAL_ID + "=" + eActivityId,
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
		public List<Hospital> singleHospitalInfo(String eDate) {
			this.open();
			this.cDate();

			Cursor mCursor = hospitalDatabase.query(
					SQLiteHelper.TABLE_HOSPITAL, new String[] {
							SQLiteHelper.COL_HOSPITAL_ID,
							SQLiteHelper.COL_HOSPITAL_NAME,
							SQLiteHelper.COL_HOSPITAL_ADDRESS,
							SQLiteHelper.COL_HOSPITAL_LATITUDE,
							SQLiteHelper.COL_HOSPITAL_LONGITUDE,
							SQLiteHelper.COL_HOSPITAL_DESCRIPTION,

					},
					SQLiteHelper.COL_HOSPITAL_NAME + "= '" + eDate + "' ",
					null, null, null, null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {

						String mActivityId = mCursor.getString(mCursor
								.getColumnIndex("hospital_id"));
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("name"));
						String mActivityTime = mCursor.getString(mCursor
								.getColumnIndex("address"));
						String mDietFoodMenu = mCursor.getString(mCursor
								.getColumnIndex("latitude"));
						String mDietEventName = mCursor.getString(mCursor
								.getColumnIndex("longitude"));
						String mSetAlarm = mCursor.getString(mCursor
								.getColumnIndex("description"));

						dailyDietChart.add(new Hospital(mActivityId,
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

			Cursor mCursor = hospitalDatabase.rawQuery(
					"SELECT DISTINCT date FROM diet  WHERE date > '"
							+ mCurrentDate + "' ORDER BY date ASC", null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("name"));
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

			Cursor mCursor = hospitalDatabase.rawQuery(
					"SELECT DISTINCT date FROM diet  WHERE date <= '"
							+ mCurrentDate + "' ORDER BY date DESC Limit 7", null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("name"));
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

			Cursor mCursor = hospitalDatabase.rawQuery(
					"SELECT DISTINCT date FROM diet  WHERE date <='"
							+ mCurrentDate + "'", null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("name"));
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

			Cursor mCursor = hospitalDatabase.rawQuery(
					"SELECT DISTINCT date FROM diet", null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("name"));
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

		public Hospital updateActivityData(long eActivityId) {
			this.open();
			Hospital UpdateActivity;

			Cursor mUpdateCursor = hospitalDatabase.query(
					SQLiteHelper.TABLE_HOSPITAL, new String[] {
							SQLiteHelper.COL_HOSPITAL_ID,
							SQLiteHelper.COL_HOSPITAL_NAME,
							SQLiteHelper.COL_HOSPITAL_ADDRESS,
							SQLiteHelper.COL_HOSPITAL_LATITUDE,
							SQLiteHelper.COL_HOSPITAL_LONGITUDE,
							SQLiteHelper.COL_HOSPITAL_DESCRIPTION,

					}, SQLiteHelper.COL_HOSPITAL_ID + "=" + eActivityId,
					null, null, null, null);

			mUpdateCursor.moveToFirst();

			String mActivityId = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("hospital_id"));
			String mActivityDate = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("name"));
			String mActivityTime = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("address"));
			String mDietFoodMenu = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("latitude"));
			String mDietEventName = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("longitude"));
			String mSetAlarm = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex("description"));

			mUpdateCursor.close();
			UpdateActivity = new Hospital(mActivityId,
					mActivityDate, mActivityTime, mDietFoodMenu, mDietEventName,
					mSetAlarm);

			this.close();
			return UpdateActivity;
		}
		
		
		/*
		 * using cursor for display data from database.
		 */
		public List<Hospital> allHospitals() {
			this.open();
			this.cDate();

			Cursor mCursor = hospitalDatabase.query(
					SQLiteHelper.TABLE_HOSPITAL, new String[] {
							SQLiteHelper.COL_HOSPITAL_ID,
							SQLiteHelper.COL_HOSPITAL_NAME,
							SQLiteHelper.COL_HOSPITAL_ADDRESS,
							SQLiteHelper.COL_HOSPITAL_LATITUDE,
							SQLiteHelper.COL_HOSPITAL_LONGITUDE,
							SQLiteHelper.COL_HOSPITAL_DESCRIPTION,

					}, null, null, null, null, null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {

					do {

						String mActivityId = mCursor.getString(mCursor
								.getColumnIndex("hospital_id"));
						String mActivityDate = mCursor.getString(mCursor
								.getColumnIndex("name"));
						String mActivityTime = mCursor.getString(mCursor
								.getColumnIndex("address"));
						String mDietFoodMenu = mCursor.getString(mCursor
								.getColumnIndex("latitude"));
						String mDietEventName = mCursor.getString(mCursor
								.getColumnIndex("longitude"));
						String mSetAlarm = mCursor.getString(mCursor
								.getColumnIndex("description"));

						todayDietChart.add(new Hospital(mActivityId,
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
