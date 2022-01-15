package com.symbol.shoppinglistv2.Command;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.symbol.shoppinglistv2.Activities.FragmentSettings;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.MyNotifications;

import java.util.Calendar;

public class CommandPushNotification implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandPushNotification";
    private FragmentSettings fragmentSettings;
    private ActivityMain activityMain;
    private AlarmManager alarmManager;
    private NotificationManager notificationManager;

    private final String CHANNEL_ID = "channelID";
    private final String CHANNEL_NAME = "channelName";

    public CommandPushNotification(FragmentSettings fragmentSettings) {
        this.fragmentSettings = fragmentSettings;
    }

    public CommandPushNotification(ActivityMain activityMain, AlarmManager alarmManager, NotificationManager notificationManager){
        this.activityMain = activityMain;
        this.alarmManager = alarmManager;
        this.notificationManager = notificationManager;
    }

    @Override
    public boolean execute() {
        createChannel();
//        fragmentSettings.switchCompat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 9);
                calendar.set(Calendar.SECOND, Calendar.SECOND+10);


                Intent intent = new Intent(ActivityMain.appContext, MyNotifications.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityMain.appContext, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//                AlarmManager alarmManager = MainActivity.service;
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent );
//
//            }
//        });
//

        return false;
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLightColor(Color.RED);
            channel.enableLights(true);
            notificationManager.createNotificationChannel(channel);
        }


    }



}
