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
import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

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
        fetchRemindersFromDynamoDB();
        return view;
    }

    private void fetchRemindersFromDynamoDB() {
        new Thread(() -> {
            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
            List<Prescription> prescriptions = dynamoDBMapper.scan(Prescription.class, new DynamoDBScanExpression());
            if (isAdded()) {
                getActivity().runOnUiThread(() -> displayReminders(prescriptions));
            }
        }).start();
    }

    private void displayReminders(List<Prescription> prescriptions) {
        adapter = new MedicationReminderAdapter(prescriptions, getContext());
        recyclerView.setAdapter(adapter);
    }
}
