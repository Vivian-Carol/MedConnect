package org.me.gcu.medconnect.controllers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;

import java.util.ArrayList;
import java.util.List;

public class HomeController {
    private DynamoDBMapper dynamoDBMapper;
    private Handler mainHandler;

    public interface HomeControllerListener {
        void onRemindersFetched(List<Prescription> reminders);
        void onMedicationsFetched(List<Prescription> medications);
    }

    public HomeController() {
        this.dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public void fetchReminders(String userId, HomeControllerListener listener) {
        new Thread(() -> {
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);

            // Filter prescriptions for the logged-in user
            List<Prescription> userReminders = new ArrayList<>();
            for (Prescription prescription : allPrescriptions) {
                if (prescription.getUserId().equals(userId)) {
                    userReminders.add(prescription);
                }
            }

            mainHandler.post(() -> listener.onRemindersFetched(userReminders));
        }).start();
    }

    public void fetchMedications(String userId, HomeControllerListener listener) {
        new Thread(() -> {
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);

            // Filter prescriptions for the logged-in user
            List<Prescription> userMedications = new ArrayList<>();
            for (Prescription prescription : allPrescriptions) {
                if (prescription.getUserId().equals(userId)) {
                    userMedications.add(prescription);
                }
            }

            mainHandler.post(() -> listener.onMedicationsFetched(userMedications));
        }).start();
    }
}
