package com.ftfl.icare.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.security.auth.PrivateCredentialPermission;

import android.R.integer;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TimePicker;

public class setTimeOnclickDialog implements OnFocusChangeListener, OnTimeSetListener {   

    private EditText editText;
    private Calendar myCalendar;
	private Context ctx;


    public setTimeOnclickDialog(EditText editText, Context ctx){
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.ctx=ctx;
        myCalendar = Calendar.getInstance();

    }

     @Override
     public void onFocusChange(View v, boolean hasFocus) {
         // TODO Auto-generated method stub
         if(hasFocus){
             int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
             int minute = myCalendar.get(Calendar.MINUTE);
             new TimePickerDialog(ctx, this, hour, minute, false).show();
         }
     }

     @Override
     public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
         // TODO Auto-generated method stub
    	 String s=hourOfDay + ":" + minute;
    	
    	    
         editText.setText(getval(hourOfDay,minute));
     }
     
     public String getval(int hours ,int minutes) {
       
        
        String current_time;

         String suffix = "AM";
         if (hours >= 12) {
             suffix = "PM";
             hours = hours - 12;
         }
         if (hours == 0) {
             hours = 12;
         }
         if(minutes<10){
          current_time = hours + ":0" + minutes + " " + suffix;
         }else{
          current_time = hours + ":" + minutes + " " + suffix;
         }
        return current_time;
     }

 }