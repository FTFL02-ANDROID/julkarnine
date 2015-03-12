package com.ftfl.icare.util;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.icare.R;
import com.ftfl.icare.helper.VaccineDataSource;
import com.ftfl.icare.model.Vaccine;

public class FragmentAddVaccination extends Fragment {

	VaccineDataSource mVaccineDataSource;
	Vaccine mVaccine;
	View view;
	EditText etVaccinationName;
	EditText etDate;
	EditText etTime;
	EditText etDescription;
	EditText etStatus;
	CheckBox cbAlarm;
	Button btnSave;
	FragmentManager frgManager;
	Fragment fragment;
	String mAlarm = "0";
	private SetDateOnClickDialog datePickerObj;
	private setTimeOnclickDialog timePickerObj;
	Context thiscontext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_layout_add_vaccination,
				container, false);
		thiscontext = container.getContext();

		etVaccinationName = (EditText) view
				.findViewById(R.id.etVaccinationName);
		etDate = (EditText) view.findViewById(R.id.etDate);
		etTime = (EditText) view.findViewById(R.id.etTime);
		etDescription = (EditText) view.findViewById(R.id.etDescription);
		etStatus = (EditText) view.findViewById(R.id.etStatus);
		cbAlarm = (CheckBox) view.findViewById(R.id.cbAlarm);

		datePickerObj = new SetDateOnClickDialog(etDate, thiscontext);
		timePickerObj = new setTimeOnclickDialog(etTime, thiscontext);

		btnSave = (Button) view.findViewById(R.id.btnSave);

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				createVeccination();

			}

		});

		return view;
	}

	private void createVeccination() {

		String mName = etVaccinationName.getText().toString();
		String mDate = etDate.getText().toString();
		String mTime = etTime.getText().toString();
		String mNotes = etDescription.getText().toString();
		String mStatus = etStatus.getText().toString();

		

		SharedPreferences prfs = getActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		String mProfileId = prfs.getString("profile_id", "");

		mVaccineDataSource = new VaccineDataSource(getActivity());
		mVaccine = new Vaccine();
		mVaccine.setName(mName);
		mVaccine.setDate(mDate);
		mVaccine.setTime(mTime);
		mVaccine.setNotes(mNotes);
		mVaccine.setStatus(mStatus);
		mVaccine.setAlarm(mAlarm);
		mVaccine.setProfileId(mProfileId);
		
		if (cbAlarm.isChecked()) {
			mAlarm = "1";

			addReminder(2015, 2, 8, 9, 15);

		}

		if (mVaccineDataSource.insert(mVaccine) == true) {
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

	public void addReminder(int mYear, int mMonth, int mDay, int mHour,
			int mMinute) {
		Calendar c = Calendar.getInstance();

		// set Reminder time and date into calendar object
		c.set(Calendar.YEAR, mYear);
		c.set(Calendar.MONTH, mMonth);// Don't use exact numeric value of the
										// month, use one minus.Ex: April=>as 3
		c.set(Calendar.DATE, mDay);
		c.set(Calendar.HOUR_OF_DAY, mHour);
		c.set(Calendar.MINUTE, mMinute);
		c.set(Calendar.SECOND, 0);

		// Unique Alarm ID creation
		int alrmId = 0;
		alrmId = Integer.parseInt(mMonth + "" + mDay + "" + mHour + ""
				+ mMinute);
		// Alarm task creation
		Intent in = new Intent(getActivity(), ReminderReceiver.class);
		in.putExtra("text", "You have a Reminder!");
		in.putExtra("AlrmId", alrmId);

		PendingIntent pi;

		pi = PendingIntent.getBroadcast(getActivity(), alrmId, in, 0);

		AlarmManager am;

		am = (AlarmManager) (getActivity()
				.getSystemService(Context.ALARM_SERVICE));

		am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);

	}

	public void setTitle(CharSequence title) {
		getActivity().getActionBar().setTitle(title);
	}

}