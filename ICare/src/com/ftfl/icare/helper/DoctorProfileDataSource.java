package com.ftfl.icare.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.icare.model.DoctorProfile;
import com.ftfl.icare.util.ICareConstants;

public class DoctorProfileDataSource {

	private SQLiteDatabase mICareDatabase;
	private ICareSQLiteHelper mICareDbHelper;
	List<DoctorProfile> mDoctorsProfilesList = new ArrayList<DoctorProfile>();

	public DoctorProfileDataSource(Context context) {
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

	public boolean insert(DoctorProfile eDoctorProfile) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_DOCTOR_NAME, eDoctorProfile.getName());

		cv.put(ICareSQLiteHelper.COL_DOCTOR_SPECIALIZATION,
				eDoctorProfile.getSpecialization());
		cv.put(ICareSQLiteHelper.COL_DOCTOR_PHONE, eDoctorProfile.getPhone());
		cv.put(ICareSQLiteHelper.COL_DOCTOR_EMAIL, eDoctorProfile.getEmail());
		cv.put(ICareSQLiteHelper.COL_DOCTOR_ADDRESS,
				eDoctorProfile.getAddress());
		cv.put(ICareSQLiteHelper.COL_DOCTOR_PROFILE_ID,
				eDoctorProfile.getProfileId());

		long check = mICareDatabase.insert(
				ICareSQLiteHelper.TABLE_DOCTOR_PROFILE, null, cv);
		mICareDatabase.close();

		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by id
	public boolean updateData(int eProfileId, DoctorProfile eDoctorProfile) {
		this.open();
		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_DOCTOR_NAME, eDoctorProfile.getName());

		cv.put(ICareSQLiteHelper.COL_DOCTOR_SPECIALIZATION,
				eDoctorProfile.getSpecialization());
		cv.put(ICareSQLiteHelper.COL_DOCTOR_PHONE, eDoctorProfile.getPhone());
		cv.put(ICareSQLiteHelper.COL_DOCTOR_EMAIL, eDoctorProfile.getEmail());
		cv.put(ICareSQLiteHelper.COL_DOCTOR_ADDRESS,
				eDoctorProfile.getAddress());
		cv.put(ICareSQLiteHelper.COL_DOCTOR_PROFILE_ID,
				eDoctorProfile.getProfileId());

		int check = mICareDatabase.update(
				ICareSQLiteHelper.TABLE_DOCTOR_PROFILE, cv,
				ICareSQLiteHelper.COL_DOCTOR_ID + "=" + eProfileId, null);
		mICareDatabase.close();

		this.close();
		if (check == 0)
			return false;
		else
			return true;
	}

	// delete data form database.
	public boolean deleteData(int eProfileId) {
		this.open();
		try {
			mICareDatabase.delete(ICareSQLiteHelper.TABLE_DOCTOR_PROFILE,
					ICareSQLiteHelper.COL_DOCTOR_ID + "=" + eProfileId, null);
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
	public List<DoctorProfile> doctorProfileList() {
		this.open();

		Cursor mCursor = mICareDatabase.query(
				ICareSQLiteHelper.TABLE_DOCTOR_PROFILE, null,
				ICareSQLiteHelper.COL_DOCTOR_PROFILE_ID + "="
						+ ICareConstants.SELECTED_PROFILE_ID, null, null, null,
				null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String id = mCursor.getString(mCursor
							.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_ID));
					String name = mCursor.getString(mCursor
							.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_NAME));
					String specialization = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_SPECIALIZATION));
					String phone = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_PHONE));
					String email = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_EMAIL));
					String address = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_ADDRESS));
					String profileId = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_PROFILE_ID));

					mDoctorsProfilesList.add(new DoctorProfile(id, name,
							specialization, phone, email, address, profileId));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return mDoctorsProfilesList;
	}

	/*
	 * create a profile of ICareProfile. Here the data of the database according
	 * to the given id is set to the profile and return a profile.
	 */
	public DoctorProfile singleProfileData(int eProfileID) {
		this.open();
		DoctorProfile singleDoctorProfile;
		Cursor mCursor = mICareDatabase.query(
				ICareSQLiteHelper.TABLE_DOCTOR_PROFILE, null,
				ICareSQLiteHelper.COL_DOCTOR_ID + "=" + eProfileID, null, null,
				null, null);

		mCursor.moveToFirst();

		String id = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_ID));
		String name = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_NAME));
		String specialization = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_SPECIALIZATION));
		String phone = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_PHONE));
		String email = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_EMAIL));
		String address = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_ADDRESS));
		String profileId = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_DOCTOR_PROFILE_ID));
		mCursor.close();

		singleDoctorProfile = new DoctorProfile(id, name, specialization,
				phone, email, address, profileId);
		this.close();
		return singleDoctorProfile;
	}
}
