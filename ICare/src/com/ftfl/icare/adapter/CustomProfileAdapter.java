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
import com.ftfl.icare.model.ICareProfile;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CustomProfileAdapter extends ArrayAdapter<ICareProfile> {

	private static LayoutInflater mInflater = null;

	List<ICareProfile> data;
	String mName;
	String mDateOfBirth;
	String mWeight;
	String mHeight;
	String mGender;
	String mBloodGroup;
	String mImagePath;

	ImageLoader imageLoader;

	public CustomProfileAdapter(Activity context, List<ICareProfile> eProfile) {
		super(context, R.layout.fragment_layout_view_profile_list_item,
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
		public TextView tvDateOfBirth;
		public TextView tvHeight;
		public TextView tvGender;
		public TextView tvBloodGroup;
		public TextView tvWeight;
		public ImageView ivImage;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater.inflate(
					R.layout.fragment_layout_view_profile_list_item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tvID = (TextView) vi.findViewById(R.id.tvID);
			holder.tvName = (TextView) vi.findViewById(R.id.tvName);
			holder.tvDateOfBirth = (TextView) vi
					.findViewById(R.id.tvDateOfBirth);
			holder.tvHeight = (TextView) vi.findViewById(R.id.tvHeight);
			holder.tvGender = (TextView) vi.findViewById(R.id.tvGender);
			holder.tvBloodGroup = (TextView) vi.findViewById(R.id.tvBloodGroup);
			holder.tvWeight = (TextView) vi.findViewById(R.id.tvWeight);
			holder.ivImage = (ImageView) vi.findViewById(R.id.ivImage);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (data.size() <= 0) {

		} else {

			ICareProfile mProfile;
			mProfile = data.get(position);

			holder.tvID.setText(mProfile.getID().toString());
			holder.tvName.setText( mProfile.getName().toString());
			holder.tvDateOfBirth.setText(mProfile.getDateOfBirth().toString());
			holder.tvHeight.setText( mProfile.getHeight().toString());
			holder.tvGender.setText(mProfile.getGender().toString());
			holder.tvBloodGroup.setText(mProfile.getBloodGroup().toString());
			holder.tvWeight.setText(mProfile.getWeight().toString());

			String imagePath = mProfile.getImage();

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.displayer(new RoundedBitmapDisplayer(0))
					.cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance().displayImage("file:///" + imagePath,
					holder.ivImage, options);

		}

		return vi;
	}
}
