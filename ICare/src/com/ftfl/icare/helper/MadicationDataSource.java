package com.ftfl.icare.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.icare.model.Medication;
import com.ftfl.icare.util.ICareConstants;

public class MadicationDataSource {
	
	private SQLiteDatabase mICareDatabase;
	private ICareSQLiteHelper mICareDbHelper;
	List<Medication> mVaccinationList = new ArrayList<Medication>();

	public MadicationDataSource(Context context) {
		mICareDbHelper = new ICareSQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		mICareDatabase = mICareDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		mICareDbHelper.close();
	}

	/*
	 * insert data into the database.
	 */
	
	public boolean insert(Medication eVaccine) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_MEDICATION_NAME, eVaccine.getName());

		cv.put(ICareSQLiteHelper.COL_MEDICATION_DATE,
				eVaccine.getDate());
		cv.put(ICareSQLiteHelper.COL_MEDICATION_TIME, eVaccine.getTime());
		cv.put(ICareSQLiteHelper.COL_MEDICATION_PURPOSE, eVaccine.getPurpose());
		

		cv.put(ICareSQLiteHelper.COL_MEDICATION_ALARM,
				eVaccine.getAlarm());
		cv.put(ICareSQLiteHelper.COL_MEDICATION_PROFILE_ID,
				eVaccine.getProfileId());

		long check = mICareDatabase.insert(
				ICareSQLiteHelper.TABLE_MEDICATION, null, cv);
		mICareDatabase.close();

		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}
	

	// Updating database by id
	public boolean updateData(int eVaccineId, Medication eVaccine) {
		this.open();
		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_MEDICATION_NAME, eVaccine.getName());

		cv.put(ICareSQLiteHelper.COL_MEDICATION_DATE,
				eVaccine.getDate());
		cv.put(ICareSQLiteHelper.COL_MEDICATION_TIME, eVaccine.getTime());
		cv.put(ICareSQLiteHelper.COL_MEDICATION_PURPOSE, eVaccine.getPurpose());
		

		cv.put(ICareSQLiteHelper.COL_MEDICATION_ALARM,
				eVaccine.getAlarm());
		cv.put(ICareSQLiteHelper.COL_MEDICATION_PROFILE_ID,
				eVaccine.getProfileId());

		int check = mICareDatabase.update(
				ICareSQLiteHelper.TABLE_MEDICATION, cv,
				ICareSQLiteHelper.COL_MEDICATION_ID + "=" + eVaccineId, null);
		mICareDatabase.close();

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
			mICareDatabase.delete(ICareSQLiteHelper.TABLE_MEDICATION,
					ICareSQLiteHelper.COL_MEDICATION_ID + "=" + eVaccineId, null);
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
	public List<Medication> medicationList() {
		this.open();

		
		
		Cursor mCursor = mICareDatabase.query(
				ICareSQLiteHelper.TABLE_MEDICATION, null,
				ICareSQLiteHelper.COL_MEDICATION_PROFILE_ID + "="
						+ ICareConstants.SELECTED_PROFILE_ID, null, null, null,
				null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {
				

					String id = mCursor.getString(mCursor
							.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_ID));
					String name = mCursor.getString(mCursor
							.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_NAME));
					String date = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_DATE));
					String time = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_TIME));
					String status = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_PURPOSE));
					String alarm = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_ALARM));
					
					String profileId = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_PROFILE_ID));

					mVaccinationList.add(new Medication(id, name,
							date, time, status,alarm, profileId));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return mVaccinationList;
	}
	
	
	/*
	 * create a profile of ICareProfile. Here the data of the database according
	 * to the given id is set to the profile and return a profile.
	 */
	public Medication singleMedicationData(int eVaccineId) {
		this.open();
		Medication singleMedication;
		Cursor mCursor = mICareDatabase.query(
				ICareSQLiteHelper.TABLE_MEDICATION, null,
				ICareSQLiteHelper.COL_MEDICATION_ID + "=" + eVaccineId, null, null,
				null, null);

		mCursor.moveToFirst();


		String id = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_ID));
		String name = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_NAME));
		String date = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_DATE));
		String time = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_TIME));
		String status = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_PURPOSE));
		String alarm = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_ALARM));
		
		String profileId = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_MEDICATION_PROFILE_ID));
		mCursor.close();

		singleMedication = new Medication(id, name,
				date, time, status,alarm, profileId);
		this.close();
		return singleMedication;
	}
}
