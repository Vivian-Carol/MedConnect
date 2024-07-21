package org.me.gcu.medconnect.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Show notification or reminder
        Toast.makeText(context, "Time to take your medication!", Toast.LENGTH_LONG).show();
    }
}
