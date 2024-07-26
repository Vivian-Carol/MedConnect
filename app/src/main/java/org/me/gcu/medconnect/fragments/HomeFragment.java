//package org.me.gcu.medconnect.fragments;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.navigation.Navigation;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.adapters.MedicationReminderSummaryAdapter;
//import org.me.gcu.medconnect.adapters.MedicationSummaryAdapter;
//import org.me.gcu.medconnect.models.Prescription;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HomeFragment extends Fragment {
//
//    private TextView welcomeTextView;
//    private RecyclerView reminderRecyclerView;
//    private RecyclerView medicationRecyclerView;
//    private TextView viewMoreReminders;
//    private TextView viewMoreMedications;
//    private String userId;
//    private String username;
//    private static final String TAG = "HomeFragment";
//    private static final String PREFS_NAME = "user_prefs";
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        welcomeTextView = view.findViewById(R.id.welcomeTextView);
//        reminderRecyclerView = view.findViewById(R.id.reminderRecyclerView);
//        medicationRecyclerView = view.findViewById(R.id.medicationRecyclerView);
//        viewMoreReminders = view.findViewById(R.id.viewMoreReminders);
//        viewMoreMedications = view.findViewById(R.id.viewMoreMedications);
//
//        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        medicationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//
//        // Fetch user details from SharedPreferences
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        username = sharedPreferences.getString("USERNAME", null);
//        userId = sharedPreferences.getString("USER_ID", null);
//
//        Log.d(TAG, "Retrieved from SharedPreferences - Username: " + username);
//        Log.d(TAG, "Retrieved from SharedPreferences - UserId: " + userId);
//
//        if (username != null) {
//            welcomeTextView.setText("Hi " + username + "!");
//        } else {
//            Log.d(TAG, "Username is null");
//        }
//
//        // Navigate to the MedicationRemindersFragment
//        viewMoreReminders.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_reminders));
//
//        // Navigate to the MedicationsFragment
//        viewMoreMedications.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_medications));
//
//        // Fetch actual data
//        if (userId != null) {
//            fetchReminders();
//            fetchMedications();
//        } else {
//            displayEmptyView();
//        }
//
//        return view;
//    }
//
//    private void fetchReminders() {
//        new Thread(() -> {
//            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
//            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);
//
//            // Filter prescriptions for the logged-in user
//            List<Prescription> userReminders = new ArrayList<>();
//            for (Prescription prescription : allPrescriptions) {
//                if (prescription.getUserId().equals(userId)) {
//                    userReminders.add(prescription);
//                }
//            }
//
//            FragmentActivity activity = getActivity();
//            if (activity != null && isAdded()) {
//                activity.runOnUiThread(() -> {
//                    if (!userReminders.isEmpty()) {
//                        Log.d(TAG, "Reminders found: " + userReminders.size());
//                        reminderRecyclerView.setAdapter(new MedicationReminderSummaryAdapter(userReminders, getContext()));
//                    } else {
//                        Log.d(TAG, "No reminders found for userId: " + userId);
//                        displayEmptyView();
//                    }
//                });
//            }
//        }).start();
//    }
//
//    private void fetchMedications() {
//        new Thread(() -> {
//            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
//            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);
//
//            // Filter prescriptions for the logged-in user
//            List<Prescription> userMedications = new ArrayList<>();
//            for (Prescription prescription : allPrescriptions) {
//                if (prescription.getUserId().equals(userId)) {
//                    userMedications.add(prescription);
//                }
//            }
//
//            FragmentActivity activity = getActivity();
//            if (activity != null && isAdded()) {
//                activity.runOnUiThread(() -> {
//                    if (!userMedications.isEmpty()) {
//                        Log.d(TAG, "Medications found: " + userMedications.size());
//                        medicationRecyclerView.setAdapter(new MedicationSummaryAdapter(userMedications, getContext()));
//                    } else {
//                        Log.d(TAG, "No medications found for userId: " + userId);
//                        displayEmptyView();
//                    }
//                });
//            }
//        }).start();
//    }
//
//    private void displayEmptyView() {
//        // Display an empty view or a message indicating no data found
//    }
//}

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
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.adapters.MedicationReminderSummaryAdapter;
import org.me.gcu.medconnect.adapters.MedicationSummaryAdapter;
import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView welcomeTextView;
    private RecyclerView reminderRecyclerView;
    private RecyclerView medicationRecyclerView;
    private TextView viewMoreReminders;
    private TextView viewMoreMedications;
    private String userId;
    private String username;
    private static final String TAG = "HomeFragment";
    private static final String PREFS_NAME = "user_prefs";

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

        // Fetch user details from SharedPreferences
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

        // Navigate to the MedicationRemindersFragment
        viewMoreReminders.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_reminders));

        // Navigate to the MedicationsFragment
        viewMoreMedications.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_medications));

        // Fetch actual data
        if (userId != null) {
            fetchReminders();
            fetchMedications();
        } else {
            displayEmptyView();
        }

        return view;
    }

    private void fetchReminders() {
        new Thread(() -> {
            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);

            // Filter prescriptions for the logged-in user
            List<Prescription> userReminders = new ArrayList<>();
            for (Prescription prescription : allPrescriptions) {
                if (prescription.getUserId().equals(userId)) {
                    userReminders.add(prescription);
                }
            }

            FragmentActivity activity = getActivity();
            if (activity != null && isAdded()) {
                activity.runOnUiThread(() -> {
                    if (!userReminders.isEmpty()) {
                        Log.d(TAG, "Reminders found: " + userReminders.size());
                        reminderRecyclerView.setAdapter(new MedicationReminderSummaryAdapter(userReminders, getContext()));
                    } else {
                        Log.d(TAG, "No reminders found for userId: " + userId);
                        displayEmptyView();
                    }
                });
            }
        }).start();
    }

    private void fetchMedications() {
        new Thread(() -> {
            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);

            // Filter prescriptions for the logged-in user
            List<Prescription> userMedications = new ArrayList<>();
            for (Prescription prescription : allPrescriptions) {
                if (prescription.getUserId().equals(userId)) {
                    userMedications.add(prescription);
                }
            }

            FragmentActivity activity = getActivity();
            if (activity != null && isAdded()) {
                activity.runOnUiThread(() -> {
                    if (!userMedications.isEmpty()) {
                        Log.d(TAG, "Medications found: " + userMedications.size());
                        medicationRecyclerView.setAdapter(new MedicationSummaryAdapter(userMedications, getContext()));
                    } else {
                        Log.d(TAG, "No medications found for userId: " + userId);
                        displayEmptyView();
                    }
                });
            }
        }).start();
    }

    private void displayEmptyView() {
        // Display an empty view or a message indicating no data found
    }
}
