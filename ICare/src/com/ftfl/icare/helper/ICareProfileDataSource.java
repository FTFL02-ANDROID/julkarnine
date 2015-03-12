package com.ftfl.icare.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.icare.model.ICareProfile;

public class ICareProfileDataSource {

	// Database fields
	private SQLiteDatabase mICareDatabase;
	private ICareSQLiteHelper mICareDbHelper;
	List<ICareProfile> mICareProfilesList = new ArrayList<ICareProfile>();
	List<String> iCareProfilesId = new ArrayList<String>();

	public ICareProfileDataSource(Context context) {
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

	public boolean insert(ICareProfile iprofile) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_ICARE_PROFILE_NAME, iprofile.getName());

		cv.put(ICareSQLiteHelper.COL_ICARE_PROFILE_GENDER, iprofile.getGender());
		cv.put(ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP,
				iprofile.getBloodGroup());
		cv.put(ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT, iprofile.getWeight());
		cv.put(ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT, iprofile.getHeight());
		cv.put(ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH,
				iprofile.getDateOfBirth());

		cv.put(ICareSQLiteHelper.COL_ICARE_PROFILE_IMAGES, iprofile.getImage());

		long check = mICareDatabase.insert(ICareSQLiteHelper.TABLE_I_CARE_PROFILE,
				null, cv);
		mICareDatabase.close();

		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by id
	public boolean updateData(int profileId, ICareProfile iprofile) {
		this.open();
		ContentValues cvUpdate = new ContentValues();

		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_NAME,
				iprofile.getName());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_GENDER,
				iprofile.getGender());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP,
				iprofile.getBloodGroup());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT,
				iprofile.getWeight());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT,
				iprofile.getHeight());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH,
				iprofile.getDateOfBirth());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_IMAGES,
				iprofile.getImage());

		int check = mICareDatabase.update(ICareSQLiteHelper.TABLE_I_CARE_PROFILE,
				cvUpdate, ICareSQLiteHelper.COL_ICARE_PROFILE_ID + "="
						+ profileId, null);
		mICareDatabase.close();

		this.close();
		if (check == 0)
			return false;
		else
			return true;
	}

	// delete data form database.
	public boolean deleteData(int profileId) {
		this.open();
		try {
			mICareDatabase.delete(ICareSQLiteHelper.TABLE_I_CARE_PROFILE,
					ICareSQLiteHelper.COL_ICARE_PROFILE_ID + "=" + profileId,
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
	public List<ICareProfile> iCareProfilesList() {
		this.open();

		Cursor mCursor = mICareDatabase.query(ICareSQLiteHelper.TABLE_I_CARE_PROFILE,
				new String[] { ICareSQLiteHelper.COL_ICARE_PROFILE_ID,
						ICareSQLiteHelper.COL_ICARE_PROFILE_NAME,
						ICareSQLiteHelper.COL_ICARE_PROFILE_GENDER,
						ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP,
						ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH,
						ICareSQLiteHelper.COL_ICARE_PROFILE_IMAGES }, null,
				null, null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String id = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_ID));
					String name = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_NAME));
					String gender = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_GENDER));
					String bloodGroup = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP));
					String weight = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT));
					String height = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT));
					String dateOfBirth = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH));
					String image = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_IMAGES));
					// long mmId = Long.parseLong(mId);
					mICareProfilesList.add(new ICareProfile(id, name, gender,
							dateOfBirth, height, weight, bloodGroup, image));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return mICareProfilesList;
	}

	/*
	 * create a profile of ICareProfile. Here the data of the database according
	 * to the given id is set to the profile and return a profile.
	 */
	public ICareProfile singleProfileData(int eProfileID) {
		this.open();
		ICareProfile iCareUpdateProfile;
		Cursor mUpdateCursor = mICareDatabase.query(
				ICareSQLiteHelper.TABLE_I_CARE_PROFILE, new String[] {
						ICareSQLiteHelper.COL_ICARE_PROFILE_ID,
						ICareSQLiteHelper.COL_ICARE_PROFILE_NAME,
						ICareSQLiteHelper.COL_ICARE_PROFILE_GENDER,
						ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP,
						ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH,
						ICareSQLiteHelper.COL_ICARE_PROFILE_IMAGES, },
				ICareSQLiteHelper.COL_ICARE_PROFILE_ID + "=" + eProfileID, null,
				null, null, null);

		mUpdateCursor.moveToFirst();

		String id = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_ID));
		String name = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_NAME));
		String gender = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_GENDER));
		String bloodGroup = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP));
		String weight = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT));
		String height = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT));
		String dateOfBirth = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH));
		String image = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_IMAGES));
		mUpdateCursor.close();
		
		iCareUpdateProfile = new ICareProfile(id, name, gender,
				dateOfBirth, height, weight, bloodGroup, image);
		this.close();
		return iCareUpdateProfile;
	}
	
	public List<String> profilesId()
	{
		
		this.open();

		Cursor mCursor = mICareDatabase.query(ICareSQLiteHelper.TABLE_I_CARE_PROFILE,
				new String[] { ICareSQLiteHelper.COL_ICARE_PROFILE_ID
						}, null,
				null, null, null, null);
		
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {
					
					String id = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_ID));
					
					iCareProfilesId.add(id);
				}while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		
		return iCareProfilesId;
	}

	public int profileNumber() {
		this.open();
		Cursor mCursor = mICareDatabase.query(ICareSQLiteHelper.TABLE_I_CARE_PROFILE,
				new String[] { ICareSQLiteHelper.COL_ICARE_PROFILE_ID,
						ICareSQLiteHelper.COL_ICARE_PROFILE_NAME,
						ICareSQLiteHelper.COL_ICARE_PROFILE_GENDER,
						ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP,
						ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH,
						ICareSQLiteHelper.COL_ICARE_PROFILE_IMAGES }, null,
				null, null, null, null);
		return mCursor.getCount();
		
	}

}
