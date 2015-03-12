package com.ftfl.icare.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.icare.R;
import com.ftfl.icare.helper.DoctorProfileDataSource;
import com.ftfl.icare.helper.ImportantNoteDataSource;
import com.ftfl.icare.model.DoctorProfile;
import com.ftfl.icare.model.ImportantNotes;

public class FragmentAddNote extends Fragment {

	ImportantNoteDataSource mImportantNoteDataSource;
	ImportantNotes mImportantNotes;
	View view;
	EditText mEtTitle;
	EditText mEtSubject;
	EditText mEtNote;
	Button mBtnSave;
	FragmentManager mFrgManager;
	Fragment mFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_add_note,
				container, false);

		mEtTitle = (EditText) view.findViewById(R.id.etTitle);
		mEtSubject = (EditText) view.findViewById(R.id.etSubject);
		mEtNote = (EditText) view.findViewById(R.id.etNote);
		mBtnSave = (Button) view.findViewById(R.id.btnSave);

		mBtnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				createImportantNote();

			}

		});

		return view;

	}

	private void createImportantNote() {

		String mTitle = mEtTitle.getText().toString();
		String mSubject = mEtSubject.getText().toString();
		String mDescription = mEtNote.getText().toString();

		SharedPreferences prfs = getActivity().getSharedPreferences(
				"AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		String mProfileId = prfs.getString("profile_id", "");

		mImportantNoteDataSource = new ImportantNoteDataSource(getActivity());
		mImportantNotes = new ImportantNotes();
		mImportantNotes.setTitle(mTitle);
		mImportantNotes.setSubject(mSubject);
		mImportantNotes.setDescription(mDescription);
		mImportantNotes.setProfileId(mProfileId);

		if (mImportantNoteDataSource.insert(mImportantNotes) == true) {
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

	public void setTitle(CharSequence title) {
		getActivity().getActionBar().setTitle(title);
	}

}