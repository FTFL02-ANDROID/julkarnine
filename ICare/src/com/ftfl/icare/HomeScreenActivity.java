package com.ftfl.icare;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icare.adapter.CustomDrawerAdapter;
import com.ftfl.icare.adapter.CustomProfileAdapter;
import com.ftfl.icare.helper.ICareProfileDataSource;
import com.ftfl.icare.model.ICareProfile;
import com.ftfl.icare.util.DrawerItem;
import com.ftfl.icare.util.FragmentAbout;
import com.ftfl.icare.util.FragmentAddAppointmentInfo;
import com.ftfl.icare.util.FragmentAddCareCenter;
import com.ftfl.icare.util.FragmentAddDiet;
import com.ftfl.icare.util.FragmentAddDoctor;
import com.ftfl.icare.util.FragmentAddMadication;
import com.ftfl.icare.util.FragmentAddMedicalHistory;
import com.ftfl.icare.util.FragmentAddNote;
import com.ftfl.icare.util.FragmentAddVaccination;
import com.ftfl.icare.util.FragmentHome;
import com.ftfl.icare.util.FragmentViewGeneralDiet;
import com.ftfl.icare.util.FragmentViewGeneralGrowth;
import com.ftfl.icare.util.FragmentViewGeneralVaccination;
import com.ftfl.icare.util.ICareConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class HomeScreenActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	FragmentManager mFrgManager;
	Fragment mFragment;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter mAdapter;
	Bundle args = new Bundle();
	List<DrawerItem> dataList;

	TextView mId_tv = null;
	ICareProfileDataSource mProfileDataSource;
	ICareProfile mIProfile;

	ListView mlvProfileList;
	List<ICareProfile> mICareProfilesList;
	String mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		centerActionBarTitle();
		getOverflowMenu();
		
		
		mProfileDataSource = new ICareProfileDataSource(this);
		mICareProfilesList = mProfileDataSource.iCareProfilesList();
		
		if(mICareProfilesList.size() == 1){
			
			
			share(mICareProfilesList.get(0).getID());
			
		}

		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		dataList.add(new DrawerItem("Home", R.drawable.home));
		dataList.add(new DrawerItem("View Profile ", R.drawable.myprofile));
		dataList.add(new DrawerItem("Profile List", R.drawable.userlist));
		dataList.add(new DrawerItem("Add Profile", R.drawable.adduser));

		dataList.add(new DrawerItem("________________________________"));

		dataList.add(new DrawerItem("Add Diet", R.drawable.plus));
		dataList.add(new DrawerItem("Add Doctor", R.drawable.plus));
		dataList.add(new DrawerItem("Add Appointment", R.drawable.plus));
		dataList.add(new DrawerItem("Add Madication", R.drawable.plus));
		dataList.add(new DrawerItem("Add Madical History", R.drawable.plus));
		dataList.add(new DrawerItem("Add Care Centre", R.drawable.plus));
		dataList.add(new DrawerItem("Add Vaccination", R.drawable.plus));
		dataList.add(new DrawerItem("Add Notes", R.drawable.plus));

		dataList.add(new DrawerItem("________________________________"));

		dataList.add(new DrawerItem("Growth", R.drawable.drawergrowth));
		dataList.add(new DrawerItem("Vaccination", R.drawable.drawerinjection));
		dataList.add(new DrawerItem("Diet/Nutrition", R.drawable.drawerdiet));

		dataList.add(new DrawerItem("_________________________________"));

		dataList.add(new DrawerItem("About", R.drawable.drawerabout));

		mAdapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(mAdapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.menu, R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			
			mProfileDataSource = new ICareProfileDataSource(this);
			mICareProfilesList = mProfileDataSource.iCareProfilesList();
			
			if(mICareProfilesList.size() == 0){
				SelectItem(3);
				SharedPreferences settings = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
				settings.edit().clear().commit();
				
				
			}
			else if(mICareProfilesList.size() == 1){
				
				SelectItem(0);
				share(mICareProfilesList.get(0).getID());
				
			}else{
				
				SelectItem(0);
				dialogOnStart();
			}
			
		}

	}

	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // Handle the back button
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// // Ask the user if they want to quit
	// new AlertDialog.Builder(this)
	// .setIcon(android.R.drawable.ic_dialog_alert)
	// .setTitle(R.string.quit)
	// .setMessage(R.string.really_quit)
	// .setPositiveButton(R.string.yes,
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	//
	// // Stop the activity
	// HomeScreenActivity.this.finish();
	// }
	//
	// }).setNegativeButton(R.string.no, null).show();
	//
	// return true;
	// } else {
	// return super.onKeyDown(keyCode, event);
	// }
	//
	// }

	private void centerActionBarTitle() {

		int titleId = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			titleId = getResources().getIdentifier("action_bar_title", "id",
					"android");
		} else {
			// This is the id is from your app's generated R class when
			// ActionBarActivity is used
			// for SupportActionBar
			titleId = R.id.action_bar_title;
		}

		// Final check for non-zero invalid id
		if (titleId > 0) {
			TextView titleTextView = (TextView) findViewById(titleId);

			DisplayMetrics metrics = getResources().getDisplayMetrics();

			// Fetch layout parameters of titleTextView
			// (LinearLayout.LayoutParams : Info from HierarchyViewer)
			LinearLayout.LayoutParams txvPars = (android.widget.LinearLayout.LayoutParams) titleTextView
					.getLayoutParams();
			txvPars.gravity = Gravity.CENTER_HORIZONTAL;
			txvPars.width = metrics.widthPixels;
			titleTextView.setLayoutParams(txvPars);

			titleTextView.setGravity(Gravity.CENTER);
		}
	}

	public void SelectItem(int possition) {

		switch (possition) {

		case 0:
			mFragment = new FragmentHome();

			break;

		case 1:
			mFragment = new FragmentViewProfile();
			break;

		case 2:
			mFragment = new FragmentViewProfileList();
			break;
		case 3:
			mFragment = new FragmentAddProfile();
			break;

		case 5:
			mFragment = new FragmentAddDiet();

			break;

		case 6:
			mFragment = new FragmentAddDoctor();
			break;
		case 7:
			mFragment = new FragmentAddAppointmentInfo();
			break;
		case 8:
			mFragment = new FragmentAddMadication();
			break;
		case 9:
			mFragment = new FragmentAddMedicalHistory();
			break;
		case 10:
			mFragment = new FragmentAddCareCenter();
			break;
		case 11:
			mFragment = new FragmentAddVaccination();
			break;
		case 12:
			mFragment = new FragmentAddNote();
			break;
		case 14:
			mFragment = new FragmentViewGeneralGrowth();
			break;

		case 15:
			mFragment = new FragmentViewGeneralVaccination();
			break;
		case 16:
			mFragment = new FragmentViewGeneralDiet();
			break;

		case 18:
			mFragment = new FragmentAbout();
			break;

		default:
			break;
		}

		mFragment.setArguments(args);
		mFrgManager = getFragmentManager();
		mFrgManager.beginTransaction().replace(R.id.content_frame, mFragment)
				.addToBackStack(null).commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	public void dialogOnStart() {

		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.fragment_layout_view_profile_list);
		dialog.setTitle("Select Profile");

		// Create global configuration and initialize ImageLoader with this
		// config
		// Create default options which will be used for every
		// displayImage(...) call if no options will be passed to this method
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(defaultOptions).build();
		ImageLoader.getInstance().init(config);

		mProfileDataSource = new ICareProfileDataSource(this);
		mICareProfilesList = mProfileDataSource.iCareProfilesList();
		CustomProfileAdapter arrayAdapter = new CustomProfileAdapter(this,
				mICareProfilesList);
		mlvProfileList = (ListView) dialog.findViewById(R.id.lvProfileList);
		mlvProfileList.setAdapter(arrayAdapter);

		mlvProfileList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						mId_tv = (TextView) view.findViewById(R.id.tvID);
						mId = mId_tv.getText().toString();

						share(mICareProfilesList.get(position).getID());

						dialog.dismiss();
					}
				});

		dialog.show();
	}

	public void share(String position) {

		SharedPreferences preferences = getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("profile_id", position);
		editor.apply();
		
		ICareConstants.SELECTED_PROFILE_ID = Integer.parseInt(position);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (dataList.get(position).getTitle() == null) {
				SelectItem(position);
			}

		}
	}

	private void getOverflowMenu() {

		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

}
