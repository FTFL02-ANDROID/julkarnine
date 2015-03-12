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
import com.ftfl.icare.model.Appointment;
import com.ftfl.icare.model.DoctorProfile;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CustomAppointmentAdapter extends ArrayAdapter<Appointment> {

	private static LayoutInflater mInflater = null;

	List<Appointment> data;
	String mName;
	String mDate;
	String mTime;
	String mPurpose;
	String mLocation;
	String mAlarm;

	public CustomAppointmentAdapter(Activity context, List<Appointment> eProfile) {
		super(context, R.layout.activity_appointment_list_item, eProfile);
		this.data = eProfile;

		/*********** Layout inflator to call external xml layout () ***********/
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView tvID;
		public TextView tvName;
		public TextView tvDate;
		public TextView tvTime;
		public TextView tvPurpose;
		public TextView tvLocation;
		public TextView tvAlarm;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater.inflate(R.layout.activity_appointment_list_item,
					null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tvID = (TextView) vi.findViewById(R.id.tvID);
			holder.tvName = (TextView) vi.findViewById(R.id.tvName);
			holder.tvDate = (TextView) vi.findViewById(R.id.tvDate);
			holder.tvTime = (TextView) vi.findViewById(R.id.tvTime);
			holder.tvPurpose = (TextView) vi.findViewById(R.id.tvPurpose);
			holder.tvLocation = (TextView) vi.findViewById(R.id.tvLocation);
			holder.tvAlarm = (TextView) vi.findViewById(R.id.tvAlarm);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (data.size() <= 0) {

		} else {

			Appointment mAppointment;
			mAppointment = data.get(position);

			holder.tvID.setText(mAppointment.getId().toString());
			holder.tvName.setText(mAppointment.getDrName().toString());
			holder.tvDate.setText(mAppointment.getDate().toString());
			holder.tvTime.setText(mAppointment.getTime().toString());
			holder.tvPurpose.setText(mAppointment.getPurpose().toString());
			holder.tvLocation.setText(mAppointment.getLocation().toString());
			holder.tvAlarm.setText(mAppointment.getAlarm().toString());

		}

		return vi;
	}
}
