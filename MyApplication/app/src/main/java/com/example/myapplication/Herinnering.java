package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Herinnering extends BroadcastReceiver {
    //In deze klasse wordt de notificatie aangemaakt.
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder =    new NotificationCompat.Builder(context, "Notify")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Trime")
                .setContentText("Uw trein komt aan!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationmanager = NotificationManagerCompat.from(context);
        notificationmanager.notify(200,builder.build());
    }
}
