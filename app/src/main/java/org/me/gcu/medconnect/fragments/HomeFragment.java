package org.me.gcu.medconnect.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.adapters.MedicationReminderSummaryAdapter;
import org.me.gcu.medconnect.adapters.MedicationSummaryAdapter;
import org.me.gcu.medconnect.controllers.HomeController;
import org.me.gcu.medconnect.models.Prescription;

import java.util.List;

public class HomeFragment extends Fragment implements HomeController.HomeControllerListener {

    private TextView welcomeTextView;
    private RecyclerView reminderRecyclerView;
    private RecyclerView medicationRecyclerView;
    private TextView viewMoreReminders;
    private TextView viewMoreMedications;
    private String userId;
    private String username;
    private static final String TAG = "HomeFragment";
    private static final String PREFS_NAME = "user_prefs";
    private HomeController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        welcomeTextView = view.findViewById(R.id.welcomeTextView);
        reminderRecyclerView = view.findViewById(R.id.reminderRecyclerView);
        medicationRecyclerView = view.findViewById(R.id.medicationRecyclerView);
        viewMoreReminders = view.findViewById(R.id.viewMoreReminders);
        viewMoreMedications = view.findViewById(R.id.viewMoreMedications);

        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        medicationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString("USERNAME", null);
        userId = sharedPreferences.getString("USER_ID", null);

        Log.d(TAG, "Retrieved from SharedPreferences - Username: " + username);
        Log.d(TAG, "Retrieved from SharedPreferences - UserId: " + userId);

        if (username != null) {
            welcomeTextView.setText("Hi " + username + "!");
        } else {
            Log.d(TAG, "Username is null");
        }

        viewMoreReminders.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_reminders));
        viewMoreMedications.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_medications));

        controller = new HomeController();

        if (userId != null) {
            controller.fetchReminders(userId, this);
            controller.fetchMedications(userId, this);
        } else {
            displayEmptyView();
        }

        return view;
    }

    @Override
    public void onRemindersFetched(List<Prescription> reminders) {
        if (!reminders.isEmpty()) {
            Log.d(TAG, "Reminders found: " + reminders.size());
            // Limit the reminders to the first 5 items
            List<Prescription> limitedReminders = reminders.subList(0, Math.min(reminders.size(), 5));
            reminderRecyclerView.setAdapter(new MedicationReminderSummaryAdapter(limitedReminders, getContext()));
        } else {
            Log.d(TAG, "No reminders found for userId: " + userId);
            displayEmptyView();
        }
    }

    @Override
    public void onMedicationsFetched(List<Prescription> medications) {
        if (!medications.isEmpty()) {
            Log.d(TAG, "Medications found: " + medications.size());
            // Limit the medications to the first 4 items
            List<Prescription> limitedMedications = medications.subList(0, Math.min(medications.size(), 4));
            medicationRecyclerView.setAdapter(new MedicationSummaryAdapter(limitedMedications, getContext()));
        } else {
            Log.d(TAG, "No medications found for userId: " + userId);
            displayEmptyView();
        }
    }

    private void displayEmptyView() {
        // Display an empty view or a message indicating no data found
    }
}
