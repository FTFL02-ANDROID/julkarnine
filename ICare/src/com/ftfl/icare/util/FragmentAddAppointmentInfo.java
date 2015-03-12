package com.ftfl.icare.util;

import java.util.Calendar;

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
import com.ftfl.icare.helper.AppointmentDataSource;
import com.ftfl.icare.model.Appointment;

public class FragmentAddAppointmentInfo extends Fragment {

	AppointmentDataSource mAppointmentDataSource;
	Appointment mAppointment;
	View view;
	EditText mEtDrName;
	EditText mEtDate;
	EditText mEtTime;
	EditText mEtPurpose;
	EditText mEtLocation;
	CheckBox mCbAlarm;
	Button mBtnSave;
	FragmentManager mFrgManager;
	Fragment mFragment;
	String mAlarm = "0";
	private SetDateOnClickDialog mDatePickerObj;
	private setTimeOnclickDialog mTimePickerObj;
	Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_appointment_info,
				container, false);
		
		mContext = container.getContext();

		mEtDrName = (EditText) view
				.findViewById(R.id.etDrName);
		mEtDate = (EditText) view.findViewById(R.id.etDate);
		mEtTime = (EditText) view.findViewById(R.id.etTime);
		mEtPurpose = (EditText) view.findViewById(R.id.etPurpose);
		mEtLocation = (EditText) view.findViewById(R.id.etLocation);
		mCbAlarm = (CheckBox) view.findViewById(R.id.cbAlarm);

		mDatePickerObj = new SetDateOnClickDialog(mEtDate, mContext);
		mTimePickerObj = new setTimeOnclickDialog(mEtTime, mContext);

		mBtnSave = (Button) view.findViewById(R.id.btnSave);

		mBtnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				createVeccination();

			}

		});

		return view;

	}

	private void createVeccination() {

		String mName = mEtDrName.getText().toString();
		String mDate = mEtDate.getText().toString();
		String mTime = mEtTime.getText().toString();
		String mPurpose = mEtPurpose.getText().toString();
		String mLocation = mEtLocation.getText().toString();

		if (mCbAlarm.isChecked()) {
			mAlarm = "1";

			addReminder(2015, 3, 8, 9, 15);

		}

		SharedPreferences prfs = getActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		String mProfileId = prfs.getString("profile_id", "");

		mAppointmentDataSource = new AppointmentDataSource(getActivity());
		mAppointment = new Appointment();
		mAppointment.setDrName(mName);
		mAppointment.setDate(mDate);
		mAppointment.setTime(mTime);
		mAppointment.setPurpose(mPurpose);
		mAppointment.setLocation(mLocation);
		mAppointment.setAlarm(mAlarm);
		mAppointment.setProfileId(mProfileId);

		if (mAppointmentDataSource.insert(mAppointment) == true) {
			Toast toast = Toast.makeText(getActivity(), "Successfully Saved.",
					Toast.LENGTH_LONG);
			toast.show();

			mFragment = new FragmentHome();
			mFrgManager = getFragmentManager();
			mFrgManager.beginTransaction().replace(R.id.content_frame, mFragment)
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