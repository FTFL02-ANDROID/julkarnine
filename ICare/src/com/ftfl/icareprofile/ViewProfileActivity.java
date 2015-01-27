package com.ftfl.icareprofile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icareprofile.model.Profile;
import com.google.gson.Gson;

public class ViewProfileActivity extends Activity {
	Profile mPObj;
	SharedPreferences mMyPrefs;
	TextView mTextViewName;
	TextView mTextViewFName;
	TextView mTextViewMName;
	TextView mTextViewDOB;
	TextView mTextViewWeight;
	TextView mTextViewHeight;
	TextView mTextViewEyeColor;
	TextView mTextViewComments;
	Button mBtnAddDailyDiet;
	Button mBtnUpdateMyPref;
	ListView mlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_profile);

		// initialize
		mMyPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
		mTextViewName = (TextView) findViewById(R.id.editTextName);
		mTextViewFName = (TextView) findViewById(R.id.editTextFName);
		mTextViewMName = (TextView) findViewById(R.id.editTextMName);
		mTextViewDOB = (TextView) findViewById(R.id.editTextDOB);
		mTextViewWeight = (TextView) findViewById(R.id.editTextWeight);
		mTextViewHeight = (TextView) findViewById(R.id.editTextHeight);
		mTextViewEyeColor = (TextView) findViewById(R.id.editTextEyeColor);
		mTextViewComments = (TextView) findViewById(R.id.editTextComments);
		mBtnAddDailyDiet=(Button) findViewById(R.id.btnAddDailyDiet);
		mBtnUpdateMyPref=(Button) findViewById(R.id.btnUpdateMyPref);


		// getting data from shared preference
		Gson gson = new Gson();
		String json = mMyPrefs.getString("myProfile", "");
		mPObj = gson.fromJson(json, Profile.class);

		// setting data to text view
		mTextViewName.setText(mPObj.getName());
		mTextViewName.setFocusable(false);
		mTextViewName.setClickable(false);
		mTextViewFName.setText(mPObj.getFateherName());
		mTextViewFName.setFocusable(false);
		mTextViewFName.setClickable(false);
		mTextViewMName.setText(mPObj.getMotherName());
		mTextViewMName.setFocusable(false);
		mTextViewMName.setClickable(false);
		mTextViewDOB.setText(mPObj.getDateOfBirth());
		mTextViewDOB.setFocusable(false);
		mTextViewDOB.setClickable(false);
		mTextViewWeight.setText(mPObj.getWeight());
		mTextViewWeight.setFocusable(false);
		mTextViewWeight.setClickable(false);
		mTextViewHeight.setText(mPObj.getHeight());
		mTextViewHeight.setFocusable(false);
		mTextViewHeight.setClickable(false);
		mTextViewEyeColor.setText(mPObj.getEyeColor());
		mTextViewEyeColor.setFocusable(false);
		mTextViewEyeColor.setClickable(false);
		mTextViewComments.setText(mPObj.getSpecialComment());
		mTextViewComments.setFocusable(false);
		mTextViewComments.setClickable(false);


		initCallbacks();

	}

	private void initCallbacks() {

		
		// go to adding Diet chart
		mBtnAddDailyDiet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(ViewProfileActivity.this,
						CreateDietActivity.class);
				startActivity(i);

			}
		});
		// update profile
		mBtnUpdateMyPref.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Editor prefsEditor = mMyPrefs.edit();
				Gson gson = new Gson();
				String json = gson.toJson(mPObj);
				prefsEditor.putString("myProfile", json);
				prefsEditor.commit();

				Intent i = new Intent(ViewProfileActivity.this,
						CreateProfileActivity.class);
				startActivity(i);

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
		// Intent intent = new
		// Intent(getApplicationContext(),com.ftfl.sqliteexample.DisplayContact.class);
		Intent i = new Intent(getApplicationContext(),
				CreateProfileActivity.class);
		startActivity(i);
	}

	private void ViewProfile() {
		Intent i = new Intent(ViewProfileActivity.this, HomeActivity.class);
		startActivity(i);
	}

	private void CreateDiet() {
		Intent i = new Intent(ViewProfileActivity.this,
				CreateDietActivity.class);
		startActivity(i);
	}

	private void DietList() {
		Intent i = new Intent(ViewProfileActivity.this, DietListActivity.class);
		startActivity(i);
	}
}
