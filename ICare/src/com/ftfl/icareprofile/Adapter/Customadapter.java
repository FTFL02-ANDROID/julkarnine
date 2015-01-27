package com.ftfl.icareprofile.Adapter;

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

import com.ftfl.icareprofile.R;
import com.ftfl.icareprofile.model.DietChart;

public class Customadapter extends ArrayAdapter<DietChart> {

	private static LayoutInflater inflater = null;

	private final Activity context;
	List<DietChart> allDailyDietChart;

	public Customadapter(Activity context, List<DietChart> allDailyDietChart) {
		super(context, R.layout.activity_list_view, allDailyDietChart);
		this.context = context;
		this.allDailyDietChart = allDailyDietChart;

		/*********** Layout inflator to call external xml layout () ***********/
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView id;
		public TextView event;
		public TextView time;
		public TextView foodMenu;
		public ImageView image;

	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.activity_list_view, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.id = (TextView) vi.findViewById(R.id.viewId);
			holder.event = (TextView) vi.findViewById(R.id.viewEvent);
			holder.time = (TextView) vi.findViewById(R.id.viewTime);
			holder.foodMenu = (TextView) vi.findViewById(R.id.viewManu);
			holder.image = (ImageView) vi.findViewById(R.id.imageAlarm);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		DietChart mDietChart;
		mDietChart = allDailyDietChart.get(position);

		holder.id.setText(mDietChart.getDietId().toString());
		holder.event.setText(mDietChart.getDietEventName().toString());
		holder.time.setText(mDietChart.getDietTime().toString());
		holder.foodMenu.setText(mDietChart.getDietMenu().toString());
		String mAlarm = mDietChart.getAlarm();
		
		
		if (mAlarm == "0") {
			holder.image.setVisibility(View.GONE);
		} else {
			holder.image.setVisibility(View.VISIBLE);
		}
		return vi;
	}
}
