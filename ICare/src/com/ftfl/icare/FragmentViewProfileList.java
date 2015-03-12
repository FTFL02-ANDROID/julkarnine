package com.ftfl.icare;

import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.adapter.CustomProfileAdapter;
import com.ftfl.icare.helper.ICareProfileDataSource;
import com.ftfl.icare.model.ICareProfile;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FragmentViewProfileList extends Fragment {

	TextView mId_tv = null;
	ICareProfileDataSource mProfileDataSource;
	ICareProfile mIProfile;
	FragmentManager mFrgManager;
	Fragment mFragment;
	Context mContext;
	ListView mLvProfileList;
	List<ICareProfile> mICareProfilesList;
	String mId;
	Bundle args = new Bundle();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_view_profile_list,
				container, false);
		mContext = container.getContext();
		
		
		
		// Create global configuration and initialize ImageLoader with this
				// config
				// Create default options which will be used for every
				// displayImage(...) call if no options will be passed to this method
				DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
						.cacheInMemory(true).cacheOnDisk(true).build();
				ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
						mContext).defaultDisplayImageOptions(
						defaultOptions).build();
				ImageLoader.getInstance().init(config);


				mProfileDataSource = new ICareProfileDataSource(mContext);
				mICareProfilesList = mProfileDataSource.iCareProfilesList();
				CustomProfileAdapter arrayAdapter = new CustomProfileAdapter(getActivity(), mICareProfilesList);
				mLvProfileList = (ListView) view.findViewById(R.id.lvProfileList);
				mLvProfileList.setAdapter(arrayAdapter);

				
				
				mLvProfileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						mId_tv = (TextView) view.findViewById(R.id.tvID);
						mId = mId_tv.getText().toString();

						mFragment = new FragmentViewProfile();
						mFrgManager = getFragmentManager();
						args.putString("id", mICareProfilesList.get(position).getID());
						mFragment.setArguments(args);
						mFrgManager.beginTransaction().replace(R.id.content_frame, mFragment)
								.commit();
						setTitle("View Profile");
					}
				});

				

		
		

		return view;
	}
	
	public void setTitle(CharSequence title) {
		
		getActivity().getActionBar().setTitle(title);
	}


}