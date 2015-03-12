package com.ftfl.icare.util;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.icare.R;
 
public class FragmentAddVaccination extends Fragment {

 
      public FragmentAddVaccination() {
 
      }
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState) {
 
            View view = inflater.inflate(R.layout.fragment_layout_add_vaccination, container,
                        false);
 
            return view;
      }
 
}