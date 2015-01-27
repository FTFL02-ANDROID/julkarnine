package com.ftfl.googlemap.Adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.googlemap.R;
import com.ftfl.googlemap.model.Hospital;

public class Customadapter extends ArrayAdapter<Hospital> {

	private static LayoutInflater inflater = null;

	private final Activity context;
	List<Hospital> hospitals;

	public Customadapter(Activity context, List<Hospital> hospitals) {
		super(context, R.layout.activity_hospital_list, hospitals);
		this.context = context;
		this.hospitals = hospitals;

		/*********** Layout inflator to call external xml layout () ***********/
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView id;
		public TextView name;
		public TextView address;
		public TextView latitude;
		public TextView longitude;
		public TextView description;
	

	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.activity_hospital_list, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.id = (TextView) vi.findViewById(R.id.tvHospitalId);
			holder.name = (TextView) vi.findViewById(R.id.tvHospitalName);
			holder.address = (TextView) vi.findViewById(R.id.tvHospitalAddress);
			holder.latitude = (TextView) vi.findViewById(R.id.tvHospitalLatitude);
			holder.longitude = (TextView) vi.findViewById(R.id.tvHospitalLongitude);
			holder.description = (TextView) vi.findViewById(R.id.tvHospitalDescription);
			

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		Hospital hospital;
		hospital = hospitals.get(position);

		holder.id.setText(hospital.getHospitalId().toString());
		holder.name.setText("Name: "+hospital.getHospitalName().toString());
		holder.address.setText("Address: " +hospital.getHospitalAddress().toString());
		holder.latitude.setText(""+hospital.getHospitalLatitude().toString());
		holder.longitude.setText(hospital.getHospitalLongitude().toString());
		holder.description.setText("Description: "+hospital.getHospitalDescription().toString());
		
		
		
		return vi;
	}
}
