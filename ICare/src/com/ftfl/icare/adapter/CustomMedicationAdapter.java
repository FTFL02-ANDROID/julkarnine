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
import com.ftfl.icare.model.Medication;

public class CustomMedicationAdapter extends ArrayAdapter<Medication> {

	private static LayoutInflater mInflater = null;

	List<Medication> data;
	String mName;
	String mDate;
	String mTime;
	String mPurpose;
	String mAlarm;


	public CustomMedicationAdapter(Activity context, List<Medication> eProfile) {
		super(context, R.layout.activity_madication_list_item,
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
		public TextView tvDate;
		public TextView tvPurpose;
		public TextView tvTime;
		public TextView tvAlarm;
		

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater.inflate(
					R.layout.activity_madication_list_item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tvID = (TextView) vi.findViewById(R.id.tvID);
			holder.tvName = (TextView) vi.findViewById(R.id.tvName);
			holder.tvDate = (TextView) vi
					.findViewById(R.id.tvDate);
			holder.tvPurpose = (TextView) vi.findViewById(R.id.tvPurpose);
			holder.tvTime = (TextView) vi.findViewById(R.id.tvTime);
			holder.tvAlarm = (TextView) vi.findViewById(R.id.tvAlarm);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (data.size() <= 0) {

		} else {

			Medication mMedication;
			mMedication = data.get(position);

			holder.tvID.setText(mMedication.getId().toString());
			holder.tvName.setText( mMedication.getName().toString());
			holder.tvDate.setText(mMedication.getDate().toString());
			holder.tvPurpose.setText( mMedication.getPurpose().toString());
			holder.tvTime.setText(mMedication.getTime().toString());
			holder.tvAlarm.setText(mMedication.getAlarm().toString());
			

		
		}

		return vi;
	}
}
