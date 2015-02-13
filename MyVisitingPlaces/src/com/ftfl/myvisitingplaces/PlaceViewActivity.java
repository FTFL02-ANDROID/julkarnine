package com.ftfl.myvisitingplaces;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ftfl.myvisitingplaces.helper.VisitingPlacesDataSource;
import com.ftfl.myvisitingplaces.model.VisitingPlace;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class PlaceViewActivity extends Activity {

	ImageView mIvImage;
	EditText mEdLatitude;
	EditText mEdLongtitude;
	EditText mEdDate;
	EditText mEdTime;
	EditText mEdDescription;
	Button mBtnSave;
	Button mBtnDelete;
	ImageButton mImageButtonEmail;
	ImageButton mImageButtonPhone;
	ImageButton mImageButtonAddContract;
	String mImage;
	String mImageUpdate;
	String mLatitude;
	String mLongitude;
	String mDate;
	String mTime;
	String mDescription;
	String mName = "";
	String mEmail = "";
	String mPhone = "";
	VisitingPlace place;
	VisitingPlace placeUpdate;
	VisitingPlacesDataSource mPlacesDataSource;
	String mId = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_view);

		mIvImage = (ImageView) findViewById(R.id.ivTakePhoto);
		mEdLatitude = (EditText) findViewById(R.id.etLatitude);
		mEdLongtitude = (EditText) findViewById(R.id.etLongitude);
		mEdDate = (EditText) findViewById(R.id.etDate);
		mEdTime = (EditText) findViewById(R.id.etTime);
		mEdDescription = (EditText) findViewById(R.id.etDescription);
		mBtnSave = (Button) findViewById(R.id.btnSave);
		mBtnDelete = (Button) findViewById(R.id.btnDelete);
		mImageButtonEmail = (ImageButton) findViewById(R.id.imageButtonEmail);
		mImageButtonPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
		mImageButtonAddContract = (ImageButton) findViewById(R.id.imageButtonAddContract);

		Intent mActivityIntent = getIntent();
		mId = mActivityIntent.getStringExtra("Id");
		mPlacesDataSource = new VisitingPlacesDataSource(this);
		place = mPlacesDataSource.singlePlace(mId);

		mImage = place.getPlaceImage();
		mLatitude = place.getPlaceLatitude().toString();
		mLongitude = place.getPlaceLongitude().toString();
		mDate = place.getPlaceDate().toString();
		mTime = place.getPlaceTime().toString();
		mDescription = place.getPlaceDescription().toString();
		mName = place.getPlaceName().toString();
		mEmail = place.getPlaceEmail().toString();
		mPhone = place.getPlacePhone().toString();

		if (mImage != null) {
			// Bitmap bmImg = BitmapFactory.decodeFile(mImage);
			// mIvImage.setImageBitmap(bmImg);

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.displayer(new RoundedBitmapDisplayer(0))
					.cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance().displayImage("file:///" + mImage,
					mIvImage, options);
		}

		mEdLatitude.setText(mLatitude);
		mEdLongtitude.setText(mLongitude);
		mEdDate.setText(mDate);
		mEdTime.setText(mTime);
		mEdDescription.setText(mDescription);

		mImageButtonEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				
				
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
						.fromParts("mailto", mEmail, null));
//				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
				startActivity(Intent
						.createChooser(emailIntent, "Send email..."));

			}
		});
		
		mImageButtonAddContract.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
	            int rawContactInsertIndex = ops.size();

	            ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
	                    .withValue(RawContacts.ACCOUNT_TYPE, null)
	                    .withValue(RawContacts.ACCOUNT_NAME, null).build());
	            ops.add(ContentProviderOperation
	                    .newInsert(Data.CONTENT_URI)
	                    .withValueBackReference(Data.RAW_CONTACT_ID,rawContactInsertIndex)
	                    .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
	                    .withValue(StructuredName.DISPLAY_NAME, mName) // Name of the person
	                    .build());
	            ops.add(ContentProviderOperation
	                    .newInsert(Data.CONTENT_URI)
	                    .withValueBackReference(
	                            ContactsContract.Data.RAW_CONTACT_ID,   rawContactInsertIndex)
	                    .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
	                    .withValue(Phone.NUMBER, mPhone) // Number of the person
	                    .withValue(Phone.TYPE, Phone.TYPE_MOBILE).build()); // Type of mobile number                    
	            try
	            {
	                ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	                
	                Toast.makeText(getApplicationContext(), "Successfully  Contract Added !!!!!!!" , Toast.LENGTH_LONG).show();
	            }
	            catch (RemoteException e)
	            { 
	                // error
	            }
	            catch (OperationApplicationException e) 
	            {
	                // error
	            }   
			}
		});

		mImageButtonPhone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
	Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:" + mPhone));
				startActivity(intent);

			}
		});

		mBtnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				editData(mId);

			}
		});

		mBtnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				deleteData(mId);
			}
		});

	}

	

	public void editData(String eId) {

		mLatitude = mEdLatitude.getText().toString();
		mLongitude = mEdLongtitude.getText().toString();
		mDate = mEdDate.getText().toString();
		mTime = mEdTime.getText().toString();
		mDescription = mEdDescription.getText().toString();
		mImageUpdate = place.getPlaceImage();
		placeUpdate = new VisitingPlace(mDate, mTime, mLatitude, mLongitude,
				mDescription, mImageUpdate, mName, mEmail, mPhone);

		long mlID = Long.parseLong(mId);
		mPlacesDataSource = new VisitingPlacesDataSource(this);

		if (mPlacesDataSource.updateData(mlID, placeUpdate) == true) {
			Toast toast = Toast.makeText(this, "Successfully Updated.",
					Toast.LENGTH_LONG);
			toast.show();

			startActivity(new Intent(getApplicationContext(),
					VisitingPlacesListViewActivity.class));

			finish();

		} else {
			Toast toast = Toast.makeText(this,
					"Error, Couldn't inserted data to database",
					Toast.LENGTH_LONG);
			toast.show();

		}

	}

	public void deleteData(String eId) {
		Long id = Long.parseLong(eId);
		mPlacesDataSource.deleteData(id);
		File file = new File(mImage);
		boolean deleted = file.delete();
		if (deleted == true) {
			Toast.makeText(getApplicationContext(), "Delete Successful",
					Toast.LENGTH_LONG).show();
		}

		Intent mEditIntent = new Intent(getApplicationContext(),
				VisitingPlacesListViewActivity.class);
		startActivity(mEditIntent);
		finish();

	}

}
