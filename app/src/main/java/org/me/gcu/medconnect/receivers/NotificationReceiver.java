package org.me.gcu.medconnect.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.me.gcu.medconnect.activities.MainActivity;
import org.me.gcu.medconnect.R;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "medication_reminder_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String medicationName = intent.getStringExtra("medicationName");
        String dosage = intent.getStringExtra("dosage");

        createNotificationChannel(context);

        Log.d("NotificationReceiver", "Received action: " + action);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(action.equals("MEDICATION_REMINDER") ? "Medication Reminder" : "Refill Reminder")
                .setContentText(action.equals("MEDICATION_REMINDER") ? "It's time to take your medication: " + medicationName + " (" + dosage + ")" :
                        "Your medication " + medicationName + " (" + dosage + ") needs a refill in 2 days")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(intent.getIntExtra("position", 0), builder.build());
            Log.d("NotificationReceiver", "Notification sent for: " + medicationName);
        }
    }


    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Medication Reminder";
            String description = "Channel for medication reminders";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
