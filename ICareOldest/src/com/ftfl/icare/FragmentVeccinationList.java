package com.ftfl.icare;

import java.util.List;

import com.ftfl.icare.adapter.CustomDoctorAdapter;
import com.ftfl.icare.adapter.CustomVeccineAdapter;
import com.ftfl.icare.helper.DoctorProfileDataSource;
import com.ftfl.icare.helper.VaccineDataSource;
import com.ftfl.icare.model.DoctorProfile;
import com.ftfl.icare.model.Vaccine;
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

public class FragmentVeccinationList extends Fragment {

	TextView mId_tv = null;
	VaccineDataSource mVaccineDataSource;
	DoctorProfile mDoctorProfile;
	FragmentManager frgManager;
	Fragment fragment;
	Context thiscontext;
	ListView lvProfileList;
	List<Vaccine> mVeccineList;
	String mId;
	Bundle args = new Bundle();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_veccination_list, container,
				false);
		thiscontext = container.getContext();

		mVaccineDataSource = new VaccineDataSource(getActivity());
		mVeccineList = mVaccineDataSource.vaccineList();
		CustomVeccineAdapter arrayAdapter = new CustomVeccineAdapter(
				getActivity(), mVeccineList);

		lvProfileList = (ListView) view.findViewById(R.id.lvVeccinationList);
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

												mVaccineDataSource = new VaccineDataSource(
														getActivity());
												if (mVaccineDataSource.deleteData(Integer
														.parseInt(mVeccineList
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