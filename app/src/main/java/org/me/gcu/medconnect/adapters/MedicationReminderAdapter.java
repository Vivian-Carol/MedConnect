package org.me.gcu.medconnect.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.models.Reminder;

import java.util.List;

public class MedicationReminderAdapter extends RecyclerView.Adapter<MedicationReminderAdapter.ViewHolder> {
    private List<Reminder> reminders;

    public MedicationReminderAdapter(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder reminder = reminders.get(position);
        holder.medicationName.setText(reminder.getMedicationName());
        holder.dosage.setText(reminder.getDosage());
        holder.frequency.setText(reminder.getFrequency());
        holder.timeOfDay.setText(reminder.getTimeOfDay());
        holder.nextReminderTime.setText(reminder.getNextReminderTime());

        holder.snoozeButton.setOnClickListener(v -> {
            // Implement snooze functionality
        });
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicationName, dosage, frequency, timeOfDay, nextReminderTime;
        Button snoozeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicationName = itemView.findViewById(R.id.medication_name);
            dosage = itemView.findViewById(R.id.dosage);
            frequency = itemView.findViewById(R.id.frequency);
            timeOfDay = itemView.findViewById(R.id.time_of_day);
            nextReminderTime = itemView.findViewById(R.id.next_reminder_time);
            snoozeButton = itemView.findViewById(R.id.snooze_button);
        }
    }
}
