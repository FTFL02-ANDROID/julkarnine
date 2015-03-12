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
import com.ftfl.icare.helper.ICareProfileDataSource;
import com.ftfl.icare.helper.MedicalHistoryDataSource;
import com.ftfl.icare.model.ICareProfile;
import com.ftfl.icare.model.MedicalHistory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FragmentAddMedicalHistory extends Fragment {

	static final int CAMERA_REQUEST = 1;
	String mImagePath = null;
	static final String IMAGE_DIRECTORY_NAME = "Icare Images";
	File mImage = null;
	ImageView mIvPrescription;
	Button mBtnImagePrescription;
	EditText mEtDrName;
	EditText mEtDate;
	EditText mEtPurpose;
	EditText mEtSuggestion;
	Button mBtnSave;
	Spinner mSpGender;
	ArrayAdapter<CharSequence> mAdapter;
	MedicalHistoryDataSource mMedicalHistoryDataSource;
	MedicalHistory mMedicalHistory;
	FragmentManager mFrgManager;
	Fragment mFragment;
	Context mContext;
	String mID = "1";
	Bundle args = new Bundle();
	private SetDateOnClickDialog mDatePickerObj;
	private setTimeOnclickDialog mTimePickerObj;
	List<MedicalHistory> mMedicalHistoryList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(
				R.layout.fragment_layout_add_medical_history, container, false);

		mContext = container.getContext();

		// Create global configuration and initialize ImageLoader with this
		// config
		// Create default options which will be used for every
		// displayImage(...) call if no options will be passed to this method
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				mContext).defaultDisplayImageOptions(defaultOptions).build();
		ImageLoader.getInstance().init(config);

		mIvPrescription = (ImageView) view.findViewById(R.id.ivPrescription);
		mBtnImagePrescription = (Button) view
				.findViewById(R.id.btnImagePrescription);
		mEtDrName = (EditText) view.findViewById(R.id.etDrName);
		mEtDate = (EditText) view.findViewById(R.id.etDate);
		mEtPurpose = (EditText) view.findViewById(R.id.etPurpose);
		mEtSuggestion = (EditText) view.findViewById(R.id.etSuggestion);
		mBtnSave = (Button) view.findViewById(R.id.btnSave);

		mDatePickerObj = new SetDateOnClickDialog(mEtDate, mContext);

		mBtnImagePrescription.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dispatchTakePictureIntent(v);

			}
		});

		mBtnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				createMedialHistory(v);

			}
		});

		return view;

	}

	public void setSpinner() {

		mAdapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.genderarray, android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpGender.setAdapter(mAdapter);
	}

	public void createMedialHistory(View view) {

		String mDoctorName = mEtDrName.getText().toString();
		String mDate = mEtDate.getText().toString();
		String mPurpose = mEtPurpose.getText().toString();
		String mSuggestion = mEtSuggestion.getText().toString();
		

		SharedPreferences prfs = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		String mProfileId = prfs.getString("profile_id", "");

		mMedicalHistory = new MedicalHistory();
		mMedicalHistory.setDoctorName(mDoctorName);
		mMedicalHistory.setDate(mDate);
		mMedicalHistory.setPurpose(mPurpose);
		mMedicalHistory.setSuggestion(mSuggestion);
		mMedicalHistory.setPrescription(mImagePath);
		mMedicalHistory.setProfileId(mProfileId);

		mMedicalHistoryDataSource = new MedicalHistoryDataSource(getActivity());
		if (mMedicalHistoryDataSource.insert(mMedicalHistory) == true) {
			Toast toast = Toast.makeText(getActivity(), "Successfully Saved.",
					Toast.LENGTH_LONG);
			toast.show();
			mFragment = new FragmentHome();
			mFrgManager = getFragmentManager();
			mFrgManager.beginTransaction().replace(R.id.content_frame, mFragment)
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
		if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT)
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

		if (mImage == null) {
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
			mImage = File.createTempFile(imageFileName, /* prefix */
					".jpg", /* suffix */
					mediaStorageDir /* directory */
			);
		}

		mImagePath = mImage.getAbsolutePath();
		return mImage;
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
						mIvPrescription, options);
				// mIvPhotoView.setImageBitmap(correctBmp);

			}

		}
	}

	public void setTitle(CharSequence title) {
		getActivity().getActionBar().setTitle(title);
	}

}