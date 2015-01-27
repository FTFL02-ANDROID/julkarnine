package com.ftfl.icareprofile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ftfl.icareprofile.Adapter.Customadapter;
import com.ftfl.icareprofile.helper.DietDataSource;
import com.ftfl.icareprofile.helper.SQLiteHelper;
import com.ftfl.icareprofile.model.DietChart;

public class DietListActivity extends Activity {

	private DietDataSource dietDS = null;
	Customadapter adapter;
	String eDate= "1/1/2015";
	ListView mlist;
	ArrayList<DietChart> array_list = new ArrayList<DietChart>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.diel_list_items);

		mlist = (ListView) findViewById(R.id.listView1);

		dietDS = new DietDataSource(this);
		ArrayList<DietChart> array_list = (ArrayList<DietChart>) dietDS.dailyDietChart(eDate);

		// setListAdapter(new ListAdapter(this, names, times, menus, date));

		adapter = new Customadapter(this, array_list);
		mlist.setAdapter(adapter);

		mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "Details",
						Toast.LENGTH_LONG).show();
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
		Intent i = new Intent(DietListActivity.this, ViewProfileActivity.class);
		startActivity(i);
	}

	private void CreateDiet() {
		Intent i = new Intent(DietListActivity.this, CreateDietActivity.class);
		startActivity(i);
	}

	private void DietList() {
		Intent i = new Intent(DietListActivity.this, DietListActivity.class);
		startActivity(i);
	}
}
