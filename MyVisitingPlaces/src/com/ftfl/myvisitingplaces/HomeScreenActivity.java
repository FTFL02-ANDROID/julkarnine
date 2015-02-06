package com.ftfl.myvisitingplaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeScreenActivity extends Activity {

	Button mBtnRegisterLocation;
	Button mBtnRetrieveLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

		mBtnRegisterLocation = (Button) findViewById(R.id.btnRegister);
		mBtnRetrieveLocation = (Button) findViewById(R.id.btnRetrieve);

		mBtnRegisterLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						InsertVisitingPlaceInfoActivity.class);
				startActivity(i);

			}
		});

		mBtnRetrieveLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						VisitingPlacesListViewActivity.class);
				startActivity(i);

			}
		});

	}

}
