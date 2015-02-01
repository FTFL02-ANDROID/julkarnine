package com.ftfl.toptenhospitalindhaka.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.toptenhospitalindhaka.model.Hospital;

public class HospitalDataSource {

	// Database fields
	private SQLiteDatabase mHospitalDatabase;
	private SQLiteHelper mHospitalDbHelper;

	Hospital mSingleHospital;
	List<Hospital> mHospitals = new ArrayList<Hospital>();

	public HospitalDataSource(Context context) {
		mHospitalDbHelper = new SQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		mHospitalDatabase = mHospitalDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		mHospitalDbHelper.close();
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
		cv.put(SQLiteHelper.COL_HOSPITAL_IMAGE, hospital.getHospitalImage());

		Long check = mHospitalDatabase.insert(SQLiteHelper.TABLE_HOSPITAL,
				null, cv);
		mHospitalDatabase.close();

		this.close();

		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by id
	public boolean updateData(long eActivityId, Hospital hospital) {
		this.open();
		ContentValues cvUpdate = new ContentValues();

		cvUpdate.put(SQLiteHelper.COL_HOSPITAL_NAME, hospital.getHospitalName());
		cvUpdate.put(SQLiteHelper.COL_HOSPITAL_ADDRESS,
				hospital.getHospitalAddress());
		cvUpdate.put(SQLiteHelper.COL_HOSPITAL_LATITUDE,
				hospital.getHospitalLatitude());
		cvUpdate.put(SQLiteHelper.COL_HOSPITAL_LONGITUDE,
				hospital.getHospitalLongitude());
		cvUpdate.put(SQLiteHelper.COL_HOSPITAL_DESCRIPTION,
				hospital.getHospitalDescription());
		cvUpdate.put(SQLiteHelper.COL_HOSPITAL_IMAGE,
				hospital.getHospitalImage());

		int check = mHospitalDatabase.update(SQLiteHelper.TABLE_HOSPITAL,
				cvUpdate, SQLiteHelper.COL_HOSPITAL_ID + "=" + eActivityId,
				null);
		mHospitalDatabase.close();

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
			mHospitalDatabase.delete(SQLiteHelper.TABLE_HOSPITAL,
					SQLiteHelper.COL_HOSPITAL_ID + "=" + eActivityId, null);
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
	public Hospital singleHospital(String id) {
		this.open();

		Cursor mCursor = mHospitalDatabase.query(SQLiteHelper.TABLE_HOSPITAL,
				new String[] { SQLiteHelper.COL_HOSPITAL_ID,
						SQLiteHelper.COL_HOSPITAL_NAME,
						SQLiteHelper.COL_HOSPITAL_ADDRESS,
						SQLiteHelper.COL_HOSPITAL_LATITUDE,
						SQLiteHelper.COL_HOSPITAL_LONGITUDE,
						SQLiteHelper.COL_HOSPITAL_DESCRIPTION,
						SQLiteHelper.COL_HOSPITAL_IMAGE,

				}, SQLiteHelper.COL_HOSPITAL_ID + "= '" + id + "' ", null,
				null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String mActivityId = mCursor.getString(mCursor
							.getColumnIndex("hospital_id"));
					String mActivityName = mCursor.getString(mCursor
							.getColumnIndex("name"));
					String mActivityAddress = mCursor.getString(mCursor
							.getColumnIndex("address"));
					String mActivityLatitude = mCursor.getString(mCursor
							.getColumnIndex("latitude"));
					String mActivityLongitude = mCursor.getString(mCursor
							.getColumnIndex("longitude"));
					String mActivityDescription = mCursor.getString(mCursor
							.getColumnIndex("description"));
					byte[] mImage = mCursor.getBlob(mCursor
							.getColumnIndex("image"));

					mSingleHospital = new Hospital(mActivityId, mActivityName,
							mActivityAddress, mActivityLatitude,
							mActivityLongitude, mActivityDescription, mImage);

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return mSingleHospital;
	}

	/*
	 * create a Diet chart of ICareProfile. Here the data of the database
	 * according to the given id is set to the Diet chart and return a Diet
	 * chart object.
	 */

	public Hospital updateActivityData(long eActivityId) {
		this.open();
		Hospital UpdateActivity;

		Cursor mUpdateCursor = mHospitalDatabase.query(
				SQLiteHelper.TABLE_HOSPITAL, new String[] {
						SQLiteHelper.COL_HOSPITAL_ID,
						SQLiteHelper.COL_HOSPITAL_NAME,
						SQLiteHelper.COL_HOSPITAL_ADDRESS,
						SQLiteHelper.COL_HOSPITAL_LATITUDE,
						SQLiteHelper.COL_HOSPITAL_LONGITUDE,
						SQLiteHelper.COL_HOSPITAL_DESCRIPTION,
						SQLiteHelper.COL_HOSPITAL_IMAGE,

				}, SQLiteHelper.COL_HOSPITAL_ID + "= '" + eActivityId + "' ",
				null, null, null, null);

		mUpdateCursor.moveToFirst();

		String mActivityId = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("hospital_id"));
		String mActivityName = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("name"));
		String mActivityAddress = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("address"));
		String mActivityLatitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("latitude"));
		String mActivityLongitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("longitude"));
		String mActivityDescription = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("description"));
		byte[] mImage = mUpdateCursor.getBlob(mUpdateCursor
				.getColumnIndex("image"));

		mUpdateCursor.close();
		UpdateActivity = new Hospital(mActivityId, mActivityName,
				mActivityAddress, mActivityLatitude, mActivityLongitude,
				mActivityDescription, mImage);

		this.close();
		return UpdateActivity;
	}

	/*
	 * using cursor for display data from database.
	 */
	public List<Hospital> allHospitals() {
		this.open();

		Cursor mCursor = mHospitalDatabase.query(SQLiteHelper.TABLE_HOSPITAL,
				new String[] { SQLiteHelper.COL_HOSPITAL_ID,
						SQLiteHelper.COL_HOSPITAL_NAME,
						SQLiteHelper.COL_HOSPITAL_ADDRESS,
						SQLiteHelper.COL_HOSPITAL_LATITUDE,
						SQLiteHelper.COL_HOSPITAL_LONGITUDE,
						SQLiteHelper.COL_HOSPITAL_DESCRIPTION,
						SQLiteHelper.COL_HOSPITAL_IMAGE,

				}, null, null, null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String mActivityId = mCursor.getString(mCursor
							.getColumnIndex("hospital_id"));
					String mActivityName = mCursor.getString(mCursor
							.getColumnIndex("name"));
					String mActivityAddress = mCursor.getString(mCursor
							.getColumnIndex("address"));
					String mActivityLatitude = mCursor.getString(mCursor
							.getColumnIndex("latitude"));
					String mActivityLongitude = mCursor.getString(mCursor
							.getColumnIndex("longitude"));
					String mActivityDescription = mCursor.getString(mCursor
							.getColumnIndex("description"));
					byte[] mImage = mCursor.getBlob(mCursor
							.getColumnIndex("image"));

					mHospitals.add(new Hospital(mActivityId, mActivityName,
							mActivityAddress, mActivityLatitude,
							mActivityLongitude, mActivityDescription, mImage));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return mHospitals;
	}

}
