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
import com.ftfl.icare.model.ImportantNotes;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CustomAddNoteAdapter extends ArrayAdapter<ImportantNotes> {

	private static LayoutInflater mInflater = null;

	List<ImportantNotes> data;
	String mTitle;
	String mSubject;
	String mDescription;

	public CustomAddNoteAdapter(Activity context, List<ImportantNotes> eProfile) {
		super(context, R.layout.activity_important_note_list_item, eProfile);
		this.data = eProfile;

		/*********** Layout inflator to call external xml layout () ***********/
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView tvID;
		public TextView tvTitle;
		public TextView tvSubject;
		public TextView tvDescription;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater.inflate(R.layout.activity_important_note_list_item,
					null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tvID = (TextView) vi.findViewById(R.id.tvID);
			holder.tvTitle = (TextView) vi.findViewById(R.id.tvTitle);
			holder.tvSubject = (TextView) vi.findViewById(R.id.tvSubject);
			holder.tvDescription = (TextView) vi
					.findViewById(R.id.tvDescription);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (data.size() <= 0) {

		} else {

			ImportantNotes mImportantNotes;
			mImportantNotes = data.get(position);

			holder.tvID.setText(mImportantNotes.getId().toString());
			holder.tvTitle.setText(mImportantNotes.getTitle().toString());
			holder.tvSubject.setText(mImportantNotes.getSubject().toString());
			holder.tvDescription.setText(mImportantNotes.getDescription()
					.toString());

		}

		return vi;
	}
}
