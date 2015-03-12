package com.ftfl.icare.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.icare.model.CareCenter;
import com.ftfl.icare.util.ICareConstants;

public class CareCenterDataSource {
	
	private SQLiteDatabase iCareDatabase;
	private ICareSQLiteHelper iCareDbHelper;
	List<CareCenter> vaccinationList = new ArrayList<CareCenter>();

	public CareCenterDataSource(Context context) {
		iCareDbHelper = new ICareSQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		iCareDatabase = iCareDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		iCareDbHelper.close();
	}

	/*
	 * insert data into the database.
	 */
	
	public boolean insert(CareCenter eVaccine) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_CARECENTER_NAME, eVaccine.getName());


		cv.put(ICareSQLiteHelper.COL_CARECENTER_LOCATION,
				eVaccine.getLocation());

		cv.put(ICareSQLiteHelper.COL_CARECENTER_IMAGE,
				eVaccine.getImage());
		cv.put(ICareSQLiteHelper.COL_CARECENTER_PROFILE_ID,
				eVaccine.getProfileId());

		long check = iCareDatabase.insert(
				ICareSQLiteHelper.TABLE_CARECENTER, null, cv);
		iCareDatabase.close();

		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}
	

	// Updating database by id
	public boolean updateData(int eVaccineId, CareCenter eVaccine) {
		this.open();
		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_CARECENTER_NAME, eVaccine.getName());


		cv.put(ICareSQLiteHelper.COL_CARECENTER_LOCATION,
				eVaccine.getLocation());

		cv.put(ICareSQLiteHelper.COL_CARECENTER_IMAGE,
				eVaccine.getImage());
		cv.put(ICareSQLiteHelper.COL_CARECENTER_PROFILE_ID,
				eVaccine.getProfileId());

		int check = iCareDatabase.update(
				ICareSQLiteHelper.TABLE_CARECENTER, cv,
				ICareSQLiteHelper.COL_CARECENTER_ID + "=" + eVaccineId, null);
		iCareDatabase.close();

		this.close();
		if (check == 0)
			return false;
		else
			return true;
	}
	
	// delete data form database.
	public boolean deleteData(int eVaccineId) {
		this.open();
		try {
			iCareDatabase.delete(ICareSQLiteHelper.TABLE_CARECENTER,
					ICareSQLiteHelper.COL_CARECENTER_ID + "=" + eVaccineId, null);
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
	public List<CareCenter> careCenterList() {
		this.open();

		
		
		Cursor mCursor = iCareDatabase.query(
				ICareSQLiteHelper.TABLE_CARECENTER, null,
				ICareSQLiteHelper.COL_CARECENTER_PROFILE_ID + "="
						+ ICareConstants.SELECTED_PROFILE_ID, null, null, null,
				null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {
				

					String id = mCursor.getString(mCursor
							.getColumnIndex(ICareSQLiteHelper.COL_CARECENTER_ID));
					String name = mCursor.getString(mCursor
							.getColumnIndex(ICareSQLiteHelper.COL_CARECENTER_NAME));
					String date = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_CARECENTER_LOCATION));
					String time = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_CARECENTER_IMAGE));
					String profileId = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_CARECENTER_PROFILE_ID));

					vaccinationList.add(new CareCenter(id, name, date, time, profileId));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return vaccinationList;
	}
	
	
	/*
	 * create a profile of ICareProfile. Here the data of the database according
	 * to the given id is set to the profile and return a profile.
	 */
	public CareCenter singleCareCenterData(int eVaccineId) {
		this.open();
		CareCenter singleVaccine;
		Cursor mCursor = iCareDatabase.query(
				ICareSQLiteHelper.TABLE_CARECENTER, null,
				ICareSQLiteHelper.COL_CARECENTER_ID + "=" + eVaccineId, null, null,
				null, null);

		mCursor.moveToFirst();


		String id = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_CARECENTER_ID));
		String name = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_CARECENTER_NAME));
		String date = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_CARECENTER_LOCATION));
		String time = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_CARECENTER_IMAGE));
		String profileId = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_CARECENTER_PROFILE_ID));
		mCursor.close();

		singleVaccine = new CareCenter(id, name, date, time, profileId);
		this.close();
		return singleVaccine;
	}
}
