package com.ftfl.icare;

import java.util.List;

import com.ftfl.icare.adapter.CustomDoctorAdapter;
import com.ftfl.icare.adapter.CustomMadicalHistoryAdapter;
import com.ftfl.icare.helper.DoctorProfileDataSource;
import com.ftfl.icare.helper.ICareProfileDataSource;
import com.ftfl.icare.helper.MedicalHistoryDataSource;
import com.ftfl.icare.model.DoctorProfile;
import com.ftfl.icare.model.ICareProfile;
import com.ftfl.icare.model.MedicalHistory;
import com.ftfl.icare.util.FragmentHome;
import com.ftfl.icare.util.ICareConstants;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMedicalHistoryList extends Fragment {

	TextView mId_tv = null;
	MedicalHistoryDataSource mMedicalHistoryDataSource;
	MedicalHistory mMedicalHistory;
	FragmentManager mFrgManager;
	Fragment mFragment;
	Context mContext;
	ListView mLvProfileList;
	List<MedicalHistory> mMedicalHistoryList;
	String mId;
	Bundle args = new Bundle();
	ICareProfileDataSource mProfileDataSource;
	ICareProfile mIProfile;
	List<ICareProfile> mICareProfilesList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_doctor_list, container,
				false);
		mContext = container.getContext();
		
		mProfileDataSource = new ICareProfileDataSource(getActivity());
		mICareProfilesList = mProfileDataSource.iCareProfilesList();
		
		if(mICareProfilesList.size() == 1){
			share(mICareProfilesList.get(0).getID());
		}

		mMedicalHistoryDataSource = new MedicalHistoryDataSource(getActivity());
		mMedicalHistoryList = mMedicalHistoryDataSource.medicalHistoryList();
		CustomMadicalHistoryAdapter arrayAdapter = new CustomMadicalHistoryAdapter(
				getActivity(), mMedicalHistoryList);

		mLvProfileList = (ListView) view.findViewById(R.id.lvDoctorList);
		mLvProfileList.setAdapter(arrayAdapter);

		mLvProfileList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						final int pos = position;

						new AlertDialog.Builder(mContext)
								.setTitle("Delete entry")
								.setMessage(
										"Are you sure you want to delete this entry?")
								.setPositiveButton(android.R.string.yes,
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {

												mMedicalHistoryDataSource = new MedicalHistoryDataSource(
														getActivity());
												if (mMedicalHistoryDataSource.deleteData(Integer
														.parseInt(mMedicalHistoryList
																.get(pos)
																.getId())) == true) {
													Toast toast = Toast
															.makeText(
																	getActivity(),
																	"Successfully Deleted.",
																	Toast.LENGTH_LONG);
													toast.show();

													mFragment = new FragmentHome();
													mFrgManager = getFragmentManager();
													mFrgManager
															.beginTransaction()
															.replace(
																	R.id.content_frame,
																	mFragment)
															.commit();
													setTitle("Home");

												} else {
													Toast toast = Toast
															.makeText(
																	getActivity(),
																	"Error, Couldn't inserted data to database",
																	Toast.LENGTH_LONG);
													toast.show();

												}
											}
										})
								.setNegativeButton(android.R.string.no,
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {
												// do nothing
											}
										})
								.setIcon(android.R.drawable.ic_dialog_alert)
								.show();

					}
				});

		return view;
	}
	
	public void share(String position) {

		SharedPreferences preferences = getActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("profile_id", position);
		editor.apply();
		
		ICareConstants.SELECTED_PROFILE_ID = Integer.parseInt(position);

	}
	
	public void setTitle(CharSequence title) {

		getActivity().getActionBar().setTitle(title);
	}


}