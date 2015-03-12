package com.ftfl.icare.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.icare.R;
import com.ftfl.icare.helper.DoctorProfileDataSource;
import com.ftfl.icare.helper.ICareProfileDataSource;
import com.ftfl.icare.model.DoctorProfile;

public class FragmentAddDoctor extends Fragment {

	DoctorProfileDataSource mDoctorProfileDataSource;
	DoctorProfile mDoctorProfile;
	View view;
	EditText etDrName;
	EditText etSpecialization;
	EditText etPhone;
	EditText etEmail;
	EditText etLocation;
	Button btnSave;
	FragmentManager frgManager;
	Fragment fragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		

		view = inflater.inflate(R.layout.fragment_layout_add_doctor, container,
				false);

		etDrName = (EditText) view.findViewById(R.id.etDrName);
		etSpecialization = (EditText) view.findViewById(R.id.etSpecialization);
		etPhone = (EditText) view.findViewById(R.id.etPhone);
		etEmail = (EditText) view.findViewById(R.id.etEmail);
		etLocation = (EditText) view.findViewById(R.id.etLocation);
		btnSave = (Button) view.findViewById(R.id.btnSave);

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				createDoctorProfile();

			}

		});

		return view;
	}

	private void createDoctorProfile() {
		
		String mName = etDrName.getText().toString();
		String mSpecialization = etSpecialization.getText().toString();
		String mPhone = etPhone.getText().toString();
		String mEmail = etEmail.getText().toString();
		String mAddress = etLocation.getText().toString();
		
		SharedPreferences prfs = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		String mProfileId = prfs.getString("profile_id", "");

		mDoctorProfileDataSource = new DoctorProfileDataSource(getActivity());
		mDoctorProfile = new DoctorProfile();
		mDoctorProfile.setName(mName);
		mDoctorProfile.setSpecialization(mSpecialization);
		mDoctorProfile.setPhone(mPhone);
		mDoctorProfile.setEmail(mEmail);
		mDoctorProfile.setAddress(mAddress);
		mDoctorProfile.setProfileId(mProfileId);
		
		
		if (mDoctorProfileDataSource.insert(mDoctorProfile) == true) {
			Toast toast = Toast.makeText(getActivity(), "Successfully Saved.",
					Toast.LENGTH_LONG);
			toast.show();

			fragment = new FragmentHome();
			frgManager = getFragmentManager();
			frgManager.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();
			setTitle("Home");

		} else {
			Toast toast = Toast.makeText(getActivity(),
					"Error, Couldn't inserted data to database",
					Toast.LENGTH_LONG);
			toast.show();

		}

	}
	
	public void setTitle(CharSequence title) {
		getActivity().getActionBar().setTitle(title);
	}

}