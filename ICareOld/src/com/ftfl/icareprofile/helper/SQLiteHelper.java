package com.ftfl.icareprofile.helper;

import java.util.ArrayList;

import com.ftfl.icareprofile.model.DietChart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	
		//Database Name
	    public static final String DATABASE_NAME = "ICareProfile.db";
	    private static final int DATABASE_VERSION = 1;
	    
	    //Table Profile 
		public static final String TABLE_PROFILE = "profiles";
		public static final String COL_PROFILE_ID = "id";
		public static final String COL_PROFILE_NAME = "name";
		public static final String COL_PROFILE_AGE = "age";
		public static final String COL_PROFILE_BLOOD_GROUP = "blood_group";
		public static final String COL_PROFILE_WEIGHT = "weight";
		public static final String COL_PROFILE_HEIGHT = "height";
		public static final String COL_PROFILE_DATE_OF_BIRTH = "dateofbirth";
		public static final String COL_PROFILE_SPECIAL_NOTES = "special_notes";
		
		// Table Diet
		public static final String TABLE_DIET = "diet";
		public static final String COL_DIET_ID = "diet_id";
		public static final String COL_DIET_DATE = "date";
		public static final String COL_DIET_TIME = "time";
		public static final String COL_DIET_FOOD_MENU = "food_menu";
		public static final String COL_DIET_EVENT_NAME = "event_name";
		public static final String COL_DIET_ALARM = "set_alarm";
		public static final String COL_DIET_PROFILE_ID = "profile_Id";
		
		// Database creation sql statement
		private static final String TABLE_CREATE_PROFILE = "create table "
				+ TABLE_PROFILE + "( " + COL_PROFILE_ID
				+ " integer primary key autoincrement, " + " "
				+ COL_PROFILE_NAME + " text not null," + " "
				+ COL_PROFILE_AGE + " text not null," + " "
				+ COL_PROFILE_BLOOD_GROUP + " text not null," + " "
				+ COL_PROFILE_WEIGHT + " text not null," + " "
				+ COL_PROFILE_HEIGHT + " text not null," + " "
				+ COL_PROFILE_DATE_OF_BIRTH + " text not null," + " "
				+ COL_PROFILE_SPECIAL_NOTES + " text not null);";

		private static final String TABLE_CREATE_DIET = "create table "
				+ TABLE_DIET + "(" + COL_DIET_ID
				+ " integer primary key autoincrement, "
				+ COL_DIET_DATE + " text not null,"
				+ COL_DIET_TIME + " text not null,"
				+ COL_DIET_FOOD_MENU + " text not null,"
				+ COL_DIET_EVENT_NAME + " text not null,"
				+ COL_DIET_ALARM + " text not null,"
				+ COL_DIET_PROFILE_ID+ " text not null);";

		
	
	
	
	
	
	
	
	

		public SQLiteHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL(TABLE_CREATE_PROFILE);
			database.execSQL(TABLE_CREATE_DIET);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(SQLiteHelper.class.getName(),
					"Upgrading database from version " + oldVersion + " to "
							+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIET);
			onCreate(db);
		}

		

	

}
