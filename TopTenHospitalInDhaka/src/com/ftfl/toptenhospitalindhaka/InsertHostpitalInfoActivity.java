package com.ftfl.toptenhospitalindhaka;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ftfl.toptenhospitalindhaka.helper.HospitalDataSource;
import com.ftfl.toptenhospitalindhaka.model.Hospital;

public class InsertHostpitalInfoActivity extends Activity {
	static final int REQUEST_IMAGE_CAPTURE = 1;
	ImageView mIvPhotoView = null;
	String mCurrentPhotoPath = "";
	EditText mEtName = null;
	EditText mEtAddress = null;
	EditText mEtLatitude = null;
	EditText mEtLongitude = null;
	EditText mEtDescription = null;
	EditText mTvHospitalTitle = null;
	Button mBtnInsert;
	String mName = "";
	String mAddress = "";
	String mLatitude = "";
	String mLongitude = "";
	String mDescription = "";
	byte[] mImage = null;
	Hospital mHospital = null;
	String mID = "";
	HospitalDataSource mHospitalDS = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_hostpital_info);

		mTvHospitalTitle = (EditText) this.findViewById(R.id.tvHospitalTitle);
		mEtName = (EditText) this.findViewById(R.id.etName);
		mEtAddress = (EditText) this.findViewById(R.id.etAddress);
		mEtLatitude = (EditText) this.findViewById(R.id.etLatitude);
		mEtLongitude = (EditText) this.findViewById(R.id.etLongitude);
		mEtDescription = (EditText) this.findViewById(R.id.etDescription);
		mBtnInsert = (Button) this.findViewById(R.id.btnInsert);
		mIvPhotoView = (ImageView) this.findViewById(R.id.ivTakePhoto);

		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("id");

		if (mID != null) {

			Long mActivityId = Long.parseLong(mID);
			mHospitalDS = new HospitalDataSource(this);
			mHospital = mHospitalDS.updateActivityData(mActivityId);

			mName = mHospital.getHospitalName();
			mAddress = mHospital.getHospitalAddress();
			mLatitude = mHospital.getHospitalLatitude();
			mLongitude = mHospital.getHospitalLongitude();
			mImage = mHospital.getHospitalImage();
			mDescription = mHospital.getHospitalDescription();

			mTvHospitalTitle.setText(mName);
			mEtName.setText(mName);
			mEtAddress.setText(mAddress);
			mEtLatitude.setText(mLatitude);
			mEtLongitude.setText(mLongitude);
			mEtDescription.setText(mDescription);
			mIvPhotoView.setImageBitmap(getImage(mImage));
			mBtnInsert.setText("Update");

		}
	}

	
	
	public void insertData(View view) {

		mName = mEtName.getText().toString();
		mAddress = mEtAddress.getText().toString();
		mLatitude = mEtLatitude.getText().toString();
		mLongitude = mEtLongitude.getText().toString();
		mDescription = mEtDescription.getText().toString();
		mHospital = new Hospital(mName, mAddress, mLatitude, mLongitude,
				mDescription, mImage);
		mHospitalDS = new HospitalDataSource(this);
		if (mHospitalDS.insert(mHospital) == true) {
			Toast toast = Toast.makeText(this, "Successfully Saved.",
					Toast.LENGTH_LONG);
			toast.show();

			startActivity(new Intent(getApplicationContext(),
					HospitalListViewActivity.class));

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
