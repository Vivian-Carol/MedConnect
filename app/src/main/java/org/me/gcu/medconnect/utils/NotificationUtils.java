package org.me.gcu.medconnect.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.receivers.NotificationReceiver;

public class NotificationUtils {

    public static void showCustomNotification(Context context, int notificationId, String medicationName, String dosage) {

        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.item_reminder);

        // Set the text for the views in the custom layout
        notificationLayout.setTextViewText(R.id.medication_name, medicationName);
        notificationLayout.setTextViewText(R.id.dosage, dosage);

        // Optionally set the image for the ImageView
        notificationLayout.setImageViewResource(R.id.reminder_image, R.drawable.ic_reminder_placeholder);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.getChannelId())
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app icon
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }

    public static PendingIntent getPendingIntent(Context context, int position, String action, String medicationName, String dosage) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction(action);
        intent.putExtra("medicationName", medicationName);
        intent.putExtra("dosage", dosage);
        intent.putExtra("position", position);

        return PendingIntent.getBroadcast(context, position, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
}
