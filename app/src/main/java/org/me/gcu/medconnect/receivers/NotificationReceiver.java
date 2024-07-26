package org.me.gcu.medconnect.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.me.gcu.medconnect.utils.NotificationHelper;
import org.me.gcu.medconnect.utils.NotificationUtils;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String medicationName = intent.getStringExtra("medicationName");
        String dosage = intent.getStringExtra("dosage");
        int notificationId = intent.getIntExtra("position", -1);

        if ("MEDICATION_REMINDER".equals(action)) {
            NotificationUtils.showCustomNotification(context, notificationId, medicationName, dosage);
            Log.d("NotificationReceiver", "Medication Reminder triggered for " + medicationName + " with ID: " + notificationId);
        } else if ("REFILL_REMINDER".equals(action)) {
            NotificationHelper notificationHelper = new NotificationHelper(context);
            notificationHelper.createNotification(notificationId, "Refill Reminder", "Time to refill your medication: " + medicationName);
        }
    }
}
