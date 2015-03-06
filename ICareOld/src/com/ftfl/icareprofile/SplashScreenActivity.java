package com.ftfl.icareprofile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends Activity {

	// private Context context=this; Now,we can write context instead of
	// FTFLICareSplashActivity.this

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		Thread background = new Thread() {
			public void run() {

				try {
					// Thread will sleep for 3 seconds
					sleep(1 * 1000);

					// After 5 seconds redirect to another intent
					// Intent i=new
					// Intent(getBaseContext(),FTFLICareProfileActivity.class);

					// Bellow code will do the same thing....

					Intent i = new Intent(SplashScreenActivity.this,
							DietListActivity.class);
					startActivity(i);
					// Remove activity
					finish(); // so that, it will not get back in the previous
								// file.

				} catch (Exception e) {

				}
			}
		};

		// start thread
		background.start();
	}

}
