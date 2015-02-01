package com.ftfl.toptenhospitalindhaka.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftfl.toptenhospitalindhaka.R;
import com.ftfl.toptenhospitalindhaka.model.Hospital;

public class Customadapter extends ArrayAdapter<Hospital> {

	private static LayoutInflater mInflater = null;

	List<Hospital> mHospitals;

	public Customadapter(Activity context, List<Hospital> eHospitals) {
		super(context, R.layout.activity_hospital_list, eHospitals);
		this.mHospitals = eHospitals;

		/*********** Layout inflator to call external xml layout () ***********/
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView mId;
		public TextView mName;
		public TextView mAddress;
		public TextView mLatitude;
		public TextView mLongitude;
		public TextView mDescription;
	

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
			holder.mName = (TextView) vi.findViewById(R.id.tvHospitalName);
			holder.mAddress = (TextView) vi.findViewById(R.id.tvHospitalAddress);
			holder.mLatitude = (TextView) vi.findViewById(R.id.tvHospitalLatitude);
			holder.mLongitude = (TextView) vi.findViewById(R.id.tvHospitalLongitude);
			
			

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		Hospital hospital;
		hospital = mHospitals.get(position);

		holder.mId.setText(hospital.getHospitalId().toString());
		holder.mName.setText("Name: "+hospital.getHospitalName().toString());
		holder.mAddress.setText("Address: " +hospital.getHospitalAddress().toString());
		holder.mLatitude.setText(""+hospital.getHospitalLatitude().toString());
		holder.mLongitude.setText(hospital.getHospitalLongitude().toString());

		
		
		
		return vi;
	}
}
