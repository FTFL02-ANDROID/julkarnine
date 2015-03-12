package com.ftfl.icare;

import java.util.List;

import com.ftfl.icare.adapter.CustomCareCenterAdapter;
import com.ftfl.icare.adapter.CustomDoctorAdapter;
import com.ftfl.icare.adapter.CustomMadicalHistoryAdapter;
import com.ftfl.icare.helper.CareCenterDataSource;
import com.ftfl.icare.helper.DoctorProfileDataSource;
import com.ftfl.icare.helper.MedicalHistoryDataSource;
import com.ftfl.icare.model.CareCenter;
import com.ftfl.icare.model.DoctorProfile;
import com.ftfl.icare.model.MedicalHistory;
import com.ftfl.icare.util.FragmentHome;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentCareCenterList extends Fragment {

	TextView mId_tv = null;
	CareCenterDataSource mCareCenterDataSource;
	CareCenter mCareCenter;
	FragmentManager frgManager;
	Fragment fragment;
	Context thiscontext;
	ListView lvProfileList;
	List<CareCenter> mMedicalHistoryList;
	String mId;
	Bundle args = new Bundle();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_doctor_list, container,
				false);
		thiscontext = container.getContext();

		mCareCenterDataSource = new CareCenterDataSource(getActivity());
		mMedicalHistoryList = mCareCenterDataSource.careCenterList();
		CustomCareCenterAdapter arrayAdapter = new CustomCareCenterAdapter(
				getActivity(), mMedicalHistoryList);

		lvProfileList = (ListView) view.findViewById(R.id.lvDoctorList);
		lvProfileList.setAdapter(arrayAdapter);

		lvProfileList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						final int pos = position;

						new AlertDialog.Builder(thiscontext)
								.setTitle("Delete entry")
								.setMessage(
										"Are you sure you want to delete this entry?")
								.setPositiveButton(android.R.string.yes,
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {

												mCareCenterDataSource = new CareCenterDataSource(
														getActivity());
												if (mCareCenterDataSource.deleteData(Integer
														.parseInt(mMedicalHistoryList
																.get(pos)
																.getId())) == true) {
													Toast toast = Toast
															.makeText(
																	getActivity(),
																	"Successfully Deleted.",
																	Toast.LENGTH_LONG);
													toast.show();

													fragment = new FragmentHome();
													frgManager = getFragmentManager();
													frgManager
															.beginTransaction()
															.replace(
																	R.id.content_frame,
																	fragment)
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
	
	public void setTitle(CharSequence title) {

		getActivity().getActionBar().setTitle(title);
	}


}