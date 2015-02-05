package com.ftfl.registerlocation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ftfl.registerlocation.helper.LocationDataSource;
import com.ftfl.registerlocation.model.LocationModel;

public class InsertLocationInfoActivity<MainActivity> extends Activity  {
	static final int REQUEST_IMAGE_CAPTURE = 1;
	ImageView mIvPhotoView = null;
	String mCurrentPhotoPath = "";
	EditText mEtDate = null;
	EditText mEtTime = null;
	EditText mEtLatitude = null;
	EditText mEtLongitude = null;
	EditText mEtDescription = null;
	EditText mTvHospitalTitle = null;
	Button mBtnInsert;
	String mDate = "";
	String mTime = "";
	String mLatitude = "";
	String mLongitude = "";
	String mDescription = "";
	byte[] mImage = null;
	LocationModel mHospital = null;
	String mID = "";
	LocationDataSource mHospitalDS = null;
	LocationManager lm;

	String provider;
	Location l;
	// GPSTracker class
    GPSTracker gps; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_hostpital_info);

		
		mEtLatitude = (EditText) this.findViewById(R.id.etLatitude);
		mEtLongitude = (EditText) this.findViewById(R.id.etLongitude);
		mEtDescription = (EditText) this.findViewById(R.id.etDescription);
		mBtnInsert = (Button) this.findViewById(R.id.btnInsert);
		mIvPhotoView = (ImageView) this.findViewById(R.id.ivTakePhoto);

	
		
		 // create class object
        gps = new GPSTracker(InsertLocationInfoActivity.this);
        // check if GPS enabled       
        if(gps.canGetLocation()){                  
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();                   
            // \n is for new line
          
            mEtLatitude.setText(String.valueOf(latitude));
            mEtLongitude.setText(String.valueOf(longitude));
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }    
	}

	

	public void insertData(View view) {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();

		mDate = today.monthDay + "/" + today.month + "/" + today.year;
		mTime = today.format("%k:%M:%S");
		mLatitude = mEtLatitude.getText().toString();
		mLongitude = mEtLongitude.getText().toString();
		mDescription = mEtDescription.getText().toString();
		mHospital = new LocationModel(mDate, mTime, mLatitude, mLongitude,
				mDescription, mImage);
		mHospitalDS = new LocationDataSource(this);
		if (mHospitalDS.insert(mHospital) == true) {
			Toast toast = Toast.makeText(this, "Successfully Saved.",
					Toast.LENGTH_LONG);
			toast.show();

			startActivity(new Intent(getApplicationContext(),
					HomeScreenActivity.class));

		} else {
			Toast toast = Toast.makeText(this,
					"Error, Couldn't inserted data to database",
					Toast.LENGTH_LONG);
			toast.show();

		}
	}

	public void dispatchTakePictureIntent(View v) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT)
						.show();
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			setPic();
			galleryAddPic();
		}
	}

	/**
	 * If user wants to load photo into gallery
	 */
	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	@SuppressWarnings("deprecation")
	private void setPic() {
		// Get the dimensions of the View
		int targetW = mIvPhotoView.getWidth();
		int targetH = mIvPhotoView.getHeight();

		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);// bmOptions
		mIvPhotoView.setImageBitmap(bitmap);
		mImage = getBytes(bitmap);
	}

	// convert from bitmap to byte array
	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, stream);
		return stream.toByteArray();
	}

	// convert from byte array to bitmap
	public static Bitmap getImage(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents

		/**************************
		 * |---- You will get the image location from this variable which you
		 * will save into database
		 */
		mCurrentPhotoPath = image.getAbsolutePath();
		return image;
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
					LocationListViewActivity.class));
			return true;
		case R.id.action_insertInfo:
			startActivity(new Intent(getApplicationContext(),
					InsertLocationInfoActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	

}
