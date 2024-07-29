package org.me.gcu.medconnect.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import org.me.gcu.medconnect.controllers.MedicationRemindersController;
import org.me.gcu.medconnect.models.Prescription;

import java.util.List;

public class MedicationRemindersFragment extends Fragment implements MedicationRemindersController.MedicationRemindersControllerListener {

    private RecyclerView recyclerView;
    private MedicationReminderAdapter adapter;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medication_reminders, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null && getArguments().getString("USER_ID") != null) {
            userId = getArguments().getString("USER_ID");
        } else {
            userId = getUserIdFromPreferences();
        }

        if (userId != null) {
            MedicationRemindersController controller = new MedicationRemindersController(getContext(), this);
            controller.fetchReminders(userId);
        } else {
            displayEmptyView();
        }

        return view;
    }

    @Override
    public void onRemindersFetched(List<Prescription> prescriptions, boolean hasData) {
        if (hasData) {
            hideEmptyView();
            adapter = new MedicationReminderAdapter(prescriptions, getContext());
            recyclerView.setAdapter(adapter);
        } else {
            displayEmptyView();
        }
    }

    private void displayEmptyView() {
        // Display an empty view or a message indicating no data found
    }

    private void hideEmptyView() {
        // Hide the empty view
    }

    private String getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("USER_ID", null);
    }
}
