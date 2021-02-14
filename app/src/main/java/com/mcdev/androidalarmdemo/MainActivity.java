package com.mcdev.androidalarmdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.core.app.AlarmManagerCompat;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private static final int NOTIFICATION_ID = 0;
    private static final String NOTIFICATION_CHANNEL_ID = "notification_channel";
    private static final String NOTIFICATION_CHANNEL_NAME = "alarm";
    NotificationManager notificationManager;
    AppCompatToggleButton toggleButton;
    private final int requestCode = 203;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        createNotificationChannel();

        Intent notifyIntent = new Intent(MainActivity.this, MyBroadcastReceiver.class);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(MainActivity.this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                //Setting the alarm to start at approx. 8:00pm
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 55);

        //With setInexactRepeating(), you have to use one of the AlarmManager interval
        //constants--in this case, AlarmManager.INTERVAL_DAY.
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);



        toggleButton.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {

//                long repeatInterval = 60000L;
//                long triggerTime = SystemClock.elapsedRealtime()
//                        + repeatInterval;
                Log.d(TAG, "onCreate: toggle is checked ");
                Toast.makeText(this, "Alarm on!", Toast.LENGTH_SHORT).show();
                if (alarmManager != null) {

//                    Log.d(TAG, "onCreate: trigger time is " + calendar.getTimeInMillis());
//                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, notifyPendingIntent);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, notifyPendingIntent);
                }
            } else {
                Log.d(TAG, "onCreate: toggle is not checked");
                Toast.makeText(this, "Alarm off!", Toast.LENGTH_SHORT).show();
                if (alarmManager != null) {
                    alarmManager.cancel(notifyPendingIntent);
                }
                notificationManager.cancelAll();

            }
        });







//        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
//
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
////        if (pendingIntent != null && alarmManager != null) {
////            alarmManager.cancel(pendingIntent);
////        }
//
//        //Setting the alarm to start at approx. 8:00pm
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 20);
//        calendar.set(Calendar.MINUTE, 56);
//
//        //With setInexactRepeating(), you have to use one of the AlarmManager interval
//        //constants--in this case, AlarmManager.INTERVAL_DAY.
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void init() {
        toggleButton = findViewById(R.id.alarm_toggle);
    }

    private void createNotificationChannel() {
        //creating notification manager object
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //creating notif channel with all params
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("test alarm");

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


}