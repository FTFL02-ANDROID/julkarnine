package com.ftfl.icare.util;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftfl.icare.R;

public class FragmentAbout extends Fragment {
    
 public FragmentAbout() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.activity_layout_about, container,
                      false);

          return view;
    }

}