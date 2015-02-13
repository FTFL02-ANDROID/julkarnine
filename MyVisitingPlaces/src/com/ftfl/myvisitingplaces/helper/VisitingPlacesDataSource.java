package com.ftfl.myvisitingplaces.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.myvisitingplaces.model.VisitingPlace;

public class VisitingPlacesDataSource {

	// Database fields
	private SQLiteDatabase mPlaceDatabase;
	private SQLiteHelper mPlacesDbHelper;

	VisitingPlace mSinglePlace;
	List<VisitingPlace> mPlaces = new ArrayList<VisitingPlace>();

	public VisitingPlacesDataSource(Context context) {
		mPlacesDbHelper = new SQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		mPlaceDatabase = mPlacesDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		mPlacesDbHelper.close();
	}

	/*
	 * insert data into the database.
	 */

	public boolean insert(VisitingPlace place) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(SQLiteHelper.COL_PLACE_DATE, place.getPlaceDate());
		cv.put(SQLiteHelper.COL_PLACE_TIME, place.getPlaceTime());
		cv.put(SQLiteHelper.COL_PLACE_LATITUDE, place.getPlaceLatitude());
		cv.put(SQLiteHelper.COL_PLACE_LONGITUDE, place.getPlaceLongitude());
		cv.put(SQLiteHelper.COL_PLACE_DESCRIPTION, place.getPlaceDescription());
		cv.put(SQLiteHelper.COL_PLACE_IMAGE, place.getPlaceImage());
		cv.put(SQLiteHelper.COL_PLACE_NAME, place.getPlaceName());
		cv.put(SQLiteHelper.COL_PLACE_EMAIL, place.getPlaceEmail());
		cv.put(SQLiteHelper.COL_PLACE_PHONE, place.getPlacePhone());
		

		Long check = mPlaceDatabase.insert(SQLiteHelper.TABLE_PLACES, null, cv);
		mPlaceDatabase.close();

