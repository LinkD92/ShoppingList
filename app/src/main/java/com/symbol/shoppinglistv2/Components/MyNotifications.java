package com.symbol.shoppinglistv2.Components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.symbol.shoppinglistv2.R;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyNotifications extends BroadcastReceiver {
    private final String TAG = "com.symbol.shoppinglistv2.Components.MyNotifications";
    private final String CHANNEL_ID = "channelID";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_temp)
                .setContentTitle("Test Tit")
                .setContentText("Long info bla bla ")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManagerCompat.notify(0, builder.build());
    }
}

