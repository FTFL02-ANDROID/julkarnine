package com.ftfl.icare.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ftfl.icare.R;
import com.ftfl.icare.helper.CareCenterDataSource;
import com.ftfl.icare.model.CareCenter;
import com.ftfl.icare.model.MedicalHistory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FragmentAddCareCenter extends Fragment {
	
	
	static final int CAMERA_REQUEST = 1;
	String mImagePath = null;
	static final String IMAGE_DIRECTORY_NAME = "Icare Images";
	File image = null;
	ImageView ivCareCenterPhoto;
	Button btnCareCenterPhoto;
	EditText etName;
	EditText etLocation;
	Button btnSave;
	Spinner spGender;
	ArrayAdapter<CharSequence> adapter;
	CareCenterDataSource mCareCenterDataSource;
	CareCenter mCareCenter;
	FragmentManager frgManager;
	Fragment fragment;
	Context thiscontext;
	String mID = "1";
	Bundle args = new Bundle();
	private SetDateOnClickDialog datePickerObj;
	private setTimeOnclickDialog timePickerObj;
	List<MedicalHistory> mMedicalHistoryList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_add_care_center,
				container, false);

		thiscontext = container.getContext();

		// Create global configuration and initialize ImageLoader with this
		// config
		// Create default options which will be used for every
		// displayImage(...) call if no options will be passed to this method
		
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				thiscontext).defaultDisplayImageOptions(defaultOptions).build();
		ImageLoader.getInstance().init(config);

		ivCareCenterPhoto = (ImageView) view
				.findViewById(R.id.ivCareCenterPhoto);
		btnCareCenterPhoto = (Button) view
				.findViewById(R.id.btnCareCenterPhoto);
		etName = (EditText) view.findViewById(R.id.etName);
		etLocation = (EditText) view.findViewById(R.id.etLocation);
		btnSave = (Button) view.findViewById(R.id.btnSave);

		

		btnCareCenterPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dispatchTakePictureIntent(v);

			}
		});

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				createMedialHistory(v);

			}
		});

		return view;

	}

	public void setSpinner() {

		adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.genderarray, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spGender.setAdapter(adapter);
	}

	public void createMedialHistory(View view) {

		String mName = etName.getText().toString();
		String mLocation = etLocation.getText().toString();
		

		SharedPreferences prfs = getActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		String mProfileId = prfs.getString("profile_id", "");

		mCareCenter = new CareCenter();
		mCareCenter.setName(mName);
		mCareCenter.setLocation(mLocation);
		mCareCenter.setImage(mImagePath);
		mCareCenter.setProfileId(mProfileId);

		mCareCenterDataSource = new CareCenterDataSource(getActivity());
		if (mCareCenterDataSource.insert(mCareCenter) == true) {
			Toast toast = Toast.makeText(getActivity(), "Successfully Saved.",
					Toast.LENGTH_LONG);
			toast.show();
			fragment = new FragmentHome();
			frgManager = getFragmentManager();
			frgManager.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();
			setTitle("Home");

		} else {
			Toast toast = Toast.makeText(getActivity(),
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
		if (takePictureIntent.resolveActivity(thiscontext.getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				Toast.makeText(thiscontext, ex.getMessage(), Toast.LENGTH_SHORT)
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST) {
			if (mImagePath != null) {

				// btnProfilePhoto.setText("Change Image");

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
						.displayer(new RoundedBitmapDisplayer(100))
						.cacheInMemory(true).cacheOnDisk(true).build();
				ImageLoader.getInstance().displayImage("file:///" + mImagePath,
						ivCareCenterPhoto, options);
				// mIvPhotoView.setImageBitmap(correctBmp);

			}

		}
	}

	public void setTitle(CharSequence title) {
		getActivity().getActionBar().setTitle(title);
	}

}