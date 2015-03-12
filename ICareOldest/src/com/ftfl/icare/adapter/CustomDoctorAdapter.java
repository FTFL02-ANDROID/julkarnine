package com.ftfl.icare.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.model.DoctorProfile;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CustomDoctorAdapter extends ArrayAdapter<DoctorProfile> {

	private static LayoutInflater mInflater = null;

	List<DoctorProfile> data;
	String mName;
	String mSpecialization;
	String mPhone;
	String mEnail;
	String mLocation;


	public CustomDoctorAdapter(Activity context, List<DoctorProfile> eProfile) {
		super(context, R.layout.activity_doctor_list_item,
				eProfile);
		this.data = eProfile;

		/*********** Layout inflator to call external xml layout () ***********/
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView tvID;
		public TextView tvName;
		public TextView tvSpecializtion;
		public TextView tvPhone;
		public TextView tvEmail;
		public TextView tvLocation;
		

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater.inflate(
					R.layout.activity_doctor_list_item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tvID = (TextView) vi.findViewById(R.id.tvID);
			holder.tvName = (TextView) vi.findViewById(R.id.tvName);
			holder.tvSpecializtion = (TextView) vi
					.findViewById(R.id.tvSpecializtion);
			holder.tvPhone = (TextView) vi.findViewById(R.id.tvPhone);
			holder.tvEmail = (TextView) vi.findViewById(R.id.tvEmail);
			holder.tvLocation = (TextView) vi.findViewById(R.id.tvLocation);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (data.size() <= 0) {

		} else {

			DoctorProfile mDoctorProfile;
			mDoctorProfile = data.get(position);

			holder.tvID.setText(mDoctorProfile.getId().toString());
			holder.tvName.setText( mDoctorProfile.getName().toString());
			holder.tvSpecializtion.setText(mDoctorProfile.getSpecialization().toString());
			holder.tvPhone.setText( mDoctorProfile.getPhone().toString());
			holder.tvEmail.setText(mDoctorProfile.getEmail().toString());
			holder.tvLocation.setText(mDoctorProfile.getAddress().toString());
			

		
		}

		return vi;
	}
}
