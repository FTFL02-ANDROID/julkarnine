package com.ftfl.myvisitingplaces;

import java.lang.reflect.Field;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.myvisitingplaces.adapter.Customadapter;
import com.ftfl.myvisitingplaces.helper.SQLiteHelper;
import com.ftfl.myvisitingplaces.helper.VisitingPlacesDataSource;
import com.ftfl.myvisitingplaces.model.VisitingPlace;
import com.ftfl.myvisitingplaces.util.GPSTracker;

public class VisitingPlacesListViewActivity extends Activity {

	SQLiteHelper mDBHelper;
	TextView mTvMyCurrentPositionLat;
	TextView mTvMyCurrentPositionLong;
	VisitingPlacesDataSource mPlacesDataSource;
	ListView mListView;
	TextView mId_tv = null;
	TextView mLatitude_tv = null;
	TextView mLongitude_tv = null;
	String mId = "";
	String mLatitude = "";
	String mLongitude = "";
	LocationManager lm;
	String provider;
	Location l;
	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visiting_places_list_view);
		getOverflowMenu();

		mTvMyCurrentPositionLat = (TextView) findViewById(R.id.tvMyCurrentPositionLat);
		mTvMyCurrentPositionLong = (TextView) findViewById(R.id.tvMyCurrentPositionLong);
		mDBHelper = new SQLiteHelper(this);
		mPlacesDataSource = new VisitingPlacesDataSource(this);
		List<VisitingPlace> places = mPlacesDataSource.allPlaces();
		Customadapter arrayAdapter = new Customadapter(this, places);
		mListView = (ListView) findViewById(R.id.lvPlacesList);
		mListView.setAdapter(arrayAdapter);

		// create class object
		gps = new GPSTracker(VisitingPlacesListViewActivity.this);
		// check if GPS enabled
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			// \n is for new line

			mTvMyCurrentPositionLat.setText("LAT: "+String.valueOf(latitude));
			mTvMyCurrentPositionLong.setText("LNG: "+String.valueOf(longitude));
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_Home) {
			Intent i = new Intent(getApplicationContext(),
					HomeScreenActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	private void getOverflowMenu() {

		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
