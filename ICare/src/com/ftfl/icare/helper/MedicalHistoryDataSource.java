package com.ftfl.icare.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;

import com.ftfl.icare.model.ICareProfile;
import com.ftfl.icare.model.MedicalHistory;
import com.ftfl.icare.util.ICareConstants;

public class MedicalHistoryDataSource {

	private SQLiteDatabase mICareDatabase;
	private ICareSQLiteHelper mICareDbHelper;
	List<MedicalHistory> mMedicalHistoryList = new ArrayList<MedicalHistory>();
	Context mContext;
	ICareProfileDataSource mProfileDataSource;
	ICareProfile mIProfile;

	ListView mLvProfileList;
	List<ICareProfile> mICareProfilesList;

	public MedicalHistoryDataSource(Context context) {
		mICareDbHelper = new ICareSQLiteHelper(context);
		
		this.mContext = context;
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

	public boolean insert(MedicalHistory eMedicalHistory) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_DATE,
				eMedicalHistory.getDate());

		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_DOCTOR_NAME,
				eMedicalHistory.getDoctorName());
		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PURPOSE,
				eMedicalHistory.getPurpose());
		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_SUGGESTION,
				eMedicalHistory.getSuggestion());
		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PRESCRIPTION,
				eMedicalHistory.getPrescription());
		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PROFILE_ID,
				eMedicalHistory.getProfileId());

		long check = mICareDatabase.insert(
				ICareSQLiteHelper.TABLE_MEDICAL_HISTORY, null, cv);
		mICareDatabase.close();

		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by id
	public boolean updateData(int eMedicalHistoryId,
			MedicalHistory eMedicalHistory) {
		this.open();
		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_DATE,
				eMedicalHistory.getDate());

		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_DOCTOR_NAME,
				eMedicalHistory.getDoctorName());
		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PURPOSE,
				eMedicalHistory.getPurpose());
		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_SUGGESTION,
				eMedicalHistory.getSuggestion());
		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PRESCRIPTION,
				eMedicalHistory.getPrescription());
		cv.put(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PROFILE_ID,
				eMedicalHistory.getProfileId());

		int check = mICareDatabase.update(
				ICareSQLiteHelper.TABLE_MEDICAL_HISTORY, cv,
				ICareSQLiteHelper.COL_MEDICAL_HISTORY_ID + "="
						+ eMedicalHistoryId, null);
		mICareDatabase.close();

		this.close();
		if (check == 0)
			return false;
		else
			return true;
	}

	// delete data form database.
	public boolean deleteData(int eMedicalHistoryId) {
		this.open();
		try {
			mICareDatabase.delete(ICareSQLiteHelper.TABLE_MEDICAL_HISTORY,
					ICareSQLiteHelper.COL_MEDICAL_HISTORY_ID + "="
							+ eMedicalHistoryId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data delete problem");
			return false;
		}
		this.close();
		return true;
	}
	
	public void share(String position) {

		SharedPreferences preferences = mContext.getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("profile_id", position);
		editor.apply();
		
		ICareConstants.SELECTED_PROFILE_ID = Integer.parseInt(position);

	}

	/*
	 * using cursor for display data from database.
	 */
	public List<MedicalHistory> medicalHistoryList() {
		this.open();

		mProfileDataSource = new ICareProfileDataSource(mContext);
		mICareProfilesList = mProfileDataSource.iCareProfilesList();
		
		if(mICareProfilesList.size() == 1){
			
			
			share(mICareProfilesList.get(0).getID());
			
		}
		
		Cursor mCursor = mICareDatabase.query(
				ICareSQLiteHelper.TABLE_MEDICAL_HISTORY, null,
				ICareSQLiteHelper.COL_MEDICAL_HISTORY_PROFILE_ID + "="
						+ ICareConstants.SELECTED_PROFILE_ID, null, null, null,
				null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String id = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_ID));
					String date = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_DATE));
					String doctorName = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_DOCTOR_NAME));
					String purpose = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PURPOSE));
					String suggestion = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_SUGGESTION));
					String prescription = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PRESCRIPTION));
					String profileId = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PROFILE_ID));

					mMedicalHistoryList.add(new MedicalHistory(id, date,
							doctorName, purpose, suggestion, prescription,
							profileId));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return mMedicalHistoryList;
	}

	/*
	 * create a profile of ICareProfile. Here the data of the database according
	 * to the given id is set to the profile and return a profile.
	 */
	public MedicalHistory singleMedicalHistoryData(int eMedicalHistoryId) {
		this.open();
		MedicalHistory singleMedicalHistory;
		Cursor mCursor = mICareDatabase.query(
				ICareSQLiteHelper.TABLE_MEDICAL_HISTORY, null,
				ICareSQLiteHelper.COL_MEDICAL_HISTORY_ID + "="
						+ eMedicalHistoryId, null, null, null, null);

		mCursor.moveToFirst();

		String id = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_ID));
		String date = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_DATE));
		String doctorName = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_DOCTOR_NAME));
		String purpose = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PURPOSE));
		String suggestion = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_SUGGESTION));
		String prescription = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PRESCRIPTION));
		String profileId = mCursor
				.getString(mCursor
						.getColumnIndex(ICareSQLiteHelper.COL_MEDICAL_HISTORY_PROFILE_ID));
		mCursor.close();

		singleMedicalHistory = new MedicalHistory(id, date, doctorName,
				purpose, suggestion, prescription, profileId);
		this.close();
		return singleMedicalHistory;
	}

}
