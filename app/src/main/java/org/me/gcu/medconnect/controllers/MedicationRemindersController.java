package org.me.gcu.medconnect.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;

import java.util.ArrayList;
import java.util.List;

public class MedicationRemindersController {

    private Context context;
    private MedicationRemindersControllerListener listener;
    private static final String TAG = "MedRemindersController";

    public MedicationRemindersController(Context context, MedicationRemindersControllerListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void fetchReminders(String userId) {
        new FetchRemindersTask().execute(userId);
    }

    private class FetchRemindersTask extends AsyncTask<String, Void, List<Prescription>> {

        @Override
        protected List<Prescription> doInBackground(String... userIds) {
            String userId = userIds[0];
            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);

            List<Prescription> userPrescriptions = new ArrayList<>();
            for (Prescription prescription : allPrescriptions) {
                if (prescription.getUserId().equals(userId)) {
                    userPrescriptions.add(prescription);
                }
            }
            return userPrescriptions;
        }

        @Override
        protected void onPostExecute(List<Prescription> prescriptions) {
            if (prescriptions.isEmpty()) {
                listener.onRemindersFetched(prescriptions, false);
            } else {
                listener.onRemindersFetched(prescriptions, true);
            }
        }
    }

    public interface MedicationRemindersControllerListener {
        void onRemindersFetched(List<Prescription> prescriptions, boolean hasData);
    }
}
