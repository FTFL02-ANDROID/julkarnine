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
import com.ftfl.icare.model.MedicalHistory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CustomMadicalHistoryAdapter extends ArrayAdapter<MedicalHistory> {

	private static LayoutInflater mInflater = null;

	List<MedicalHistory> data;
	String mName;
	String mDate;
	String mPurpose;
	String mSuggestion;
	String mImagePath;
    ImageLoader imageLoader;

	public CustomMadicalHistoryAdapter(Activity context, List<MedicalHistory> eProfile) {
		super(context, R.layout.activity_madical_history_list_item,
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
		public TextView tvSuggestion;
		public ImageView ivImage;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater.inflate(
					R.layout.activity_madical_history_list_item, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tvID = (TextView) vi.findViewById(R.id.tvID);
			holder.tvName = (TextView) vi.findViewById(R.id.tvName);
			holder.tvDate = (TextView) vi
					.findViewById(R.id.tvDate);
			holder.tvPurpose = (TextView) vi.findViewById(R.id.tvPurpose);
			holder.tvSuggestion = (TextView) vi.findViewById(R.id.tvSuggestion);
			holder.ivImage = (ImageView) vi.findViewById(R.id.ivImage);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (data.size() <= 0) {

		} else {

			MedicalHistory mMedicalHistory;
			mMedicalHistory = data.get(position);

			holder.tvID.setText(mMedicalHistory.getId().toString());
			holder.tvName.setText( mMedicalHistory.getDoctorName().toString());
			holder.tvDate.setText(mMedicalHistory.getDate().toString());
			holder.tvPurpose.setText( mMedicalHistory.getPurpose().toString());
			holder.tvSuggestion.setText(mMedicalHistory.getSuggestion().toString());
			

			String imagePath = mMedicalHistory.getPrescription();

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.displayer(new RoundedBitmapDisplayer(0))
					.cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance().displayImage("file:///" + imagePath,
					holder.ivImage, options);

		}

		return vi;
	}
}
