package com.mcdev.androidalarmdemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Objects;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    private static final int NOTIFICATION_ID = 0;
    private static final String NOTIFICATION_CHANNEL_ID = "notification_channel";
    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Broadcast received");
//        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            //initializing notificationManager with getSystemService()
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            deliverNotification(context);
//        }
    }

    private void deliverNotification(Context context) {
        Log.d(TAG, "deliverNotification: delivering notification...");
        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
                .setContentTitle("Sample Alarm")
                .setContentText("This is a sample test for android's alarm")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(false)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
