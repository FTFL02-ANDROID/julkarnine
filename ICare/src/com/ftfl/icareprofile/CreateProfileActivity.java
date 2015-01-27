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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ftfl.icareprofile.model.Profile;
import com.google.gson.Gson;

public class CreateProfileActivity extends Activity {

	Editor prefsEditor;
	private EditText etName;
	private EditText etFatherName;
	private EditText etMotherName;
	private DatePicker dpDOB;
	private EditText etHeight;
	private EditText etWeight;
	private EditText etEyeColor;
	private EditText etSpecialComment;
	private Button btnSubmit;
	private Button btnClear;
	SharedPreferences myPrefs;
	Profile pObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.icare_profile);
		initFields();
		initCallbacks();
	}

	private void initFields() {

		// initialize
		myPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
		etName = (EditText) findViewById(R.id.etName);
		etFatherName = (EditText) findViewById(R.id.etFatherName);
		etMotherName = (EditText) findViewById(R.id.etMotherName);
		dpDOB = (DatePicker) findViewById(R.id.dpDOB);
		etHeight = (EditText) findViewById(R.id.etHeight);
		etWeight = (EditText) findViewById(R.id.etWeight);
		etEyeColor = (EditText) findViewById(R.id.etEyeColor);
		etSpecialComment = (EditText) findViewById(R.id.etSpecialComment);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnClear = (Button) findViewById(R.id.btnClear);

		// Update existing data
		if (myPrefs.contains("myProfile")) {

			Gson gson = new Gson();
			String json = myPrefs.getString("myProfile", "");
			pObj = gson.fromJson(json, Profile.class);
			etName.setText(pObj.getName());
			etFatherName.setText(pObj.getFateherName());
			etMotherName.setText(pObj.getMotherName());
			String dateOfBirth = pObj.getDateOfBirth();
			String[] split = dateOfBirth.split("/");
			int day = Integer.valueOf(split[0]);
			int month = Integer.valueOf(split[1]);
			int year = Integer.valueOf(split[2]);
			dpDOB.updateDate(year, month - 1, day);
			etHeight.setText(pObj.getHeight());
			etWeight.setText(pObj.getWeight());
			etEyeColor.setText(pObj.getEyeColor());
			etSpecialComment.setText(pObj.getSpecialComment());

		}
	}

	private void initCallbacks() {

		// when user submit data
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				submit();
			}
		});

		// reset the value
		btnClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				etName.setText("");
				etFatherName.setText("");
				etMotherName.setText("");
				etHeight.setText("");
				etWeight.setText("");
				etEyeColor.setText("");
				etSpecialComment.setText("");
			}
		});
	}

	private void submit() {

		String name = etName.getText().toString();
		String fatherName = etFatherName.getText().toString();
		String motherName = etMotherName.getText().toString();

		int day = dpDOB.getDayOfMonth();
		int month = dpDOB.getMonth();
		int year = dpDOB.getYear();

		String dateOfBirth = day + "/" + (month + 1) + "/" + year;

		String weight = etWeight.getText().toString();
		String height = etHeight.getText().toString();
		String eyeColor = etEyeColor.getText().toString();
		String specialComment = etSpecialComment.getText().toString();

		Profile pObj = new Profile(name, fatherName, motherName, dateOfBirth,
				weight, height, eyeColor, specialComment);

		// saving data to shared preference
		Editor prefsEditor = myPrefs.edit();
		Gson gson = new Gson();
		String json = gson.toJson(pObj);
		prefsEditor.putString("myProfile", json);
		prefsEditor.commit();

		Intent i = new Intent(CreateProfileActivity.this,
				ViewProfileActivity.class);
		startActivity(i);

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

		Intent i = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(i);
	}

	private void ViewProfile() {
		Intent i = new Intent(CreateProfileActivity.this,
				ViewProfileActivity.class);
		startActivity(i);
	}

	private void CreateDiet() {
		Intent i = new Intent(CreateProfileActivity.this,
				CreateDietActivity.class);
		startActivity(i);
	}

	private void DietList() {
		Intent i = new Intent(CreateProfileActivity.this,
				DietListActivity.class);
		startActivity(i);
	}

}
