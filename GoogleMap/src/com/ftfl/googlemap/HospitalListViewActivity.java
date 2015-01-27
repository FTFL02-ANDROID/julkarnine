package com.ftfl.googlemap;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.googlemap.Adapter.Customadapter;
import com.ftfl.googlemap.helper.HospitalDataSource;
import com.ftfl.googlemap.helper.SQLiteHelper;
import com.ftfl.googlemap.model.Hospital;

public class HospitalListViewActivity extends Activity {

	SQLiteHelper mDBHelper;
	HospitalDataSource mHospitalDataSource;
	ListView mListView;
	TextView mId_tv = null;
	TextView mLatitude_tv = null;
	TextView mLongitude_tv = null;
	String mId = "";
	String mLatitude ="";
	String mLongitude="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital_list_view);

		mDBHelper = new SQLiteHelper(this);
		mHospitalDataSource = new HospitalDataSource(this);
		List<Hospital> hospital_list = mHospitalDataSource.allHospitals();
		Customadapter arrayAdapter = new Customadapter(this, hospital_list);
		mListView = (ListView) findViewById(R.id.lvHospitalList);
		mListView.setAdapter(arrayAdapter);

		final String[] option = new String[] { "Details View", "Show Map" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Select Option");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.e("Selected Item", String.valueOf(which));
				if (which == 0) {
					detailsView(mId);
				}
				if (which == 1) {
					showMap(mId,mLatitude,mLongitude);
				}
			}
		});
		final AlertDialog dialog = builder.create();

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mId_tv = (TextView) view.findViewById(R.id.tvHospitalId);
				mId = mId_tv.getText().toString(); 
				
				mLatitude_tv = (TextView) view.findViewById(R.id.tvHospitalLatitude);
				mLatitude = mLatitude_tv.getText().toString(); 
				
				mLongitude_tv = (TextView) view.findViewById(R.id.tvHospitalLongitude);
				mLongitude = mLongitude_tv.getText().toString(); 
				
				dialog.show();
			}
		});
	}

	public void detailsView(String eId) {
		Intent mEditIntent = new Intent(getApplicationContext(),
				SingleHospitalViewActivity.class);
		mEditIntent.putExtra("activityId", eId);
		// startActivity(mEditIntent);
		startActivityForResult(mEditIntent, 2);
	}

	public void showMap(String eId ,String mLatitude, String mLongitude) {
//		Intent mEditIntent = new Intent(getApplicationContext(),
//				DiplayHospitalMapActivity.class);
//		mEditIntent.putExtra("activityId", eId);
//		startActivityForResult(mEditIntent, 2);
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:0,0?q=" + (""+ mLatitude+","+mLongitude+"")));
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
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
