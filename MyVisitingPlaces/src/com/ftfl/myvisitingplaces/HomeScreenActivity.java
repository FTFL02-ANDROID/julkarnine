package com.ftfl.myvisitingplaces;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeScreenActivity extends Activity {

	Button mBtnRegisterLocation;
	Button mBtnRetrieveLocation;
	private InterstitialAd interstitial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

		// Prepare the Interstitial Ad
		interstitial = new InterstitialAd(HomeScreenActivity.this);
		// Insert the Ad Unit ID
		interstitial.setAdUnitId("ca-app-pub-3751200786676647/8935736817");

		// Locate the Banner Ad in activity_main.xml
		AdView adView = (AdView) this.findViewById(R.id.adView);

		// Request for Ads
		AdRequest adRequest = new AdRequest.Builder()

				// Add a test device to show Test Ads
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("ABC")
				.build();

		// Load ads into Banner Ads
		adView.loadAd(adRequest);

		// Load ads into Interstitial Ads
		interstitial.loadAd(adRequest);

		// Prepare an Interstitial Ad Listener
		interstitial.setAdListener(new AdListener() {
			public void onAdLoaded() {
				// Call displayInterstitial() function
				displayInterstitial();
			}
		});

		mBtnRegisterLocation = (Button) findViewById(R.id.btnRegister);
		mBtnRetrieveLocation = (Button) findViewById(R.id.btnRetrieve);

		mBtnRegisterLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						InsertVisitingPlaceInfoActivity.class);
				startActivity(i);
				finish();

			}
		});

		mBtnRetrieveLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						VisitingPlacesListViewActivity.class);
				startActivity(i);
				finish();

			}
		});

	}

	public void displayInterstitial() {
		// If Ads are loaded, show Interstitial else show nothing.
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}

}
