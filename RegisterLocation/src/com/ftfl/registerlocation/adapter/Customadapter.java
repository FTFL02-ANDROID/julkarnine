package com.ftfl.registerlocation.adapter;

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

import com.ftfl.registerlocation.GPSTracker;
import com.ftfl.registerlocation.InsertLocationInfoActivity;
import com.ftfl.registerlocation.R;
import com.ftfl.registerlocation.model.LocationModel;

public class Customadapter extends ArrayAdapter<LocationModel> {

	private static LayoutInflater mInflater = null;

	List<LocationModel> mHospitals;
	public byte[] mImage;
	String yourDoubleString;
    String yourlongitudeString;
	String provider;
	Location l;
	// GPSTracker class
    GPSTracker gps; 


	public Customadapter(Activity context, List<LocationModel> eHospitals) {
		super(context, R.layout.activity_hospital_list, eHospitals);
		this.mHospitals = eHospitals;
		
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
			vi = mInflater.inflate(R.layout.activity_hospital_list, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.mId = (TextView) vi.findViewById(R.id.tvHospitalId);
			holder.mDate = (TextView) vi.findViewById(R.id.tvHospitalName);
			holder.mTime = (TextView) vi.findViewById(R.id.tvHospitalAddress);
			holder.mLatitude = (TextView) vi.findViewById(R.id.tvHospitalLatitude);
			holder.mLongitude = (TextView) vi.findViewById(R.id.tvHospitalLongitude);
			holder.mDescription = (TextView) vi.findViewById(R.id.tvDescription);
			holder.mDistance = (TextView) vi.findViewById(R.id.tvDistance);
			holder.mIvImage = (ImageView) vi.findViewById(R.id.ivImage);
			
			

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		LocationModel hospital;
		hospital = mHospitals.get(position);

		holder.mId.setText(hospital.getHospitalId().toString());
		holder.mDate.setText("Date: "+hospital.getHospitalName().toString());
		holder.mTime.setText("Time: " +hospital.getHospitalAddress().toString());
		holder.mLatitude.setText(hospital.getHospitalLatitude().toString());
		holder.mLongitude.setText(hospital.getHospitalLongitude().toString());
		holder.mDescription.setText("Description: "+hospital.getHospitalDescription().toString());
		holder.mDistance = (TextView) vi.findViewById(R.id.tvDistance);
		


		 // create class object
       gps = new GPSTracker(getContext());
       // check if GPS enabled       
       if(gps.canGetLocation()){                  
           double latitude = gps.getLatitude();
           double longitude = gps.getLongitude();   
           
           yourDoubleString = String.valueOf(latitude);
           yourlongitudeString = String.valueOf(longitude);
           // \n is for new line
         
       }else{
           // can't get location
           // GPS or Network is not enabled
           // Ask user to enable GPS/network in settings
           gps.showSettingsAlert();
       }        
		
		Location l1=new Location("One");
		l1.setLatitude(Double.parseDouble(yourDoubleString));
		l1.setLongitude(Double.parseDouble(yourlongitudeString));
		 
		     
		Location l2=new Location("Two");
		l2.setLatitude(Double.parseDouble(hospital.getHospitalLatitude().toString()));
		l2.setLongitude(Double.parseDouble(hospital.getHospitalLongitude().toString()));
		     
		float distance_bw_one_and_two=l1.distanceTo(l2);
		
		holder.mDistance.setText("Distance: "+String.valueOf(distance_bw_one_and_two));
		
		mImage = hospital.getHospitalImage();
		holder.mIvImage.setImageBitmap(getImage(mImage));;

		
		
		
		return vi;
	}
}
