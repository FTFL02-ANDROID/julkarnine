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
import com.ftfl.icare.model.ICareDailyDietChart;
import com.ftfl.icare.model.ImportantNotes;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CustomDietAdapter extends ArrayAdapter<ICareDailyDietChart> {

	private static LayoutInflater mInflater = null;

	List<ICareDailyDietChart> data;
	String mFeastName;
	String mMenu;
	String mDate;
	String mTime;
	String mAlarm;

	public CustomDietAdapter(Activity context, List<ICareDailyDietChart> eProfile) {
		super(context, R.layout.activity_diet_list_item, eProfile);
		this.data = eProfile;

		/*********** Layout inflator to call external xml layout () ***********/
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView tvID;
		public TextView tvFeastName;
		public TextView tvMenu;
		public TextView tvAlarm;
		public TextView tvDate;
		public TextView tvTime;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater.inflate(R.layout.activity_diet_list_item,
					null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tvID = (TextView) vi.findViewById(R.id.tvID);
			holder.tvFeastName = (TextView) vi.findViewById(R.id.tvFeastName);
			holder.tvMenu = (TextView) vi.findViewById(R.id.tvMenu);
			holder.tvAlarm = (TextView) vi.findViewById(R.id.tvAlarm);
			holder.tvDate = (TextView) vi.findViewById(R.id.tvDate);
			holder.tvTime = (TextView) vi.findViewById(R.id.tvTime);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (data.size() <= 0) {

		} else {

			ICareDailyDietChart mICareDailyDietChart;
			mICareDailyDietChart = data.get(position);

			holder.tvID.setText(mICareDailyDietChart.getId().toString());
			holder.tvFeastName.setText(mICareDailyDietChart.getEventName().toString());
			holder.tvMenu.setText(mICareDailyDietChart.getFoodMenu().toString());
			holder.tvAlarm.setText(mICareDailyDietChart.getAlarm()
					.toString());
			holder.tvDate.setText(mICareDailyDietChart.getDate()
					.toString());
			holder.tvTime.setText(mICareDailyDietChart.getTime()
					.toString());

		}

		return vi;
	}
}
