package org.me.gcu.medconnect.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.adapters.MedicationAdapter;
import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;

import java.util.List;

public class MedicationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private MedicationAdapter adapter;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medications, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        emptyView = view.findViewById(R.id.empty_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchPrescriptionsFromDynamoDB();
        return view;
    }

    private void fetchPrescriptionsFromDynamoDB() {
        new Thread(() -> {
            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
            List<Prescription> prescriptions = dynamoDBMapper.scan(Prescription.class, new DynamoDBScanExpression());
            FragmentActivity activity = getActivity();
            if (activity != null && isAdded()) {
                activity.runOnUiThread(() -> displayPrescriptions(prescriptions));
            }
        }).start();
    }

    private void displayPrescriptions(List<Prescription> prescriptions) {
        if (prescriptions.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new MedicationAdapter(prescriptions, getContext(), emptyView); // Pass emptyView to adapter
            recyclerView.setAdapter(adapter);
        }
    }
}
