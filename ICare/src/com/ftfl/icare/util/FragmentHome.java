package com.ftfl.icare.util;

import java.util.List;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icare.FragmentAppointmentList;
import com.ftfl.icare.FragmentCareCenterList;
import com.ftfl.icare.FragmentDietList;
import com.ftfl.icare.FragmentDoctorList;
import com.ftfl.icare.FragmentImportantNoteList;
import com.ftfl.icare.FragmentMadicationList;
import com.ftfl.icare.FragmentMedicalHistoryList;
import com.ftfl.icare.FragmentVeccinationList;
import com.ftfl.icare.FragmentViewProfile;
import com.ftfl.icare.R;
import com.ftfl.icare.adapter.CustomProfileAdapter;
import com.ftfl.icare.helper.ICareProfileDataSource;
import com.ftfl.icare.model.ICareProfile;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FragmentHome extends Fragment {
	TextView mTvDietList;
	TextView mTvDoctorList;
	TextView mTvMadicationList;
	TextView mTvAppointmentList;
	TextView mTvVeccinationList;
	TextView mTvMadicalHistoryList;
	TextView mTvImportantDateList;
	TextView mTvCareCenterList;
	FragmentManager frgManager;
	Fragment fragment;
	Bundle args = new Bundle();
	private CharSequence mTitle;
	String id;
	TextView mId_tv = null;
	ICareProfileDataSource ProfileDataSource;
	ICareProfile IProfile;
	String mId;

	ListView lvProfileList;
	List<ICareProfile> iCareProfilesList;

	public void setTitle(CharSequence title) {
		mTitle = title;
		getActivity().getActionBar().setTitle(mTitle);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.main_activity_content, container,
				false);

		setHasOptionsMenu(true);
		
		
		
		ProfileDataSource = new ICareProfileDataSource(getActivity());
		iCareProfilesList = ProfileDataSource.iCareProfilesList();
		
		if(iCareProfilesList.size() == 1){
			
			
			share(iCareProfilesList.get(0).getID());
			
		}
		
		
		mTvDietList = (TextView) view.findViewById(R.id.tvDietList);
		mTvDoctorList = (TextView) view.findViewById(R.id.tvDoctorList);
		mTvMadicationList = (TextView) view.findViewById(R.id.tvMadicationList);
		mTvAppointmentList = (TextView) view
				.findViewById(R.id.tvAppointmentList);
		mTvVeccinationList = (TextView) view
				.findViewById(R.id.tvVeccinationList);
		mTvMadicalHistoryList = (TextView) view
				.findViewById(R.id.tvMadicalHistoryList);
		mTvImportantDateList = (TextView) view
				.findViewById(R.id.tvImportantDateList);
		mTvCareCenterList = (TextView) view.findViewById(R.id.tvCareCenterList);

		mTvDietList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment = new FragmentDietList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

				setTitle("Today Diet Chart");

			}
		});

		mTvDoctorList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				fragment = new FragmentDoctorList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

				setTitle("Doctor List");

			}
		});

		mTvMadicationList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				fragment = new FragmentMadicationList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

				setTitle("Madication List");

			}
		});

		mTvAppointmentList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment = new FragmentAppointmentList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

				setTitle("Appointment List");

			}
		});

		mTvVeccinationList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment = new FragmentVeccinationList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

				setTitle("Veccination List");

			}
		});

		mTvMadicalHistoryList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				ProfileDataSource = new ICareProfileDataSource(getActivity());
				iCareProfilesList = ProfileDataSource.iCareProfilesList();
				
				if(iCareProfilesList.size() == 1){
					
					
					share(iCareProfilesList.get(0).getID());
					
				}
				
				fragment = new FragmentMedicalHistoryList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

				setTitle("Medical History List");

			}
		});

		mTvImportantDateList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment = new FragmentImportantNoteList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

				setTitle("Important Note List");

			}
		});

		mTvCareCenterList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				ProfileDataSource = new ICareProfileDataSource(getActivity());
				iCareProfilesList = ProfileDataSource.iCareProfilesList();
				
				if(iCareProfilesList.size() == 1){
					
					
					share(iCareProfilesList.get(0).getID());
					
				}
				
				fragment = new FragmentCareCenterList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

				setTitle("Care Center List");

			}
		});

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.viewProfile:
			fragment = new FragmentViewProfile();
			frgManager = getFragmentManager();
			fragment.setArguments(args);
			frgManager.beginTransaction().replace(R.id.content_frame, fragment)
					.addToBackStack(null).commit();
			setTitle("View Profile");
			return true;

		case R.id.SwitchUser:

			dialogOnStart();

			return true;

		}
		return true;

	}

	public void dialogOnStart() {

		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.fragment_layout_view_profile_list);
		dialog.setTitle("Select Profile");

		// Create global configuration and initialize ImageLoader with
		// getActivity()
		// config
		// Create default options which will be used for every
		// displayImage(...) call if no options will be passed to getActivity()
		// method
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity()).defaultDisplayImageOptions(defaultOptions)
				.build();
		ImageLoader.getInstance().init(config);

		ProfileDataSource = new ICareProfileDataSource(getActivity());
		iCareProfilesList = ProfileDataSource.iCareProfilesList();
		CustomProfileAdapter arrayAdapter = new CustomProfileAdapter(
				getActivity(), iCareProfilesList);
		lvProfileList = (ListView) dialog.findViewById(R.id.lvProfileList);
		lvProfileList.setAdapter(arrayAdapter);

		lvProfileList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						mId_tv = (TextView) view.findViewById(R.id.tvID);
						mId = mId_tv.getText().toString();

						share(iCareProfilesList.get(position).getID());

						dialog.dismiss();
					}
				});

		dialog.show();
	}

	public void share(String position) {

		SharedPreferences preferences = getActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("profile_id", position);
		editor.apply();
		
		ICareConstants.SELECTED_PROFILE_ID = Integer.parseInt(position);

	}

}