package com.ftfl.myvisitingplaces;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.myvisitingplaces.adapter.Customadapter;
import com.ftfl.myvisitingplaces.helper.VisitingPlacesDataSource;
import com.ftfl.myvisitingplaces.model.VisitingPlace;
import com.ftfl.myvisitingplaces.util.GPSTracker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class VisitingPlacesListViewActivity extends Activity {

	TextView mTvMyCurrentPositionLat;
	TextView mTvMyCurrentPositionLong;
	VisitingPlacesDataSource mPlacesDataSource;
	Button mBtnHomeNav;
	ListView mListView;
	TextView mId_tv = null;
	TextView mLatitude_tv = null;
	TextView mLongitude_tv = null;
	TextView mCount = null;
	String mId = "";
	String mLatitude = "";
	String mLongitude = "";
	LocationManager lm;
	String provider;
	Location l;
	GPSTracker gps;
	int listCount;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visiting_places_list_view);

		

		// Create global configuration and initialize ImageLoader with this
		// config
		// Create default options which will be used for every
		// displayImage(...) call if no options will be passed to this method
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();
		ImageLoader.getInstance().init(config);

		mTvMyCurrentPositionLat = (TextView) findViewById(R.id.tvMyCurrentPositionLat);
		mTvMyCurrentPositionLong = (TextView) findViewById(R.id.tvMyCurrentPositionLong);
		mCount = (TextView) findViewById(R.id.tvCount);
		mPlacesDataSource = new VisitingPlacesDataSource(this);
		List<VisitingPlace> places = mPlacesDataSource.allPlaces();
		Customadapter arrayAdapter = new Customadapter(this, places);
		mListView = (ListView) findViewById(R.id.lvPlacesList);
		mBtnHomeNav = (Button) findViewById(R.id.btnHomeNav);
		mListView.setAdapter(arrayAdapter);

		listCount = places.size();
		mCount.setText(String.valueOf(listCount));
		// create class object
		gps = new GPSTracker(VisitingPlacesListViewActivity.this);
		// check if GPS enabled
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			// \n is for new line

			mTvMyCurrentPositionLat.setText("LAT: " + String.valueOf(latitude));
			mTvMyCurrentPositionLong.setText("LNG: "
					+ String.valueOf(longitude));
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mId_tv = (TextView) view.findViewById(R.id.tvPlaceId);
				mId = mId_tv.getText().toString();

				Intent mEditIntent = new Intent(getApplicationContext(),
						PlaceViewActivity.class);

				mEditIntent.putExtra("Id", mId);
				startActivity(mEditIntent);
				finish();

			}
		});

		mBtnHomeNav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent mEditIntent = new Intent(getApplicationContext(),
						HomeScreenActivity.class);
				startActivity(mEditIntent);
				finish();

			}
		});
	}



}
