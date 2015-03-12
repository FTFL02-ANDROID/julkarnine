package com.ftfl.icare.util;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import android.content.Context;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;






public class SetDateOnClickDialog implements OnFocusChangeListener, OnDateSetListener {   

       private EditText editText;
       private Calendar myCalendar;
	   private Context ctx;
	   public int dayOfWeek=0;

       public  SetDateOnClickDialog(EditText editText, Context ctx){
           this.editText = editText;
           this.editText.setOnFocusChangeListener (this);
           this.ctx=ctx;
           myCalendar = Calendar.getInstance();
           dayOfWeek=myCalendar.get(Calendar.DAY_OF_WEEK);
          
       }
       @Override
    public void onFocusChange(View v, boolean hasFocus) {
    	// TODO Auto-generated method stub
    	   if(hasFocus){
               int year = myCalendar.get(Calendar.YEAR);
               int monthOfYear = myCalendar.get(Calendar.MONTH);
               int dayOfMonth= myCalendar.get(Calendar.DAY_OF_MONTH);
             
               new DatePickerDialog(ctx, this, year, monthOfYear, dayOfMonth).show();
    		  
           }
    }
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		int month=monthOfYear+1;
		 String s;
		if(month<10){
		s=dayOfMonth + "/0" + month+"/"+year;
		}else{
	    s=dayOfMonth + "/" + month+"/"+year;
		}
		editText.setText(s);
		
	}
	public String getDayOfMonth(int eDay){
	  String day="";
	  if(eDay==1){
		  day="Sunday";
	  }else if(eDay==2){
		  day="Monday";
	  }else if(eDay==3){
		  day="Tuesday";
	  }else if(eDay==4){
		  day="Wednesday";
	  }else if(eDay==5){
		  day="Thursday";
	  }else if(eDay==6){
		  day="Friday";
	  }else if(eDay==7){
		  day="Saturday";
	  }
	  return day;
	}

	
	

       
        }

    

