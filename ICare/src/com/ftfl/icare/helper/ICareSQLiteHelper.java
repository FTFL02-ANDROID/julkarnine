package com.ftfl.icare.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ICareSQLiteHelper extends SQLiteOpenHelper {
	// ICare Profile Table
	public static final String TABLE_I_CARE_PROFILE = "icareprofiles";
	public static final String COL_ICARE_PROFILE_ID = "id";
	public static final String COL_ICARE_PROFILE_NAME = "name";
	public static final String COL_ICARE_PROFILE_GENDER = "gender";
	public static final String COL_ICARE_PROFILE_DATE_OF_BIRTH = "dateofbirth";
	public static final String COL_ICARE_PROFILE_HEIGHT = "height";
	public static final String COL_ICARE_PROFILE_WEIGHT = "weight";
	public static final String COL_ICARE_PROFILE_BLOOD_GROUP = "blood_group";
	public static final String COL_ICARE_PROFILE_IMAGES = "images";

	// ICare Activity Chart Table
	public static final String ICARE_DIET_CHART = "icaredietchart";
	public static final String COL_ICARE_DIET_ID = "diet_id";
	public static final String COL_ICARE_DIET_DATE = "date";
	public static final String COL_ICARE_DIET_TIME = "time";
	public static final String COL_ICARE_DIET_FOOD_MENU = "food_menu";
	public static final String COL_ICARE_DIET_EVENT_NAME = "event_name";
	public static final String COL_ICARE_DIET_ALARM = "set_alarm";

	// ICare Doctor Profile Table
	public static final String TABLE_DOCTOR_PROFILE = "icare_doctor_profile";
	public static final String COL_DOCTOR_ID = "doctor_id";
	public static final String COL_DOCTOR_NAME = "doctor_name";
	public static final String COL_DOCTOR_SPECIALIZATION = "doctor_specialization";
	public static final String COL_DOCTOR_PHONE = "doctor_phone";
	public static final String COL_DOCTOR_EMAIL = "doctor_email";
	public static final String COL_DOCTOR_ADDRESS = "doctor_address";
	public static final String COL_DOCTOR_PROFILE_ID = "doctor_profile_id";

	// ICare important note Table
	public static final String TABLE_IMPORTANT_NOTE = "icareimportantnotes";
	public static final String COL_NOTE_ID = "note_id";
	public static final String COL_NOTE_TITLE = "note_title";
	public static final String COL_NOTE_SUBJECT = "note_subject";
	public static final String COL_DESCRIPTION = "note_description";
	public static final String COL_NOTE_PROFILE_ID = "note_profile_id";

	// ICare Vaccine Table
	public static final String TABLE_VACCINE = "icare_vaccine";
	public static final String COL_VACCINE_ID = "vaccine_id";
	public static final String COL_VACCINE_NAME = "vaccine_name";
	public static final String COL_VACCINE_DATE = "vaccine_date";
	public static final String COL_VACCINE_TIME = "vaccine_time";
	public static final String COL_VACCINE_STATUS = "vaccine_status";
	public static final String COL_VACCINE_NOTES = "vaccine_notes";
	public static final String COL_VACCINE_PROFILE_ID = "vaccine_profile_id";

	private static final String DATABASE_NAME = "iCare.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_PROFILE = "create table "
			+ TABLE_I_CARE_PROFILE + "( " + COL_ICARE_PROFILE_ID
			+ " integer primary key autoincrement, " + " "
			+ COL_ICARE_PROFILE_NAME + " text not null," + " "
			+ COL_ICARE_PROFILE_GENDER + " text not null," + " "
			+ COL_ICARE_PROFILE_BLOOD_GROUP + " text not null," + " "
			+ COL_ICARE_PROFILE_WEIGHT + " text not null," + " "
			+ COL_ICARE_PROFILE_HEIGHT + " text not null," + " "
			+ COL_ICARE_PROFILE_DATE_OF_BIRTH + " text not null," + " "
			+ COL_ICARE_PROFILE_IMAGES + " text not null);";

	private static final String DATABASE_CREATE_DACTIVITY = "create table "
			+ ICARE_DIET_CHART + "(" + COL_ICARE_DIET_ID
			+ " integer primary key autoincrement, " + COL_ICARE_DIET_DATE
			+ " text not null," + COL_ICARE_DIET_TIME + " text not null,"
			+ COL_ICARE_DIET_FOOD_MENU + " text not null,"
			+ COL_ICARE_DIET_EVENT_NAME + " text not null,"
			+ COL_ICARE_DIET_ALARM + " text not null);";

	// Doctor Profile sql command

	private static final String DATABASE_CREATE_DOCTOR_PROFILE = "create table "
			+ TABLE_DOCTOR_PROFILE
			+ "("
			+ COL_DOCTOR_ID
			+ " integer primary key autoincrement, "
			+ COL_DOCTOR_NAME
			+ " text not null,"
			+ COL_DOCTOR_SPECIALIZATION
			+ " text not null,"
			+ COL_DOCTOR_PHONE
			+ " text not null,"
			+ COL_DOCTOR_EMAIL
			+ " text not null,"
			+ COL_DOCTOR_ADDRESS
			+ " text not null,"
			+ COL_DOCTOR_PROFILE_ID + " text not null);";

	// Doctor note SQL command

	private static final String DATABASE_CREATE_NOTE = "create table "
			+ TABLE_IMPORTANT_NOTE + "(" + COL_NOTE_ID
			+ " integer primary key autoincrement, " + COL_NOTE_TITLE
			+ " text not null," + COL_NOTE_SUBJECT + " text not null,"
			+ COL_DESCRIPTION + " text not null," + COL_NOTE_PROFILE_ID
			+ " text not null);";

	// Doctor Vaccine SQL command

	private static final String DATABASE_CREATE_VACCINE = "create table "
			+ TABLE_VACCINE + "(" + COL_VACCINE_ID
			+ " integer primary key autoincrement, " + COL_VACCINE_NAME
			+ " text not null," + COL_VACCINE_DATE + " text not null,"
			+ COL_VACCINE_TIME + " text not null," + COL_VACCINE_STATUS
			+ " text not null," + COL_VACCINE_NOTES + " text not null,"
			+ COL_VACCINE_PROFILE_ID + " text not null);";

	public ICareSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_PROFILE);
		database.execSQL(DATABASE_CREATE_DACTIVITY);
		database.execSQL(DATABASE_CREATE_DOCTOR_PROFILE);
		database.execSQL(DATABASE_CREATE_NOTE);
		database.execSQL(DATABASE_CREATE_VACCINE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ICareSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_I_CARE_PROFILE);
		db.execSQL("DROP TABLE IF EXISTS " + ICARE_DIET_CHART);
		onCreate(db);
	}

}
