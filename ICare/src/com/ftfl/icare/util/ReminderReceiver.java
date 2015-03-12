package com.ftfl.icare.util;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.widget.Toast;

public class ReminderReceiver extends BroadcastReceiver {
	// Vibrator object
	public Vibrator vibrator;
	long[] pattern = { 0L, 250L, 200L, 250L, 200L, 250L, 200L, 250L, 200L,
	        250L, 200L, 250L, 200L, 250L, 200L };
	// Ringtone object
	Uri ringT;
	Ringtone ringTone;

	@Override
	public void onReceive(Context context, Intent intent) {
	    String remindText = intent.getStringExtra("text");
	//Unique notification id for every alarm
	    int receiverID = intent.getIntExtra("AlrmId", 0);
	    // Notification creation
	    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
	            context).setSmallIcon(android.R.drawable.ic_popup_reminder)
	            .setContentTitle("Reminder").setContentText(remindText);

	    // Create vibrator pattern
	    vibrator = (Vibrator) context
	            .getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(pattern, -1);// No repetition

	    // Notification tone creation and play
	    ringT = RingtoneManager
	            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	    ringTone = RingtoneManager.getRingtone(context, ringT);
	    ringTone.play();
	    // Create toast and show on center of the screen
	    Toast toast = Toast.makeText(context, remindText, Toast.LENGTH_LONG);
	    toast.setGravity(Gravity.CENTER, 0, 0);
	    toast.show();
	    // end of toast...

	    // Show status bar notification
	    NotificationManager mNotificationManager = (NotificationManager) context
	            .getSystemService(Context.NOTIFICATION_SERVICE);
	    mNotificationManager.notify(receiverID, mBuilder.build());

	}
}