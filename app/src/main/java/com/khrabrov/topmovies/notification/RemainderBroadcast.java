package com.khrabrov.topmovies.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.khrabrov.topmovies.R;


public class RemainderBroadcast extends BroadcastReceiver {
    // Идентификатор канала
    private static String CHANNEL_ID = "Movie channel";

    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Movie")
                .setContentText("Scheduled movie viewing")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}