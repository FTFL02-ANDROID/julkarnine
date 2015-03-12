package com.ftfl.icare.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.model.CareCenter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CustomCareCenterAdapter extends ArrayAdapter<CareCenter> {

	private static LayoutInflater mInflater = null;

	List<CareCenter> data;
	String mName;
	String mLocation;
	String mImagePath;

	ImageLoader imageLoader;

	public CustomCareCenterAdapter(Activity context, List<CareCenter> eProfile) {
		super(context, R.layout.activity_care_center_list_item,
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
		public TextView tvLocation;
		public ImageView ivImage;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater.inflate(
					R.layout.activity_care_center_list_item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tvID = (TextView) vi.findViewById(R.id.tvID);
			holder.tvName = (TextView) vi.findViewById(R.id.tvName);
			holder.tvLocation = (TextView) vi
					.findViewById(R.id.tvLocation);
			holder.ivImage = (ImageView) vi.findViewById(R.id.ivImage);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (data.size() <= 0) {

		} else {

			CareCenter mCareCenter;
			mCareCenter = data.get(position);

			holder.tvID.setText(mCareCenter.getId().toString());
			holder.tvName.setText( mCareCenter.getName().toString());
			holder.tvLocation.setText(mCareCenter.getLocation().toString());
			String imagePath = mCareCenter.getImage();

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.displayer(new RoundedBitmapDisplayer(0))
					.cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance().displayImage("file:///" + imagePath,
					holder.ivImage, options);

		}

		return vi;
	}
}