		this.close();

		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by id
	public boolean updateData(long eActivityId, VisitingPlace place) {
		this.open();
		ContentValues cvUpdate = new ContentValues();

		cvUpdate.put(SQLiteHelper.COL_PLACE_DATE, place.getPlaceDate());
		cvUpdate.put(SQLiteHelper.COL_PLACE_TIME, place.getPlaceTime());
		cvUpdate.put(SQLiteHelper.COL_PLACE_LATITUDE, place.getPlaceLatitude());
		cvUpdate.put(SQLiteHelper.COL_PLACE_LONGITUDE,
				place.getPlaceLongitude());
		cvUpdate.put(SQLiteHelper.COL_PLACE_DESCRIPTION,
				place.getPlaceDescription());
		cvUpdate.put(SQLiteHelper.COL_PLACE_IMAGE, place.getPlaceImage());
		cvUpdate.put(SQLiteHelper.COL_PLACE_NAME, place.getPlaceName());
		cvUpdate.put(SQLiteHelper.COL_PLACE_EMAIL, place.getPlaceEmail());
		cvUpdate.put(SQLiteHelper.COL_PLACE_PHONE, place.getPlacePhone());

		int check = mPlaceDatabase.update(SQLiteHelper.TABLE_PLACES, cvUpdate,
				SQLiteHelper.COL_PLACE_ID + "=" + eActivityId, null);
		mPlaceDatabase.close();

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
			mPlaceDatabase.delete(SQLiteHelper.TABLE_PLACES,
					SQLiteHelper.COL_PLACE_ID + "=" + eActivityId, null);
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
	public VisitingPlace singlePlace(String id) {
		this.open();

		Cursor mCursor = mPlaceDatabase.query(SQLiteHelper.TABLE_PLACES,
				new String[] { SQLiteHelper.COL_PLACE_ID,
				SQLiteHelper.COL_PLACE_DATE,
				SQLiteHelper.COL_PLACE_TIME,
				SQLiteHelper.COL_PLACE_LATITUDE,
				SQLiteHelper.COL_PLACE_LONGITUDE,
				SQLiteHelper.COL_PLACE_DESCRIPTION,
				SQLiteHelper.COL_PLACE_IMAGE,
				SQLiteHelper.COL_PLACE_NAME,
				SQLiteHelper.COL_PLACE_EMAIL,
				SQLiteHelper.COL_PLACE_PHONE,

				}, SQLiteHelper.COL_PLACE_ID + "= '" + id + "' ", null, null,
				null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String mPlaceId = mCursor.getString(mCursor
							.getColumnIndex("id"));
					String mPlaceDate = mCursor.getString(mCursor
							.getColumnIndex("date"));
					String mPlaceTime = mCursor.getString(mCursor
							.getColumnIndex("time"));
					String mPlaceLatitude = mCursor.getString(mCursor
							.getColumnIndex("latitude"));
					String mPlaceLongitude = mCursor.getString(mCursor
							.getColumnIndex("longitude"));
					String mPlaceDescription = mCursor.getString(mCursor
							.getColumnIndex("description"));
					String mImage = mCursor.getString(mCursor
							.getColumnIndex("image"));
					String mName = mCursor.getString(mCursor
							.getColumnIndex("name"));
					String mEmail = mCursor.getString(mCursor
							.getColumnIndex("email"));
					String mPhone = mCursor.getString(mCursor
							.getColumnIndex("phone"));

					mSinglePlace = new VisitingPlace(mPlaceId,
							mPlaceDate, mPlaceTime, mPlaceLatitude,
							mPlaceLongitude, mPlaceDescription, mImage,mName,mEmail,mPhone);

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return mSinglePlace;
	}

	/*
	 * create a Diet chart of ICareProfile. Here the data of the database
	 * according to the given id is set to the Diet chart and return a Diet
	 * chart object.
	 */

	public VisitingPlace updateActivityData(long eActivityId) {
		this.open();
		VisitingPlace UpdateActivity;

		Cursor mUpdateCursor = mPlaceDatabase.query(SQLiteHelper.TABLE_PLACES,
				new String[] { SQLiteHelper.COL_PLACE_ID,
						SQLiteHelper.COL_PLACE_DATE,
						SQLiteHelper.COL_PLACE_TIME,
						SQLiteHelper.COL_PLACE_LATITUDE,
						SQLiteHelper.COL_PLACE_LONGITUDE,
						SQLiteHelper.COL_PLACE_DESCRIPTION,
						SQLiteHelper.COL_PLACE_IMAGE,
						SQLiteHelper.COL_PLACE_NAME,
						SQLiteHelper.COL_PLACE_EMAIL,
						SQLiteHelper.COL_PLACE_PHONE,

				}, SQLiteHelper.COL_PLACE_ID + "= '" + eActivityId + "' ",
				null, null, null, null);

		mUpdateCursor.moveToFirst();

		String mPlaceId = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("id"));
		String mPlaceDate = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("date"));
		String mPlaceTime = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("time"));
		String mActivityLatitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("latitude"));
		String mActivityLongitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("longitude"));
		String mActivityDescription = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("description"));
		String mImage = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("image"));
		String mName = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("name"));
		String mEmail = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("email"));
		String mPhone = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex("phone"));

		mUpdateCursor.close();
		UpdateActivity = new VisitingPlace(mPlaceId, mPlaceDate, mPlaceTime,
				mActivityLatitude, mActivityLongitude, mActivityDescription,
				mImage,mName,mEmail,mPhone);

		this.close();
		return UpdateActivity;
	}

	/*
	 * using cursor for display data from database.
	 */
	public List<VisitingPlace> allPlaces() {
		this.open();

		Cursor mCursor = mPlaceDatabase.query(SQLiteHelper.TABLE_PLACES,
				new String[] { SQLiteHelper.COL_PLACE_ID,
						SQLiteHelper.COL_PLACE_DATE,
						SQLiteHelper.COL_PLACE_TIME,
						SQLiteHelper.COL_PLACE_LATITUDE,
						SQLiteHelper.COL_PLACE_LONGITUDE,
						SQLiteHelper.COL_PLACE_DESCRIPTION,
						SQLiteHelper.COL_PLACE_IMAGE,
						SQLiteHelper.COL_PLACE_NAME,
						SQLiteHelper.COL_PLACE_EMAIL,
						SQLiteHelper.COL_PLACE_PHONE,

				}, null, null, null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String mPlaceId = mCursor.getString(mCursor
							.getColumnIndex("id"));
					String mPlaceDate = mCursor.getString(mCursor
							.getColumnIndex("date"));
					String mPlaceTime = mCursor.getString(mCursor
							.getColumnIndex("time"));
					String mPlaceLatitude = mCursor.getString(mCursor
							.getColumnIndex("latitude"));
					String mPlaceLongitude = mCursor.getString(mCursor
							.getColumnIndex("longitude"));
					String mPlaceDescription = mCursor.getString(mCursor
							.getColumnIndex("description"));
					String mImage = mCursor.getString(mCursor
							.getColumnIndex("image"));
					String mName = mCursor.getString(mCursor
							.getColumnIndex("name"));
					String mEmail = mCursor.getString(mCursor
							.getColumnIndex("email"));
					String mPhone = mCursor.getString(mCursor
							.getColumnIndex("phone"));

					mPlaces.add(new VisitingPlace(mPlaceId, mPlaceDate,
							mPlaceTime, mPlaceLatitude, mPlaceLongitude,
							mPlaceDescription, mImage,mName,mEmail,mPhone));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return mPlaces;
	}

}
