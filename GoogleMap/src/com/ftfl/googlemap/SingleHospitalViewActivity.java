package com.ftfl.googlemap;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ftfl.googlemap.Adapter.Customadapter;
import com.ftfl.googlemap.helper.HospitalDataSource;
import com.ftfl.googlemap.helper.SQLiteHelper;
import com.ftfl.googlemap.model.Hospital;

public class SingleHospitalViewActivity extends Activity {

	SQLiteHelper mDBHelper;
	HospitalDataSource mHospitalDataSource;
	ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_hospital_view);
		

		mDBHelper = new SQLiteHelper(this);
		mHospitalDataSource = new HospitalDataSource(this);
		List<Hospital> hospital_list = mHospitalDataSource.singleHospitalInfo();
		Customadapter arrayAdapter = new Customadapter(this, hospital_list);
		mListView = (ListView) findViewById(R.id.lvHospitalList);
		mListView.setAdapter(arrayAdapter);
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
