package com.ftfl.icare;

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
import com.ftfl.icare.model.ICareProfile;
import com.ftfl.icare.util.FragmentHome;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.SetDateOnClickDialog;
import com.ftfl.icare.util.setTimeOnclickDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FragmentAddProfile extends Fragment {

	static final int CAMERA_REQUEST = 1;
	String mImagePath = null;
	static final String IMAGE_DIRECTORY_NAME = "Icare Images";
	File image = null;
	ImageView mIvProfilePhoto;
	Button mBtnProfilePhoto;
	EditText mEtName;
	EditText mEtDateOfBirth;
	EditText mEtWeight;
	EditText mEtHeight;
	Spinner mSpGender;
	EditText mEtBloodGroup;
	Button mBtnSave;
	ArrayAdapter<CharSequence> mAdapter;
	ICareProfileDataSource mProfileDataSource;
	ICareProfile mIProfile;
	FragmentManager mfrgManager;
	Fragment mfragment;
	Context mContext;
	String mID = "1";
	Bundle args = new Bundle();
	private SetDateOnClickDialog mDatePickerObj;
	private setTimeOnclickDialog mTimePickerObj;
	List<ICareProfile> mIcareProfilesList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_add_profile,
				container, false);
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

		mIvProfilePhoto = (ImageView) view.findViewById(R.id.ivProfilePhoto);
		mBtnProfilePhoto = (Button) view.findViewById(R.id.btnProfilePhoto);
		mEtName = (EditText) view.findViewById(R.id.etName);
		mEtDateOfBirth = (EditText) view.findViewById(R.id.etDateOfBirth);
		mEtWeight = (EditText) view.findViewById(R.id.etWeight);
		mEtHeight = (EditText) view.findViewById(R.id.etHeight);
		mSpGender = (Spinner) view.findViewById(R.id.spGender);
		mEtBloodGroup = (EditText) view.findViewById(R.id.etBloodGroup);
		mBtnSave = (Button) view.findViewById(R.id.btnSave);

		mDatePickerObj = new SetDateOnClickDialog(mEtDateOfBirth, mContext);

		setSpinner();
		mBtnProfilePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dispatchTakePictureIntent(v);

			}
		});

		if (getArguments().getString("id") != null) {

			mBtnSave.setText("Update");
			mProfileDataSource = new ICareProfileDataSource(getActivity());
			mIProfile = mProfileDataSource.singleProfileData(Integer
					.parseInt(getArguments().getString("id")));

			mEtName.setText(mIProfile.getName().toString());
			mEtDateOfBirth.setText(mIProfile.getDateOfBirth().toString());
			mEtWeight.setText(mIProfile.getWeight().toString());
			mEtHeight.setText(mIProfile.getHeight().toString());
			String gender = mIProfile.getGender().toString();
			mImagePath = mIProfile.getImage().toString();
			mEtBloodGroup.setText(mIProfile.getBloodGroup().toString());

			for (int i = 0; i < mAdapter.getCount(); i++) {
				if (gender.trim().equals(mAdapter.getItem(i).toString())) {
					mSpGender.setSelection(i);
					break;
				}
			}

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.displayer(new RoundedBitmapDisplayer(0))
					.cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance().displayImage("file:///" + mImagePath,
					mIvProfilePhoto, options);

			mBtnSave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					updateProfile(v);

				}
			});

		} else {

			mBtnSave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					createProfile(v);

				}
			});

		}

		return view;
	}

	public void setSpinner() {

		mAdapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.genderarray, android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpGender.setAdapter(mAdapter);
	}

	public void updateProfile(View view) {

		String mName = mEtName.getText().toString();
		String mDob = mEtDateOfBirth.getText().toString();
		String mWeight = mEtWeight.getText().toString();
		String mHeight = mEtHeight.getText().toString();
		String mGender = mSpGender.getSelectedItem().toString();
		String mBloodGroup = mEtBloodGroup.getText().toString();

		mIProfile = new ICareProfile();
		mIProfile.setName(mName);
		mIProfile.setDateOfBirth(mDob);
		mIProfile.setWeight(mWeight);
		mIProfile.setHeight(mHeight);
		mIProfile.setGender(mGender);
		mIProfile.setBloodGroup(mBloodGroup);
		mIProfile.setImage(mImagePath);

		mProfileDataSource = new ICareProfileDataSource(getActivity());
		if (mProfileDataSource.updateData(
				Integer.parseInt(getArguments().getString("id")), mIProfile) == true) {
			Toast toast = Toast.makeText(getActivity(), "Successfully Updated.",
					Toast.LENGTH_LONG);
			toast.show();

			mfragment = new FragmentHome();
			mfrgManager = getFragmentManager();
			mfrgManager.beginTransaction().replace(R.id.content_frame, mfragment)
					.commit();
			setTitle("Home");

		} else {
			Toast toast = Toast.makeText(getActivity(),
					"Error, Couldn't inserted data to database",
					Toast.LENGTH_LONG);
			toast.show();

		}
	}
	
	public void share(String position) {

		SharedPreferences preferences = getActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("profile_id", position);
		editor.apply();
		
		ICareConstants.SELECTED_PROFILE_ID = Integer.parseInt(position);

	}

	public void createProfile(View view) {

		String mName = mEtName.getText().toString();
		String mDob = mEtDateOfBirth.getText().toString();
		String mWeight = mEtWeight.getText().toString();
		String mHeight = mEtHeight.getText().toString();
		String mGender = mSpGender.getSelectedItem().toString();
		String mBloodGroup = mEtBloodGroup.getText().toString();

		mIProfile = new ICareProfile();
		mIProfile.setName(mName);
		mIProfile.setDateOfBirth(mDob);
		mIProfile.setWeight(mWeight);
		mIProfile.setHeight(mHeight);
		mIProfile.setGender(mGender);
		mIProfile.setBloodGroup(mBloodGroup);
		mIProfile.setImage(mImagePath);

		mProfileDataSource = new ICareProfileDataSource(getActivity());
		if (mProfileDataSource.insert(mIProfile) == true) {
			Toast toast = Toast.makeText(getActivity(), "Successfully Saved.",
					Toast.LENGTH_LONG);
			toast.show();
			
			
			
			mProfileDataSource = new ICareProfileDataSource(getActivity());
			mIcareProfilesList = mProfileDataSource.iCareProfilesList();
			if(mIcareProfilesList.size() == 1){
			mID = mIcareProfilesList.get(0).getID();

			share(mID);
			}
			

			mfragment = new FragmentHome();
			mfrgManager = getFragmentManager();
			mfrgManager.beginTransaction().replace(R.id.content_frame, mfragment)
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
						mIvProfilePhoto, options);
				// mIvPhotoView.setImageBitmap(correctBmp);

			}

		}
	}

	public void setTitle(CharSequence title) {
		getActivity().getActionBar().setTitle(title);
	}

}