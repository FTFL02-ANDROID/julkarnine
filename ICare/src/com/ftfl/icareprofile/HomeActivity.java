package com.ftfl.icareprofile;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icareprofile.Adapter.Customadapter;
import com.ftfl.icareprofile.helper.DietDataSource;
import com.ftfl.icareprofile.helper.SQLiteHelper;
import com.ftfl.icareprofile.model.DietChart;

public class HomeActivity extends Activity {
	ListView mListViewOne = null;
	ListView mListViewTwo = null;
	DietDataSource mDietDataSource = null;
	SQLiteHelper mDBHelper = null;
	DietChart dailyDietChart = null;
	int id_To_Update = 0;
	TextView tvDate = null;
	List<String> diet_list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homescreen);
		
		mDBHelper = new SQLiteHelper(this);	
		mDietDataSource = new DietDataSource(this);
		List<DietChart> allDailyDietChart = mDietDataSource.todayDietChart();
		Customadapter arrayAdapterOne = new Customadapter(this, allDailyDietChart);
		mListViewOne = (ListView) findViewById(R.id.lvTodayDietChart);
		mListViewOne.setAdapter(arrayAdapterOne);
		
		
		List<String> upcomingDates = mDietDataSource.upcomingDates();
		
		ArrayAdapter<String> mDatesAdapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1, upcomingDates);
		mListViewTwo = (ListView) findViewById(R.id.lvUpComingDietChart);
		mListViewTwo.setAdapter(mDatesAdapter);
		mListViewTwo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					android.view.View view, int position, long id) {

				tvDate = (TextView) view.findViewById(android.R.id.text1);
				String dateValue = tvDate.getText().toString(); 
																
																

				/*
				 * create an intent and send data
				 */
				Intent intnet = new Intent(getApplicationContext(),
						DailyDietViewActivity.class);
				intnet.putExtra("cDate", dateValue);

				startActivity(intnet);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_createProfile:
			// search action
			CreateProfile();
			return true;
		case R.id.action_viewProfile:
			// location found
			ViewProfile();
			return true;
		case R.id.action_addDiet:
			// refresh
			CreateDiet();
			return true;
		case R.id.action_viewDiet:
			// help action
			DietList();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Launching new activity
	 * */
	private void CreateProfile() {
		Intent i = new Intent(getApplicationContext(),
				CreateProfileActivity.class);
		startActivity(i);
	}

	private void ViewProfile() {
		Intent i = new Intent(HomeActivity.this, ViewProfileActivity.class);
		startActivity(i);
	}

	private void CreateDiet() {
		Intent i = new Intent(HomeActivity.this, CreateDietActivity.class);
		startActivity(i);
	}

	private void DietList() {
		Intent i = new Intent(HomeActivity.this, DietListActivity.class);
		startActivity(i);
	}
}
