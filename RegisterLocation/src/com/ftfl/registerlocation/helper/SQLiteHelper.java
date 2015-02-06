package com.ftfl.registerlocation.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

	  
	// Database Name
	public static final String DATABASE_NAME = "registerlocations.db";
	private static final int DATABASE_VERSION = 3;

	// Table HOSPITAL
	public static final String TABLE_HOSPITAL = "location";
	public static final String COL_HOSPITAL_ID = "location_id";
	public static final String COL_HOSPITAL_NAME = "date";
	public static final String COL_HOSPITAL_ADDRESS = "time";
	public static final String COL_HOSPITAL_LATITUDE = "latitude";
	public static final String COL_HOSPITAL_LONGITUDE = "longitude";
	public static final String COL_HOSPITAL_DESCRIPTION = "description";
	public static final String COL_HOSPITAL_IMAGE = "image";

	// Database creation sql statement
	private static final String TABLE_CREATE_HOSPITAL = "create table "
			+ TABLE_HOSPITAL + "(" + COL_HOSPITAL_ID
			+ " integer primary key autoincrement, " + COL_HOSPITAL_NAME
			+ " text not null," + COL_HOSPITAL_ADDRESS + " text not null,"
			+ COL_HOSPITAL_LATITUDE + " text not null,"
			+ COL_HOSPITAL_LONGITUDE + " text not null," + COL_HOSPITAL_DESCRIPTION
			+ " text not null ," + COL_HOSPITAL_IMAGE
			+ " BLOB not null);";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		database.execSQL(TABLE_CREATE_HOSPITAL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOSPITAL);
		onCreate(db);
	}

}
