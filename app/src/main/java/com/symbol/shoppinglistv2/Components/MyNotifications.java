package com.symbol.shoppinglistv2.Components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyNotifications extends BroadcastReceiver {
    private final String TAG = "com.symbol.shoppinglistv2.Components.MyNotifications";
    private final String CHANNEL_ID = "channelID";
    private ArrayList<MyLog> myLogs;
    private int days;


    @Override
    public void onReceive(Context context, Intent intent) {
        myLogs = (ArrayList<MyLog>) intent.getExtras().getSerializable("array");
        days = intent.getExtras().getInt("days");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Log.d(TAG, "TestNote: 1"  );
            int productCounter = 0;
            for (MyLog myLog :
                    myLogs) {
                Date tempDate = new Date(Date.parse(myLog.getExpirationDays()));
                Calendar calendar = Calendar.getInstance();
                Log.d(TAG, "TestNote: 3"  );
                calendar.add(Calendar.DATE, days);
                if(tempDate.before(calendar.getTime())){
                    Log.d(TAG, "TestNote: 4"  );
                    productCounter++;
                }
            }
            Log.d(TAG, "TestNote: 5"  );
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_temp)
                    .setContentTitle("Some Products will expire in " + days + " days")
                    .setContentText("Amount of products to expire: " + productCounter)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            Log.d(TAG, "TestNote: 6"  );
            notificationManagerCompat.notify(0, builder.build());
            Log.d(TAG, "TestNote: 7"  );


    }
}

