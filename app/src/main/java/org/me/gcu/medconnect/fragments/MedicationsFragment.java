////package org.me.gcu.medconnect.fragments;
////
////import android.content.Context;
////import android.content.SharedPreferences;
////import android.os.Bundle;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.TextView;
////
////import androidx.annotation.NonNull;
////import androidx.annotation.Nullable;
////import androidx.fragment.app.Fragment;
////import androidx.fragment.app.FragmentActivity;
////import androidx.recyclerview.widget.LinearLayoutManager;
////import androidx.recyclerview.widget.RecyclerView;
////
////import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
////import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
////
////import org.me.gcu.medconnect.R;
////import org.me.gcu.medconnect.adapters.MedicationAdapter;
////import org.me.gcu.medconnect.models.Prescription;
////import org.me.gcu.medconnect.network.AwsClientProvider;
////
////import java.util.ArrayList;
////import java.util.List;
////
////public class MedicationsFragment extends Fragment {
////
////    private RecyclerView recyclerView;
////    private MedicationAdapter adapter;
////    private TextView emptyView;
////    private String userId;
////
////    @Nullable
////    @Override
////    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.fragment_medications, container, false);
////        recyclerView = view.findViewById(R.id.recycler_view);
////        emptyView = view.findViewById(R.id.empty_view);
////
////        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////
////        // Get the userId from arguments or SharedPreferences
////        if (getArguments() != null && getArguments().getString("USER_ID") != null) {
////            userId = getArguments().getString("USER_ID");
////        } else {
////            userId = getUserIdFromPreferences();
////        }
////
////        if (userId != null) {
////            fetchPrescriptionsFromDynamoDB(userId);
////        } else {
////            displayEmptyView();
////        }
////
////        return view;
////    }
////
////    private void fetchPrescriptionsFromDynamoDB(String userId) {
////        new Thread(() -> {
////            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
////            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
////            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);
////
////            // Filter prescriptions for the logged-in user
////            List<Prescription> userPrescriptions = new ArrayList<>();
////            for (Prescription prescription : allPrescriptions) {
////                if (prescription.getUserId().equals(userId)) {
////                    userPrescriptions.add(prescription);
////                }
////            }
////
////            FragmentActivity activity = getActivity();
////            if (activity != null && isAdded()) {
////                activity.runOnUiThread(() -> displayPrescriptions(userPrescriptions));
////            }
////        }).start();
////    }
////
////    private void displayPrescriptions(List<Prescription> prescriptions) {
////        if (prescriptions.isEmpty()) {
////            displayEmptyView();
////        } else {
////            hideEmptyView();
////            adapter = new MedicationAdapter(prescriptions, getContext());
////            recyclerView.setAdapter(adapter);
////        }
////    }
////
////    private void displayEmptyView() {
////        emptyView.setVisibility(View.VISIBLE);
////        recyclerView.setVisibility(View.GONE);
////    }
////
////    private void hideEmptyView() {
////        emptyView.setVisibility(View.GONE);
////        recyclerView.setVisibility(View.VISIBLE);
////    }
////
////    private String getUserIdFromPreferences() {
////        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
////        return sharedPreferences.getString("userId", null);
////    }
////}
//
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
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.adapters.MedicationAdapter;
//import org.me.gcu.medconnect.models.Prescription;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MedicationsFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private MedicationAdapter adapter;
//    private TextView emptyView;
//    private String userId;
//    private static final String TAG = "MedicationsFragment";
//    private static final String PREFS_NAME = "user_prefs";
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_medications, container, false);
//        recyclerView = view.findViewById(R.id.recycler_view);
//        emptyView = view.findViewById(R.id.empty_view);
//
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
//            fetchPrescriptionsFromDynamoDB(userId);
//        } else {
//            displayEmptyView();
//        }
//
//        return view;
//    }
//
//    private void fetchPrescriptionsFromDynamoDB(String userId) {
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
//            FragmentActivity activity = getActivity();
//            if (activity != null && isAdded()) {
//                activity.runOnUiThread(() -> displayPrescriptions(userPrescriptions));
//            }
//        }).start();
//    }
//
//    private void displayPrescriptions(List<Prescription> prescriptions) {
//        if (prescriptions.isEmpty()) {
//            displayEmptyView();
//        } else {
//            hideEmptyView();
//            adapter = new MedicationAdapter(prescriptions, getContext());
//            recyclerView.setAdapter(adapter);
//        }
//    }
//
//    private void displayEmptyView() {
//        emptyView.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);
//    }
//
//    private void hideEmptyView() {
//        emptyView.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);
//    }
//
//    private String getUserIdFromPreferences() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.adapters.MedicationAdapter;
import org.me.gcu.medconnect.controllers.MedicationsController;
import org.me.gcu.medconnect.models.Prescription;

import java.util.List;

public class MedicationsFragment extends Fragment implements MedicationsController.MedicationsControllerListener {

    private RecyclerView recyclerView;
    private MedicationAdapter adapter;
    private TextView emptyView;
    private String userId;
    private MedicationsController controller;
    private static final String PREFS_NAME = "user_prefs";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medications, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        emptyView = view.findViewById(R.id.empty_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the controller
        controller = new MedicationsController(this);

        // Get the userId from arguments or SharedPreferences
        if (getArguments() != null && getArguments().getString("USER_ID") != null) {
            userId = getArguments().getString("USER_ID");
        } else {
            userId = getUserIdFromPreferences();
        }

        if (userId != null) {
            controller.fetchPrescriptions(userId);
        } else {
            displayEmptyView();
        }

        return view;
    }

    @Override
    public void onPrescriptionsFetched(List<Prescription> prescriptions) {
        if (prescriptions.isEmpty()) {
            displayEmptyView();
        } else {
            hideEmptyView();
            adapter = new MedicationAdapter(prescriptions, getContext());
            recyclerView.setAdapter(adapter);
        }
    }

    private void displayEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private String getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("USER_ID", null);
    }
}
