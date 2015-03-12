package com.ftfl.icare.util;

import java.util.Calendar;
import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ftfl.icare.R;
import com.ftfl.icare.helper.ICareDailyDietDataSource;
import com.ftfl.icare.model.ICareDailyDietChart;
import com.ftfl.icare.model.ICareProfile;

public class FragmentAddDiet extends Fragment {

	Spinner spFeastName;
	EditText etDate;
	EditText etTime;
	EditText etMenu;
	Button btnSave;
	CheckBox cbAlarm;
	ArrayAdapter<CharSequence> adapter;
	ICareDailyDietDataSource mICareDailyDietDataSource;
	ICareDailyDietChart mICareDailyDietChart;
	FragmentManager frgManager;
	Fragment fragment;
	Context thiscontext;
	String mID = "1";
	Bundle args = new Bundle();
	private SetDateOnClickDialog datePickerObj;
	private setTimeOnclickDialog timePickerObj;
	List<ICareProfile> iCareProfilesList;
	String mAlarm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_add_diet,
				container, false);
		thiscontext = container.getContext();

		spFeastName = (Spinner) view.findViewById(R.id.spFeastName);
		etDate = (EditText) view.findViewById(R.id.etDate);
		etTime = (EditText) view.findViewById(R.id.etTime);
		etMenu = (EditText) view.findViewById(R.id.etMenu);
		cbAlarm = (CheckBox) view.findViewById(R.id.cbAlarm);

		btnSave = (Button) view.findViewById(R.id.btnSave);

		datePickerObj = new SetDateOnClickDialog(etDate, thiscontext);
		timePickerObj = new setTimeOnclickDialog(etTime, thiscontext);

		setSpinner();

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				createDiet(v);

			}
		});

		return view;
	}

	public void setSpinner() {

		adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.dietNameSpinner, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spFeastName.setAdapter(adapter);
	}

	

	public void createDiet(View view) {

		String mDate = etDate.getText().toString();
		String mTime = etTime.getText().toString();
		String mMenu = etMenu.getText().toString();
		String mEventName = spFeastName.getSelectedItem().toString();
		if(cbAlarm.isChecked()){
			mAlarm = "1";
			
			addReminder(2015, 2, 8, 9, 15);
		}
		
		SharedPreferences prfs = getActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		String mProfileId = prfs.getString("profile_id", "");

		mICareDailyDietChart = new ICareDailyDietChart();
		mICareDailyDietChart.setDate(mDate);
		mICareDailyDietChart.setTime(mTime);
		mICareDailyDietChart.setFoodMenu(mMenu);
		mICareDailyDietChart.setEventName(mEventName);
		mICareDailyDietChart.setAlarm(mAlarm);
		mICareDailyDietChart.setProfileId(mProfileId);

		mICareDailyDietDataSource = new ICareDailyDietDataSource(getActivity());
		if (mICareDailyDietDataSource.insert(mICareDailyDietChart) == true) {
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