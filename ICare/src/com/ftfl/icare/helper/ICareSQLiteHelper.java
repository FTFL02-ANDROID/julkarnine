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
	public static final String COL_DIET_PROFILE_ID = "diet_profile_id";

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
	public static final String COL_VACCINE_ALARM = "set_alarm";
	public static final String COL_VACCINE_PROFILE_ID = "vaccine_profile_id";

	// ICare Vaccine Table
	public static final String TABLE_APPOINTMENT = "icare_appointment";
	public static final String COL_APPOINTMENT_ID = "appointment_id";
	public static final String COL_APPOINTMENT_NAME = "appointment_name";
	public static final String COL_APPOINTMENT_DATE = "appointment_date";
	public static final String COL_APPOINTMENT_TIME = "appointment_time";
	public static final String COL_APPOINTMENT_PURPOSE = "appointment_purpose";
	public static final String COL_APPOINTMENT_LOCATION = "appointment_location";
	public static final String COL_APPOINTMENT_ALARM = "set_alarm";
	public static final String COL_APPOINTMENT_PROFILE_ID = "appointment_profile_id";

	// ICare Medical History Table
	public static final String TABLE_MEDICAL_HISTORY = "icare_medical_history";
	public static final String COL_MEDICAL_HISTORY_ID = "medical_history_id";
	public static final String COL_MEDICAL_HISTORY_DATE = "medical_history_date";
	public static final String COL_MEDICAL_HISTORY_DOCTOR_NAME = "medical_history_doctor_name";
	public static final String COL_MEDICAL_HISTORY_PURPOSE = "medical_history_purpose";
	public static final String COL_MEDICAL_HISTORY_SUGGESTION = "medical_history_suggestion";
	public static final String COL_MEDICAL_HISTORY_PRESCRIPTION = "medical_history_prescription";
	public static final String COL_MEDICAL_HISTORY_PROFILE_ID = "medical_history_profile_id";

	// ICare MEDICATION Table
	public static final String TABLE_MEDICATION = "icare_medication";
	public static final String COL_MEDICATION_ID = "medication_id";
	public static final String COL_MEDICATION_NAME = "medication_name";
	public static final String COL_MEDICATION_DATE = "medication_date";
	public static final String COL_MEDICATION_TIME = "medication_time";
	public static final String COL_MEDICATION_PURPOSE = "medication_purpose";
	public static final String COL_MEDICATION_ALARM = "set_alarm";
	public static final String COL_MEDICATION_PROFILE_ID = "medication_profile_id";

	// ICare CARECENTER Table
	public static final String TABLE_CARECENTER = "icare_carecenter";
	public static final String COL_CARECENTER_ID = "carecenter_id";
	public static final String COL_CARECENTER_NAME = "carecenter_name";
	public static final String COL_CARECENTER_LOCATION = "carecenter_location";
	public static final String COL_CARECENTER_IMAGE = "carecenter_image";
	public static final String COL_CARECENTER_PROFILE_ID = "carecenter_profile_id";

	// Doctor Vaccine SQL command

	private static final String DATABASE_CREATE_APPOINTMENT = "create table "
			+ TABLE_APPOINTMENT + "(" + COL_APPOINTMENT_ID
			+ " integer primary key autoincrement, " + COL_APPOINTMENT_NAME
			+ " text not null," + COL_APPOINTMENT_DATE + " text not null,"
			+ COL_APPOINTMENT_TIME + " text not null,"
			+ COL_APPOINTMENT_PURPOSE + " text not null,"
			+ COL_APPOINTMENT_LOCATION + " text not null,"
			+ COL_APPOINTMENT_ALARM + " text  null,"
			+ COL_APPOINTMENT_PROFILE_ID + " text not null);";

	private static final String DATABASE_CREATE_MEDICATION = "create table "
			+ TABLE_MEDICATION + "(" + COL_MEDICATION_ID
			+ " integer primary key autoincrement, " + COL_MEDICATION_NAME
			+ " text not null," + COL_MEDICATION_DATE + " text not null,"
			+ COL_MEDICATION_TIME + " text not null," + COL_MEDICATION_PURPOSE
			+ " text not null," + COL_MEDICATION_ALARM + " text  null,"
			+ COL_MEDICATION_PROFILE_ID + " text not null);";

	private static final String DATABASE_CREATE_CARECENTER = "create table "
			+ TABLE_CARECENTER + "(" + COL_CARECENTER_ID
			+ " integer primary key autoincrement, " + COL_CARECENTER_NAME 
			+ " text not null," + COL_CARECENTER_LOCATION
			+ " text not null," + COL_CARECENTER_IMAGE + " text not null,"
			+ COL_CARECENTER_PROFILE_ID + " text not null);";

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
			+ COL_ICARE_DIET_EVENT_NAME + " text not null," + COL_DIET_PROFILE_ID + " text not null,"
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
			+ COL_VACCINE_ALARM + " text  null," + COL_VACCINE_PROFILE_ID
			+ " text not null);";

	private static final String DATABASE_CREATE_MEDICAL_HISTORY = "create table "
			+ TABLE_MEDICAL_HISTORY
			+ "("
			+ COL_MEDICAL_HISTORY_ID
			+ " integer primary key autoincrement, "
			+ COL_MEDICAL_HISTORY_DATE
			+ " text not null,"
			+ COL_MEDICAL_HISTORY_DOCTOR_NAME
			+ " text not null,"
			+ COL_MEDICAL_HISTORY_PURPOSE
			+ " text not null,"
			+ COL_MEDICAL_HISTORY_SUGGESTION
			+ " text not null,"
			+ COL_MEDICAL_HISTORY_PRESCRIPTION
			+ " text not null,"
			+ COL_MEDICAL_HISTORY_PROFILE_ID
			+ " text not null);";

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
		database.execSQL(DATABASE_CREATE_MEDICAL_HISTORY);
		database.execSQL(DATABASE_CREATE_MEDICATION);
		database.execSQL(DATABASE_CREATE_APPOINTMENT);
		database.execSQL(DATABASE_CREATE_CARECENTER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ICareSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_I_CARE_PROFILE);
		db.execSQL("DROP TABLE IF EXISTS " + ICARE_DIET_CHART);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR_PROFILE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMPORTANT_NOTE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAL_HISTORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICATION);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARECENTER);

		onCreate(db);
	}

}
