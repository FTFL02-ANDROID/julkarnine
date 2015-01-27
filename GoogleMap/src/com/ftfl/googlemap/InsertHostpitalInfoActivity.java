package com.ftfl.googlemap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.googlemap.helper.HospitalDataSource;
import com.ftfl.googlemap.model.Hospital;

public class InsertHostpitalInfoActivity extends Activity {
	EditText etName;
	EditText etAddress;
	EditText etLatitude;
	EditText etLongitude;
	EditText etDescription;
	Button btnInsert;
	String mName;
	String mAddress;
	String mLatitude;
	String mLongitude;
	String mDescription;
	Hospital hospitalObj;
	HospitalDataSource hospitalDS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_hostpital_info);

		initialize();
	}

	public void initialize() {

		etName = (EditText) findViewById(R.id.etName);
		etAddress = (EditText) findViewById(R.id.etAddress);
		etLatitude = (EditText) findViewById(R.id.etLatitude);
		etLongitude = (EditText) findViewById(R.id.etLongitude);
		etDescription = (EditText) findViewById(R.id.etDescription);
		btnInsert = (Button) findViewById(R.id.btnInsert);

	}

	public void insertData(View view) {

		mName = etName.getText().toString();
		mAddress = etAddress.getText().toString();
		mLatitude = etLatitude.getText().toString();
		mLongitude = etLongitude.getText().toString();
		mDescription = etDescription.getText().toString();

		hospitalObj = new Hospital(mName, mAddress, mLatitude, mLongitude,
				mDescription);
		hospitalDS = new HospitalDataSource(this);
		if (hospitalDS.insert(hospitalObj) == true) {
			Toast toast = Toast.makeText(this, "Successfully Saved.",
					Toast.LENGTH_LONG);
			toast.show();

			startActivity(new Intent(getApplicationContext(),
					HospitalListViewActivity.class));

		} else {
			Toast toast = Toast.makeText(this,
					"Error, Couldn't inserted data to database",
					Toast.LENGTH_LONG);
			toast.show();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_hospitalList:
			startActivity(new Intent(getApplicationContext(),
					HospitalListViewActivity.class));
			return true;
		case R.id.action_insertInfo:
			startActivity(new Intent(getApplicationContext(),
					InsertHostpitalInfoActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
