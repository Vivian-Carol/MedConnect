package org.me.gcu.medconnect.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.adapters.MedicationReminderAdapter;
import org.me.gcu.medconnect.models.Reminder;

import java.util.ArrayList;
import java.util.List;

public class MedicationRemindersFragment extends Fragment {

    private RecyclerView recyclerView;
    private MedicationReminderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medication_reminders, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchReminders();
        return view;
    }

    private void fetchReminders() {
        // This method should fetch reminders from your data source
        // For now, let's create a dummy list of reminders

        List<Reminder> reminders = new ArrayList<>();
        reminders.add(new Reminder("Medication 1", "10mg", "Twice a day", "Morning", "2024-01-01 08:00"));
        reminders.add(new Reminder("Medication 2", "20mg", "Once a day", "Evening", "2024-01-01 20:00"));

        displayReminders(reminders);
    }

    private void displayReminders(List<Reminder> reminders) {
        adapter = new MedicationReminderAdapter(reminders);
        recyclerView.setAdapter(adapter);
    }
}
