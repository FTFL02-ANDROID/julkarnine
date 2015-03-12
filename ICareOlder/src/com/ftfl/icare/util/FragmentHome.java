package com.ftfl.icare.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftfl.icare.FragmentAppointmentList;
import com.ftfl.icare.FragmentCareCenterList;
import com.ftfl.icare.FragmentDietList;
import com.ftfl.icare.FragmentDoctorList;
import com.ftfl.icare.FragmentImportantDateList;
import com.ftfl.icare.FragmentMadicationList;
import com.ftfl.icare.FragmentMedicalHistoryList;
import com.ftfl.icare.FragmentVeccinationList;
import com.ftfl.icare.R;

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
						.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

				setTitle("Diet List");

			}
		});

		mTvDoctorList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				fragment = new FragmentDoctorList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

				setTitle("Doctor List");

			}
		});

		mTvMadicationList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				fragment = new FragmentMadicationList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
					.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

				setTitle("Madication List");

			}
		});

		mTvAppointmentList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment = new FragmentAppointmentList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

				setTitle("Appointment List");

			}
		});

		mTvVeccinationList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment = new FragmentVeccinationList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

				setTitle("Veccination List");

			}
		});

		mTvMadicalHistoryList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment = new FragmentMedicalHistoryList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

				setTitle("Medical History List");

			}
		});

		mTvImportantDateList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment = new FragmentImportantDateList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

				setTitle("Important Date List");

			}
		});

		mTvCareCenterList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fragment = new FragmentCareCenterList();
				frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).addToBackStack(null).commit();

				setTitle("Care Center List");

			}
		});

		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    // TODO Add your menu entries here
	    inflater.inflate(R.menu.main, menu);
	    super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.veccinationBtn:
			fragment = new FragmentViewGeneralVaccination();
			frgManager = getFragmentManager();
			fragment.setArguments(args);
			frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null)
					.commit();
			setTitle("Veccination Information");
			return true;
		case R.id.growthBtn:
			fragment = new FragmentViewGeneralGrowth();
			frgManager = getFragmentManager();
			fragment.setArguments(args);
			frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null)
					.commit();
			setTitle("Growth Information");
			return true;

		case R.id.nutritionBtn:
			fragment = new FragmentViewGeneralDiet();
			frgManager = getFragmentManager();
			fragment.setArguments(args);
			frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null)
					.addToBackStack(null).commit();
			setTitle("Nutrition Information");
			return true;

		
		}
	    return true;

	}

}