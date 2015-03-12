package com.ftfl.icare;

import java.util.List;

import com.ftfl.icare.adapter.CustomAppointmentAdapter;
import com.ftfl.icare.adapter.CustomDoctorAdapter;
import com.ftfl.icare.helper.AppointmentDataSource;
import com.ftfl.icare.helper.DoctorProfileDataSource;
import com.ftfl.icare.model.Appointment;
import com.ftfl.icare.model.DoctorProfile;
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

public class FragmentAppointmentList extends Fragment {

	TextView mId_tv = null;
	AppointmentDataSource mAppointmentDataSource;
	Appointment mAppointment;
	FragmentManager mFrgManager;
	Fragment mFragment;
	Context mContext;
	ListView mLvProfileList;
	List<Appointment> mDoctorProfileList;
	String mId;
	Bundle mArgs = new Bundle();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_doctor_list, container,
				false);
		mContext = container.getContext();

		mAppointmentDataSource = new AppointmentDataSource(getActivity());
		mDoctorProfileList = mAppointmentDataSource.appointmentList();
		CustomAppointmentAdapter arrayAdapter = new CustomAppointmentAdapter(
				getActivity(), mDoctorProfileList);

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

												mAppointmentDataSource = new AppointmentDataSource(
														getActivity());
												if (mAppointmentDataSource.deleteData(Integer
														.parseInt(mDoctorProfileList
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
	
	public void setTitle(CharSequence title) {

		getActivity().getActionBar().setTitle(title);
	}

}