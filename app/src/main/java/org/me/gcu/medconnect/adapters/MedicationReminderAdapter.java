package org.me.gcu.medconnect.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;
import org.me.gcu.medconnect.receivers.NotificationReceiver;
import org.me.gcu.medconnect.utils.AlarmUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MedicationReminderAdapter extends RecyclerView.Adapter<MedicationReminderAdapter.ViewHolder> {
    private final List<Prescription> prescriptions;
    private final DynamoDBMapper dynamoDBMapper;
    private final Context context;

    public MedicationReminderAdapter(List<Prescription> prescriptions, Context context) {
        this.prescriptions = prescriptions;
        this.dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prescription prescription = prescriptions.get(position);
        holder.medicationName.setText(prescription.getMedicationName());
        holder.dosage.setText(prescription.getDosage());
        holder.frequency.setText(prescription.getFrequency());
        holder.timing.setText(prescription.getInstructions()); // Assuming timing info is stored in instructions

        // Log prescription details
        Log.d("MedicationReminder", "Binding ViewHolder for: " + prescription.getMedicationName());

        // Schedule medication reminders
        scheduleMedicationReminders(prescription, position);

        // Schedule refill reminders
        scheduleRefillReminder(prescription, position);
    }

    private void scheduleMedicationReminders(Prescription prescription, int position) {
        String frequency = prescription.getFrequency();
        String timing = prescription.getInstructions(); // Assuming timing info is stored in instructions
        long interval = getIntervalFromFrequency(frequency);

        if (frequency.equals("Twice a day") || frequency.equals("Three times a day")) {
            for (int i = 1; i <= getInstances(frequency); i++) {
                long startTime = getStartTimeInMillis(frequency, timing, i);
                Log.d("MedicationReminder", "Scheduling reminder for " + prescription.getMedicationName() + " at " + new Date(startTime));
                AlarmUtils.scheduleRepeatingAlarm(context, startTime, interval, getPendingIntent(prescription, position + i, "MEDICATION_REMINDER"));
            }
        } else {
            long startTime = getStartTimeInMillis(frequency, timing, 1);
            Log.d("MedicationReminder", "Scheduling reminder for " + prescription.getMedicationName() + " at " + new Date(startTime));
            AlarmUtils.scheduleRepeatingAlarm(context, startTime, interval, getPendingIntent(prescription, position, "MEDICATION_REMINDER"));
        }
    }

    private void scheduleRefillReminder(Prescription prescription, int position) {
        long endDate = getEndDateInMillis(prescription.getEndDate());
        long reminderTime = endDate - TimeUnit.DAYS.toMillis(2);

        Log.d("MedicationReminder", "Scheduling refill reminder for " + prescription.getMedicationName() + " at " + new Date(reminderTime));
        AlarmUtils.scheduleAlarm(context, reminderTime, getPendingIntent(prescription, position, "REFILL_REMINDER"));
    }


    private PendingIntent getPendingIntent(Prescription prescription, int position, String action) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction(action);
        intent.putExtra("medicationName", prescription.getMedicationName());
        intent.putExtra("dosage", prescription.getDosage());
        intent.putExtra("position", position);

        return PendingIntent.getBroadcast(context, position, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    private long getIntervalFromFrequency(String frequency) {
//        switch (frequency) {
//            case "Once a day":
//                return TimeUnit.DAYS.toMillis(1);
//            case "Twice a day":
//                return TimeUnit.HOURS.toMillis(12);
//            case "Three times a day":
//                return TimeUnit.HOURS.toMillis(8);
//            default:
//                return TimeUnit.DAYS.toMillis(1);
//        }
        // Add the following code for testing:
        switch (frequency) {
            case "Once a day":
                return TimeUnit.MINUTES.toMillis(1); // For testing, trigger every minute
            case "Twice a day":
                return TimeUnit.SECONDS.toMillis(30); // For testing, trigger every 30 seconds
            case "Three times a day":
                return TimeUnit.SECONDS.toMillis(20); // For testing, trigger every 20 seconds
            default:
                return TimeUnit.MINUTES.toMillis(1); // Default to 1 minute for testing
        }
    }

    private int getInstances(String frequency) {
        switch (frequency) {
            case "Twice a day":
                return 2;
            case "Three times a day":
                return 3;
            default:
                return 1;
        }
    }

    private long getStartTimeInMillis(String frequency, String timing, int instance) {
        Calendar calendar = Calendar.getInstance();
        switch (frequency) {
            case "Once a day":
                setTimeForTiming(calendar, timing, 8); // 8 AM
                break;
            case "Twice a day":
                if (instance == 1) {
                    setTimeForTiming(calendar, timing, 8); // 8 AM
                } else {
                    setTimeForTiming(calendar, timing, 20); // 8 PM
                }
                break;
            case "Three times a day":
                if (instance == 1) {
                    setTimeForTiming(calendar, timing, 7); // 7 AM
                } else if (instance == 2) {
                    setTimeForTiming(calendar, timing, 13); // 1 PM
                } else {
                    setTimeForTiming(calendar, timing, 19); // 7 PM
                }
                break;
            default:
                setTimeForTiming(calendar, timing, 7); // Default to 8 AM
                break;
        }
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // If the time is in the past, move it to the next day
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return calendar.getTimeInMillis();
    }

    private void setTimeForTiming(Calendar calendar, String timing, int defaultHour) {
        switch (timing) {
            case "Before Meal":
                calendar.set(Calendar.HOUR_OF_DAY, defaultHour - 1); // 1 hour before the default time
                break;
            case "After Meal":
                calendar.set(Calendar.HOUR_OF_DAY, defaultHour + 1); // 1 hour after the default time
                break;
            default:
                calendar.set(Calendar.HOUR_OF_DAY, defaultHour); // Default to the provided hour
                break;
        }
    }

    private long getEndDateInMillis(String endDate) {
        // Convert endDate to millis
        // Assuming endDate is in the format "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = sdf.parse(endDate);
            return date != null ? date.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicationName, dosage, frequency, timing;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicationName = itemView.findViewById(R.id.medication_name);
            dosage = itemView.findViewById(R.id.dosage);
            frequency = itemView.findViewById(R.id.frequency);
            timing = itemView.findViewById(R.id.timing); // Add timing TextView
            editButton = itemView.findViewById(R.id.btn_edit);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}
