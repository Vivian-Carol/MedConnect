//package org.me.gcu.medconnect.fragments;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.adapters.MedicationReminderAdapter;
//import org.me.gcu.medconnect.models.Prescription;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MedicationRemindersFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private MedicationReminderAdapter adapter;
//    private String userId;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_medication_reminders, container, false);
//        recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Get the userId from arguments or SharedPreferences
//        if (getArguments() != null && getArguments().getString("USER_ID") != null) {
//            userId = getArguments().getString("USER_ID");
//        } else {
//            userId = getUserIdFromPreferences();
//        }
//
//        if (userId != null) {
//            fetchRemindersFromDynamoDB(userId);
//        } else {
//            displayEmptyView();
//        }
//
//        return view;
//    }
//
//    private void fetchRemindersFromDynamoDB(String userId) {
//        new Thread(() -> {
//            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
//            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);
//
//            // Filter prescriptions for the logged-in user
//            List<Prescription> userPrescriptions = new ArrayList<>();
//            for (Prescription prescription : allPrescriptions) {
//                if (prescription.getUserId().equals(userId)) {
//                    userPrescriptions.add(prescription);
//                }
//            }
//
//            if (isAdded()) {
//                getActivity().runOnUiThread(() -> displayReminders(userPrescriptions));
//            }
//        }).start();
//    }
//
//    private void displayReminders(List<Prescription> prescriptions) {
//        if (prescriptions.isEmpty()) {
//            displayEmptyView();
//        } else {
//            hideEmptyView();
//            adapter = new MedicationReminderAdapter(prescriptions, getContext());
//            recyclerView.setAdapter(adapter);
//        }
//    }
//
//    private void displayEmptyView() {
//        // Display an empty view or a message indicating no data found
//    }
//
//    private void hideEmptyView() {
//        // Hide the empty view
//    }
//
//    private String getUserIdFromPreferences() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//        return sharedPreferences.getString("USER_ID", null);
//    }
//}

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
