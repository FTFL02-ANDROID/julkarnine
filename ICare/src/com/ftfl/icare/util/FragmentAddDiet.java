package com.ftfl.icare.util;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftfl.icare.R;

public class FragmentAddDiet extends Fragment {

	public FragmentAddDiet() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_add_diet,
				container, false);

		return view;
	}

}