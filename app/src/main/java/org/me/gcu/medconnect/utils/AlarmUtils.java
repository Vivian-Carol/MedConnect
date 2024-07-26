package org.me.gcu.medconnect.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import java.util.Date;

public class AlarmUtils {

    public static void scheduleRepeatingAlarm(Context context, long startTime, long interval, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);
            Log.d("AlarmUtils", "Repeating alarm set for: " + new Date(startTime) + " with interval: " + interval);
        }
    }

    public static void scheduleAlarm(Context context, long triggerTime, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            Log.d("AlarmUtils", "Alarm set for: " + new Date(triggerTime));
        }
    }
}
