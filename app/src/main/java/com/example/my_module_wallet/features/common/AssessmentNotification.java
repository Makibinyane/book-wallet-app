package com.example.my_module_wallet.features.common;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.my_module_wallet.MainActivity;
import com.example.my_module_wallet.R;

public class AssessmentNotification extends BroadcastReceiver {
    String task = "";
    long timeInMillis = 0;
    int reqCode = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getStringExtra("task").equals("")) {
            task = intent.getStringExtra("task");
        }
        if (intent.getLongExtra("timeInMillis", 0) != 0) {
            timeInMillis = intent.getLongExtra("timeInMillis", 0);
        }

        reqCode = intent.getIntExtra("reqCode", 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("To-Do List")
                .setContentText(task)
                .setWhen(timeInMillis)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        notificationIntent,
                        0
                );
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(reqCode, builder.build());
    }
}