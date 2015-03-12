package com.ftfl.icare.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.icare.model.Appointment;
import com.ftfl.icare.util.ICareConstants;

public class AppointmentDataSource {

	private SQLiteDatabase iCareDatabase;
	private ICareSQLiteHelper iCareDbHelper;
	List<Appointment> appointmentList = new ArrayList<Appointment>();

	public AppointmentDataSource(Context context) {
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

	public boolean insert(Appointment eAppointment) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_NAME, eAppointment.getDrName());

		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_DATE, eAppointment.getDate());
		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_TIME, eAppointment.getTime());
		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_PURPOSE,
				eAppointment.getPurpose());
		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_LOCATION,
				eAppointment.getLocation());

		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_ALARM, eAppointment.getAlarm());
		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_PROFILE_ID,
				eAppointment.getProfileId());

		long check = iCareDatabase.insert(ICareSQLiteHelper.TABLE_APPOINTMENT,
				null, cv);
		iCareDatabase.close();

		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by id
	public boolean updateData(int eAppointmentId, Appointment eAppointment) {
		this.open();
		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_NAME, eAppointment.getDrName());

		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_DATE, eAppointment.getDate());
		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_TIME, eAppointment.getTime());
		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_PURPOSE,
				eAppointment.getPurpose());
		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_LOCATION,
				eAppointment.getLocation());

		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_ALARM, eAppointment.getAlarm());
		cv.put(ICareSQLiteHelper.COL_APPOINTMENT_PROFILE_ID,
				eAppointment.getProfileId());

		int check = iCareDatabase.update(ICareSQLiteHelper.TABLE_APPOINTMENT,
				cv,
				ICareSQLiteHelper.COL_APPOINTMENT_ID + "=" + eAppointmentId,
				null);
		iCareDatabase.close();

		this.close();
		if (check == 0)
			return false;
		else
			return true;
	}

	// delete data form database.
	public boolean deleteData(int eAppointmentId) {
		this.open();
		try {
			iCareDatabase
					.delete(ICareSQLiteHelper.TABLE_APPOINTMENT,
							ICareSQLiteHelper.COL_APPOINTMENT_ID + "="
									+ eAppointmentId, null);
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
	public List<Appointment> appointmentList() {
		this.open();

		
		
		Cursor mCursor = iCareDatabase.query(
				ICareSQLiteHelper.TABLE_APPOINTMENT, null,
				ICareSQLiteHelper.COL_APPOINTMENT_PROFILE_ID + "="
						+ ICareConstants.SELECTED_PROFILE_ID, null, null, null,
				null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String id = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_ID));
					String name = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_NAME));
					String date = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_DATE));
					String time = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_TIME));
					String status = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_PURPOSE));
					String notes = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_LOCATION));

					String alarm = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_ALARM));

					String profileId = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_PROFILE_ID));

					appointmentList.add(new Appointment(id, name, date, time,
							status, notes, alarm, profileId));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return appointmentList;
	}

	/*
	 * create a profile of ICareProfile. Here the data of the database according
	 * to the given id is set to the profile and return a profile.
	 */
	public Appointment singleAppointmentData(int eAppointmentId) {
		this.open();
		Appointment singleAppointment;
		Cursor mCursor = iCareDatabase.query(
				ICareSQLiteHelper.TABLE_APPOINTMENT, null,
				ICareSQLiteHelper.COL_APPOINTMENT_ID + "=" + eAppointmentId,
				null, null, null, null);

		mCursor.moveToFirst();

		String id = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_ID));
		String name = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_NAME));
		String date = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_DATE));
		String time = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_TIME));
		String status = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_PURPOSE));
		String notes = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_LOCATION));

		String alarm = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_ALARM));

		String profileId = mCursor.getString(mCursor
				.getColumnIndex(ICareSQLiteHelper.COL_APPOINTMENT_PROFILE_ID));
		mCursor.close();

		singleAppointment = new Appointment(id, name, date, time, status,
				notes, alarm, profileId);
		this.close();
		return singleAppointment;
	}
}
