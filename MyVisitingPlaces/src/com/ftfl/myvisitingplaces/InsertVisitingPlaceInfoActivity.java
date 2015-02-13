package com.ftfl.myvisitingplaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ftfl.myvisitingplaces.helper.VisitingPlacesDataSource;
import com.ftfl.myvisitingplaces.model.VisitingPlace;
import com.ftfl.myvisitingplaces.util.GPSTracker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class InsertVisitingPlaceInfoActivity<MainActivity> extends Activity {
	static final int CAMERA_REQUEST = 1;
	String mImagePath = null;
	static final String IMAGE_DIRECTORY_NAME = "VisitingPlace Images";
	File image = null;
	ImageView mIvPhotoView = null;
	EditText mEtDate = null;
	EditText mEtTime = null;
	EditText mEtLatitude = null;
	EditText mEtLongitude = null;
	EditText mEtDescription = null;
	EditText mTvPlaceTitle = null;
	EditText mEtName = null;
	EditText mEtEmail = null;
	EditText mEtPhone = null;
	Button mBtnInsert;
	Button mBtnTakePhoto;
	String mDate = "";
	String mTime = "";
	String mLatitude = "";
	String mLongitude = "";
	String mDescription = "";
	String mName="";
	String mEmail="";
	String mPhone="";
	VisitingPlace mPlace = null;
	String mID = "";
	VisitingPlacesDataSource mDSPlaces = null;
	LocationManager lm;
	String provider;
	Location l;
	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visiting_places_info);

		// Create global configuration and initialize ImageLoader with this
		// config
		// Create default options which will be used for every
		// displayImage(...) call if no options will be passed to this method
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();
		ImageLoader.getInstance().init(config);

		mEtLatitude = (EditText) this.findViewById(R.id.etLatitude);
		mEtLongitude = (EditText) this.findViewById(R.id.etLongitude);
		mEtDescription = (EditText) this.findViewById(R.id.etDescription);
		mBtnInsert = (Button) this.findViewById(R.id.btnInsert);
		mIvPhotoView = (ImageView) this.findViewById(R.id.ivTakePhoto);
		mBtnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
		mEtName = (EditText) findViewById(R.id.etPlaceName);
		mEtEmail = (EditText) findViewById(R.id.etPlaceEmail);
		mEtPhone = (EditText) findViewById(R.id.etPlacePhone);

		// create class object
		gps = new GPSTracker(InsertVisitingPlaceInfoActivity.this);
		// check if GPS enabled
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			// \n is for new line

			mEtLatitude.setText(String.valueOf(latitude));
			mEtLongitude.setText(String.valueOf(longitude));
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
	}

	public void insertData(View view) {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int month = today.month + 1;
		mDate = today.monthDay + "/" + month + "/" + today.year;
		mTime = today.format("%k:%M");
		mLatitude = mEtLatitude.getText().toString();
		mLongitude = mEtLongitude.getText().toString();
		mDescription = mEtDescription.getText().toString();
		mName = mEtName.getText().toString();
		mEmail = mEtEmail.getText().toString();
		mPhone = mEtPhone.getText().toString();
		mPlace = new VisitingPlace(mDate, mTime, mLatitude, mLongitude,
				mDescription, mImagePath,mName,mEmail,mPhone);
		mDSPlaces = new VisitingPlacesDataSource(this);
		if (mDSPlaces.insert(mPlace) == true) {
			Toast toast = Toast.makeText(this, "Successfully Saved.",
					Toast.LENGTH_LONG);
			toast.show();

			startActivity(new Intent(getApplicationContext(),
					HomeScreenActivity.class));
			finish();

		} else {
			Toast toast = Toast.makeText(this,
					"Error, Couldn't inserted data to database",
					Toast.LENGTH_LONG);
			toast.show();

		}
	}

	/**
	 * open camera method
	 */
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
				startActivityForResult(takePictureIntent, CAMERA_REQUEST);
			}
		}
	}

	private File createImageFile() throws IOException {

		if (image == null) {
			// External SD card location
			File mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					IMAGE_DIRECTORY_NAME);

			// Create the storage directory if it does not exist
			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs()) {
					return null;
				}
			}

			// Create an image file name
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
					Locale.getDefault()).format(new Date());
			String imageFileName = "JPEG_" + timeStamp + "_";
			image = File.createTempFile(imageFileName, /* prefix */
					".jpg", /* suffix */
					mediaStorageDir /* directory */
			);
		}

		mImagePath = image.getAbsolutePath();
		return image;
	}

	/**
	 * On activity result
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			if (mImagePath != null) {

				mBtnTakePhoto.setText("Change Image");

				Bitmap correctBmp = null;

				try {
					File f = new File(mImagePath);
					ExifInterface exif = new ExifInterface(f.getPath());
					int orientation = exif.getAttributeInt(
							ExifInterface.TAG_ORIENTATION,
							ExifInterface.ORIENTATION_NORMAL);

					int angle = 0;

					if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
						angle = 90;
					} else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
						angle = 180;
					} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
						angle = 270;
					}

					Matrix mat = new Matrix();
					mat.postRotate(angle);
					BitmapFactory.Options option = new BitmapFactory.Options();
					option.inSampleSize = 8;
					Bitmap bmp1 = BitmapFactory.decodeStream(
							new FileInputStream(f), null, option);
					correctBmp = Bitmap.createBitmap(bmp1, 0, 0,
							bmp1.getWidth(), bmp1.getHeight(), mat, true);
				} catch (IOException e) {
					Log.w("TAG", "-- Error in setting image");
				} catch (OutOfMemoryError oom) {
					Log.w("TAG", "-- OOM Error in setting image");
				}

				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.displayer(new RoundedBitmapDisplayer(0))
						.cacheInMemory(true).cacheOnDisk(true).build();
				ImageLoader.getInstance().displayImage("file:///" + mImagePath,
						mIvPhotoView, options);
				// mIvPhotoView.setImageBitmap(correctBmp);
			}

		}
	}

}
