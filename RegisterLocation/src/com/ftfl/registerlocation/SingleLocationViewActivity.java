package com.ftfl.registerlocation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.registerlocation.helper.LocationDataSource;
import com.ftfl.registerlocation.helper.SQLiteHelper;
import com.ftfl.registerlocation.model.LocationModel;

public class SingleLocationViewActivity extends Activity {

	SQLiteHelper mDBHelper;
	LocationDataSource mHospitalDataSource;
	ListView mListView;
	LocationModel mHospital;
	String mId;
	String mName;
	String mAddress;
	String mLatitude;
	String mLongitude;
	String mDescriptionStr;
	byte[] mImage;
	TextView mTvName;
	TextView mTvHospitalTitle;
	TextView mTvAddress;
	TextView mTvLatitude;
	TextView mTvLongitude;
	TextView mTvImage;
	TextView mDescription;
	ImageView mImgeView;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_hospital_view);
		

		mTvName = (TextView) findViewById(R.id.viewName);
		mTvHospitalTitle = (TextView) findViewById(R.id.tvHospitalTitle);
		mTvAddress = (TextView) findViewById(R.id.viewAddress);;
		mTvLatitude = (TextView) findViewById(R.id.viewLatitude);
		mTvLongitude = (TextView) findViewById(R.id.viewLongitude);
		mDescription = (TextView) findViewById(R.id.viewServiceDescription);
		mImgeView = (ImageView) findViewById(R.id.imageViewDisplay);
		
		Intent mActivityIntent = getIntent();
		 mId = mActivityIntent.getStringExtra("activityId");
		mDBHelper = new SQLiteHelper(this);
		mHospitalDataSource = new LocationDataSource(this);
		mHospital = mHospitalDataSource.singleHospital(mId);
		
		mName =mHospital.getHospitalName().toString() ;
		mAddress=mHospital.getHospitalAddress().toString() ;
		mLatitude=mHospital.getHospitalLatitude().toString() ;
		mLongitude=mHospital.getHospitalLongitude().toString() ;
		mImage = mHospital.getHospitalImage();
		mDescriptionStr=mHospital.getHospitalDescription().toString() ;
		
		mTvHospitalTitle.setText(mName);
		mTvName.setText(mName);
		mTvAddress.setText(mAddress);
		mTvLatitude.setText(mLatitude);
		mTvLongitude.setText(mLongitude);
		mDescription.setText(mDescriptionStr);
		mImgeView.setImageBitmap(getImage(mImage));
	

	}
	
	 // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        Log.e("src",src);
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        Log.e("Bitmap","returned");
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        Log.e("Exception",e.getMessage());
	        return null;
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
		switch (item.getItemId()) {
		case R.id.action_hospitalList:
			startActivity(new Intent(getApplicationContext(),
					LocationListViewActivity.class));
			return true;
		case R.id.action_insertInfo:
			startActivity(new Intent(getApplicationContext(),
					LocationListViewActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	

}
