package com.ftfl.icareprofile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ftfl.icareprofile.helper.DietDataSource;
import com.ftfl.icareprofile.model.DietChart;

public class CreateDietActivity extends Activity {
	Button createDiet;
	private Spinner dietNameSprinner;
	private static String[] mySpinner;
	private ArrayAdapter<CharSequence> adapter;
	private EditText etFoodMenu;
	private DietDataSource dietDS = null;
	DietChart mUpdateActivity = null;
	long mActivityId = 0;
	private TimePicker timePicker;
	private DatePicker datePicker;
	private String mAlarm = "0";
	private CheckBox cbAlarm;
	private int profileId = 1;
	int mHour = 0;
	int mMinute = 0;
	String mStrActivityID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icare_create_diet);
		initialize();
		setSpinner();
		Intent mActivityIntent = getIntent();
		mStrActivityID = mActivityIntent.getStringExtra("activityId");
		if (mStrActivityID != null) {
			update();
		}

	}

	@SuppressLint("SimpleDateFormat")
	public void update() {
		Intent mActivityIntent = getIntent();
		mStrActivityID = mActivityIntent.getStringExtra("activityId");

		if (mStrActivityID != null) {
			mActivityId = Long.parseLong(mStrActivityID);

			dietDS = new DietDataSource(this);
			mUpdateActivity = dietDS.updateActivityData(mActivityId);

			String mDate = mUpdateActivity.getDietDate();
			String mTime = mUpdateActivity.getDietTime();
			String mName = mUpdateActivity.getDietEventName();
			String mDescription = mUpdateActivity.getDietMenu();
			String mAlarm = mUpdateActivity.getAlarm();
			long mActivityAlarm = Long.parseLong(mAlarm);
			
			
			for(int i=0; i < adapter.getCount(); i++) {
				  if(mName.trim().equals(adapter.getItem(i).toString())){
					  dietNameSprinner.setSelection(i);
				    break;
				  }
				}
			
			

			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			Date pkTime = null;
			try {
				pkTime = sdf.parse(mTime);
			} catch (ParseException e) {

			}
			Calendar c = Calendar.getInstance();
			c.setTime(pkTime);

			timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
			timePicker.setCurrentMinute(c.get(Calendar.MINUTE));

			SimpleDateFormat sdatef = new SimpleDateFormat("dd/mm/yyyy");
			Date date = null;
			try {
				date = sdatef.parse(mDate);
			} catch (ParseException e) {

			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);

			datePicker.updateDate(year, month, day);

			etFoodMenu.setText(mDescription);
			if (mActivityAlarm == 1) {
				cbAlarm.setChecked(!cbAlarm.isChecked());
			}

			/*
			 * change button name
			 */
			createDiet.setText("Update");
		}
	}

	public void createDiet(View view) {

		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year = datePicker.getYear();

		String dateOfDiet = day + "/" + (month + 1) + "/" + year;

		mHour = timePicker.getCurrentHour();
		mMinute = timePicker.getCurrentMinute();
		String dietMenu = etFoodMenu.getText().toString();

		if (cbAlarm.isChecked()) {
			mAlarm = "1";
			Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
			alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, dietMenu);
			alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, mHour);
			alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, mMinute);
			alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
			alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// startActivity(alarmIntent);
		}

		String dietName = dietNameSprinner.getSelectedItem().toString();
		String dietTime = timePicker.getCurrentHour() + ":"
				+ timePicker.getCurrentMinute();

		DietChart diet = new DietChart(dateOfDiet, dietTime, dietMenu,
				dietName, mAlarm);

		dietDS = new DietDataSource(this);
		if (dietDS.insert(diet) == true) {
			Toast toast = Toast.makeText(this, "Successfully Saved.",
					Toast.LENGTH_LONG);
			toast.show();

			startActivity(new Intent(CreateDietActivity.this,
					HomeActivity.class));

		} else {
			Toast toast = Toast.makeText(this,
					"Error, Couldn't inserted data to database",
					Toast.LENGTH_LONG);
			toast.show();

		}
	}

	public void initialize() {

		dietNameSprinner = (Spinner) findViewById(R.id.dietName);
		etFoodMenu = (EditText) findViewById(R.id.menuEditText);
		datePicker = (DatePicker) findViewById(R.id.dpDiet);
		timePicker = (TimePicker) findViewById(R.id.time);
		cbAlarm = (CheckBox) findViewById(R.id.cbAlarm);
		createDiet = (Button) findViewById(R.id.CreateDiet);

	}

	public void setSpinner() {

		adapter = ArrayAdapter.createFromResource(this,
				R.array.dietNameSpinner, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		dietNameSprinner.setAdapter(adapter);
	}

}
