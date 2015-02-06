package com.ftfl.myvisitingplaces.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.myvisitingplaces.R;
import com.ftfl.myvisitingplaces.model.VisitingPlace;
import com.ftfl.myvisitingplaces.util.GPSTracker;

public class Customadapter extends ArrayAdapter<VisitingPlace> {

	private static LayoutInflater mInflater = null;

	List<VisitingPlace> mPlaces;
	public byte[] mImage;
	String latitudeString;
	String longitudeString;
	String provider;
	Location l;
	// GPSTracker class
	GPSTracker gps;

	public Customadapter(Activity context, List<VisitingPlace> ePlaces) {
		super(context, R.layout.activity_visiting_places_list, ePlaces);
		this.mPlaces = ePlaces;

		/*********** Layout inflator to call external xml layout () ***********/
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView mId;
		public TextView mDate;
		public TextView mTime;
		public TextView mLatitude;
		public TextView mLongitude;
		public TextView mDescription;
		public TextView mDistance;
		public ImageView mIvImage;

	}

	// convert from byte array to bitmap
	public static Bitmap getImage(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater
					.inflate(R.layout.activity_visiting_places_list, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.mId = (TextView) vi.findViewById(R.id.tvPlaceId);
			holder.mDate = (TextView) vi.findViewById(R.id.tvDate);
			holder.mTime = (TextView) vi.findViewById(R.id.tvTime);
			holder.mLatitude = (TextView) vi
					.findViewById(R.id.tvLatitude);
			holder.mLongitude = (TextView) vi
					.findViewById(R.id.tvLongitude);
			holder.mDescription = (TextView) vi
					.findViewById(R.id.tvDescription);
			holder.mDistance = (TextView) vi.findViewById(R.id.tvDistance);
			holder.mIvImage = (ImageView) vi.findViewById(R.id.ivImage);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		VisitingPlace place;
		place = mPlaces.get(position);

		holder.mId.setText(place.getPlaceId().toString());
		holder.mDate.setText("Date: " + place.getPlaceDate().toString());
		holder.mTime.setText("Time: " + place.getPlaceTime().toString());
		holder.mLatitude.setText("LAT: "+place.getPlaceLatitude().toString());
		holder.mLongitude.setText("LNG: "+place.getPlaceLongitude().toString());
		holder.mDescription.setText("Details :"+place.getPlaceDescription().toString());
		holder.mDistance = (TextView) vi.findViewById(R.id.tvDistance);

		// create class object
		gps = new GPSTracker(getContext());
		// check if GPS enabled
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			latitudeString = String.valueOf(latitude);
			longitudeString = String.valueOf(longitude);
			// \n is for new line

		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

		Location l1 = new Location("One");
		l1.setLatitude(Double.parseDouble(latitudeString));
		l1.setLongitude(Double.parseDouble(longitudeString));

		Location l2 = new Location("Two");
		l2.setLatitude(Double.parseDouble(place.getPlaceLatitude()
				.toString()));
		l2.setLongitude(Double.parseDouble(place.getPlaceLongitude()
				.toString()));

		float distance_bw_one_and_two = l1.distanceTo(l2);

		holder.mDistance.setText(String.valueOf(Math.round(distance_bw_one_and_two/1000) + " km"));

		mImage = place.getPlaceImage();
		holder.mIvImage.setImageBitmap(getImage(mImage));
		;

		return vi;
	}
}
