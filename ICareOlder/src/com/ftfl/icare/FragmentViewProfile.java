package com.ftfl.icare;

import java.util.List;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ftfl.icare.helper.ICareProfileDataSource;
import com.ftfl.icare.model.ICareProfile;
import com.ftfl.icare.util.FragmentHome;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FragmentViewProfile extends Fragment {

	ImageView ivProfilePhoto;
	Button btnProfilePhoto;
	EditText etName;
	EditText etDateOfBirth;
	EditText etWeight;
	EditText etHeight;
	EditText etGender;
	EditText etBloodGroup;
	Button btnSave;
	ICareProfileDataSource ProfileDataSource;
	List<ICareProfile> iCareProfilesList;
	ICareProfile IProfile;
	FragmentManager frgManager;
	Fragment fragment;
	Context thiscontext;
	String mImage;
	String mID;
	Bundle args = new Bundle();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_view_profile,
				container, false);
		setHasOptionsMenu(true);

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

		try {
			ivProfilePhoto = (ImageView) view.findViewById(R.id.ivProfilePhoto);
			etName = (EditText) view.findViewById(R.id.etName);
			etDateOfBirth = (EditText) view.findViewById(R.id.etDateOfBirth);
			etWeight = (EditText) view.findViewById(R.id.etWeight);
			etHeight = (EditText) view.findViewById(R.id.etHeight);
			etGender = (EditText) view.findViewById(R.id.etGender);
			etBloodGroup = (EditText) view.findViewById(R.id.etBloodGroup);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mID = getArguments().getString("id");

		if (mID == null) {
			
			SharedPreferences prfs = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
			String id = prfs.getString("profile_id", "");

			mID = id;
		}

		try {
			ProfileDataSource = new ICareProfileDataSource(getActivity());
			IProfile = ProfileDataSource.singleProfileData(Integer
					.parseInt(mID));

			etName.setText(IProfile.getName().toString());
			etDateOfBirth.setText(IProfile.getDateOfBirth().toString());
			etWeight.setText(IProfile.getWeight().toString());
			etHeight.setText(IProfile.getHeight().toString());
			etGender.setText(IProfile.getGender().toString());
			etBloodGroup.setText(IProfile.getBloodGroup().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mImage = IProfile.getImage().toString();

		if (mImage != null) {
			// Bitmap bmImg = BitmapFactory.decodeFile(mImage);
			// mIvImage.setImageBitmap(bmImg);

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.displayer(new RoundedBitmapDisplayer(100))
					.cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance().displayImage("file:///" + mImage,
					ivProfilePhoto, options);
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
			fragment = new FragmentAddProfile();
			frgManager = getFragmentManager();
			args.putString("id", mID);
			fragment.setArguments(args);
			frgManager.beginTransaction().replace(R.id.content_frame, fragment)
					.addToBackStack(null).commit();
			setTitle("Update Profile");
			return true;

		case R.id.delete:
			
			
			new AlertDialog.Builder(thiscontext)
		    .setTitle("Delete entry")
		    .setMessage("Are you sure you want to delete this entry?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            
		        	ProfileDataSource = new ICareProfileDataSource(getActivity());
					if (ProfileDataSource.deleteData(Integer.parseInt(mID)) == true) {
						Toast toast = Toast.makeText(getActivity(),
								"Successfully Deleted.", Toast.LENGTH_LONG);
						toast.show();

						fragment = new FragmentHome();
						frgManager = getFragmentManager();
						frgManager.beginTransaction()
								.replace(R.id.content_frame, fragment).commit();
						setTitle("Home");

					} else {
						Toast toast = Toast.makeText(getActivity(),
								"Error, Couldn't inserted data to database",
								Toast.LENGTH_LONG);
						toast.show();

					}
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
			
			
			
			
			return true;

		}
		return true;

	}

	public void setTitle(CharSequence title) {
		getActivity().getActionBar().setTitle(title);
	}

}