package com.ftfl.icareprofile;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icareprofile.Adapter.Customadapter;
import com.ftfl.icareprofile.helper.DietDataSource;
import com.ftfl.icareprofile.helper.SQLiteHelper;
import com.ftfl.icareprofile.model.DietChart;

public class DailyDietViewActivity extends Activity {
	
	ListView mListView = null;
	DietDataSource mDietDataSource = null;
	SQLiteHelper mDBHelper = null;
	DietChart dailyDietChart = null;
	int id_To_Update = 0;
	String mDate = "";
	TextView textDate;
	String mId = "";
	TextView mId_tv  = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_diet_view);
		final String[] option = new String[] { "Edit Data", "Delete Data" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Select Option");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.e("Selected Item", String.valueOf(which));
				if (which == 0) {
					editData(mId);
				}
				if (which == 1) {
					deleteData(mId);
				}
			}
		});
		final AlertDialog dialog = builder.create();

		mDBHelper = new SQLiteHelper(this);
		mDietDataSource = new DietDataSource(this);
		textDate = (TextView) findViewById(R.id.textDailyDietChartDate);
		// mDate = textDate.getText().toString();

		Intent mIntent = getIntent();
		mDate = mIntent.getStringExtra("cDate");
		// mDate = "18/1/2015";

		textDate.setText(mDate);
		List<DietChart> allDailyDietChart = mDietDataSource
				.dailyDietChart(mDate);

		Customadapter arrayAdapter = new Customadapter(
				this, allDailyDietChart);

		// adding it to the list view.
		mListView = (ListView) findViewById(R.id.lvDailyDietChart);
		mListView.setAdapter(arrayAdapter);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mId_tv = (TextView) view.findViewById(R.id.viewId);
				mId = mId_tv.getText().toString(); // get id
				dialog.show();
			}
		});
	}

	public void editData(String eId) {
		Intent mEditIntent = new Intent(getApplicationContext(),
				CreateDietActivity.class);
		mEditIntent.putExtra("activityId", eId);
//		 startActivity(mEditIntent);
		startActivityForResult(mEditIntent, 2);
	}

	public void deleteData(String eId) {
		mDietDataSource = new DietDataSource(this);
		Long lId = Long.parseLong(eId);
		mDietDataSource.deleteData(lId);
		finish();
	}
	
}
