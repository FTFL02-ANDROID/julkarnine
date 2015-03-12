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
import com.ftfl.icare.model.Vaccine;

public class CustomVeccineAdapter extends ArrayAdapter<Vaccine> {

	private static LayoutInflater mInflater = null;

	List<Vaccine> data;
	String mName;
	String mDate;
	String mTime;
	String mDescription;
	String mStatus;
	String mAlarm;

	public CustomVeccineAdapter(Activity context, List<Vaccine> eProfile) {
		super(context, R.layout.activity_veccination_list_item, eProfile);
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
		public TextView tvDescription;
		public TextView tvStatus;
		public TextView tvAlarm;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater.inflate(R.layout.activity_veccination_list_item,
					null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tvID = (TextView) vi.findViewById(R.id.tvID);
			holder.tvName = (TextView) vi.findViewById(R.id.tvName);
			holder.tvDate = (TextView) vi.findViewById(R.id.tvDate);
			holder.tvTime = (TextView) vi.findViewById(R.id.tvTime);
			holder.tvDescription = (TextView) vi
					.findViewById(R.id.tvDescription);
			holder.tvStatus = (TextView) vi.findViewById(R.id.tvStatus);
			holder.tvAlarm = (TextView) vi.findViewById(R.id.tvAlarm);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (data.size() <= 0) {

		} else {

			Vaccine mVaccine;
			mVaccine = data.get(position);

			holder.tvID.setText(mVaccine.getId().toString());
			holder.tvName.setText(mVaccine.getName().toString());
			holder.tvDate.setText(mVaccine.getDate().toString());
			holder.tvTime.setText(mVaccine.getTime().toString());
			holder.tvDescription.setText(mVaccine.getNotes().toString());
			holder.tvStatus.setText(mVaccine.getStatus().toString());
			holder.tvAlarm.setText(mVaccine.getAlarm().toString());

		}

		return vi;
	}
}
