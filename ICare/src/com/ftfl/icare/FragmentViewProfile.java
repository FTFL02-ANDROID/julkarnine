package com.ftfl.icare;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.icare.adapter.CustomProfileAdapter;
import com.ftfl.icare.helper.ICareProfileDataSource;
import com.ftfl.icare.model.ICareProfile;
import com.ftfl.icare.util.FragmentHome;
import com.ftfl.icare.util.ICareConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FragmentViewProfile extends Fragment {

	ImageView mIvProfilePhoto;
	Button mBtnProfilePhoto;
	EditText mEtName;
	EditText mEtDateOfBirth;
	EditText mEtWeight;
	EditText mEtHeight;
	EditText mEtGender;
	EditText mEtBloodGroup;
	Button mBtnSave;
	ICareProfileDataSource mProfileDataSource;
	List<ICareProfile> mICareProfilesList;
	ICareProfile mIProfile;
	FragmentManager mFrgManager;
	Fragment mFragment;
	Context mContext;
	String mImage;
	String mID;
	Bundle args = new Bundle();
	TextView mId_tv;
	String mId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_view_profile,
				container, false);
		setHasOptionsMenu(true);

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

		try {
			mIvProfilePhoto = (ImageView) view.findViewById(R.id.ivProfilePhoto);
			mEtName = (EditText) view.findViewById(R.id.etName);
			mEtDateOfBirth = (EditText) view.findViewById(R.id.etDateOfBirth);
			mEtWeight = (EditText) view.findViewById(R.id.etWeight);
			mEtHeight = (EditText) view.findViewById(R.id.etHeight);
			mEtGender = (EditText) view.findViewById(R.id.etGender);
			mEtBloodGroup = (EditText) view.findViewById(R.id.etBloodGroup);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mID = getArguments().getString("id");

		if (mID == null) {

			SharedPreferences prfs = getActivity().getSharedPreferences(
					"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
			String id = prfs.getString("profile_id", "");

			if (id.equals("")) {
				mProfileDataSource = new ICareProfileDataSource(getActivity());
				mICareProfilesList = mProfileDataSource.iCareProfilesList();
				mID = mICareProfilesList.get(0).getID();

				SharedPreferences preferences = getActivity()
						.getSharedPreferences("AUTHENTICATION_FILE_NAME",
								Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("profile_id", mID);
				editor.apply();

			} else {

				mID = id;
			}

		}

		try {
			mProfileDataSource = new ICareProfileDataSource(getActivity());
			mIProfile = mProfileDataSource.singleProfileData(Integer
					.parseInt(mID));

			mEtName.setText(mIProfile.getName().toString());
			mEtDateOfBirth.setText(mIProfile.getDateOfBirth().toString());
			mEtWeight.setText(mIProfile.getWeight().toString());
			mEtHeight.setText(mIProfile.getHeight().toString());
			mEtGender.setText(mIProfile.getGender().toString());
			mEtBloodGroup.setText(mIProfile.getBloodGroup().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mImage = mIProfile.getImage().toString();

		if (mImage != null) {
			// Bitmap bmImg = BitmapFactory.decodeFile(mImage);
			// mIvImage.setImageBitmap(bmImg);

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.displayer(new RoundedBitmapDisplayer(100))
					.cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance().displayImage("file:///" + mImage,
					mIvProfilePhoto, options);
		}

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Add your menu entries here
		inflater.inflate(R.menu.menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.update:
			mFragment = new FragmentAddProfile();
			mFrgManager = getFragmentManager();
			args.putString("id", mID);
			mFragment.setArguments(args);
			mFrgManager.beginTransaction().replace(R.id.content_frame, mFragment)
					.addToBackStack(null).commit();
			setTitle("Update Profile");
			return true;

		case R.id.delete:

			new AlertDialog.Builder(mContext)
					.setTitle("Delete entry")
					.setMessage("Are you sure you want to delete this entry?")
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									mProfileDataSource = new ICareProfileDataSource(
											getActivity());
									if (mProfileDataSource.deleteData(Integer
											.parseInt(mID)) == true) {
										Toast toast = Toast.makeText(
												getActivity(),
												"Successfully Deleted.",
												Toast.LENGTH_LONG);
										toast.show();
										
										

										mFragment = new FragmentHome();
										mFrgManager = getFragmentManager();
										mFrgManager
												.beginTransaction()
												.replace(R.id.content_frame,
														mFragment).commit();
										setTitle("Home");
										
										dialogOnStart();

										

									} else {
										Toast toast = Toast
												.makeText(
														getActivity(),
														"Error, Couldn't inserted data to database",
														Toast.LENGTH_LONG);
										toast.show();

									}
								}
							})
					.setNegativeButton(android.R.string.no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing
								}
							}).setIcon(android.R.drawable.ic_dialog_alert)
					.show();

			return true;

		}
		return true;

	}

	public void dialogOnStart() {

		ListView lvProfileList;

		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.fragment_layout_view_profile_list);
		dialog.setTitle("Select Profile");

		// Create global configuration and initialize ImageLoader with
		// getActivity()
		// config
		// Create default options which will be used for every
		// displayImage(...) call if no options will be passed to getActivity()
		// method
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity()).defaultDisplayImageOptions(defaultOptions)
				.build();
		ImageLoader.getInstance().init(config);

		mProfileDataSource = new ICareProfileDataSource(getActivity());
		mICareProfilesList = mProfileDataSource.iCareProfilesList();

		if (mICareProfilesList.size() == 0) {
			
			Toast.makeText(mContext, "No Profile exist, Please create One", Toast.LENGTH_LONG).show();
			mFragment = new FragmentAddProfile();
			mFragment.setArguments(args);
			mFrgManager = getFragmentManager();
			mFrgManager.beginTransaction().replace(R.id.content_frame, mFragment)
					.addToBackStack(null).commit();
		} else {

			CustomProfileAdapter arrayAdapter = new CustomProfileAdapter(
					getActivity(), mICareProfilesList);
			lvProfileList = (ListView) dialog.findViewById(R.id.lvProfileList);
			lvProfileList.setAdapter(arrayAdapter);

			lvProfileList
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							mId_tv = (TextView) view.findViewById(R.id.tvID);
							mId = mId_tv.getText().toString();

							share(mICareProfilesList.get(position).getID());

							dialog.dismiss();
						}
					});

			dialog.show();
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

	public void setTitle(CharSequence title) {
		getActivity().getActionBar().setTitle(title);
	}

}