package org.me.gcu.medconnect.controllers;

import android.os.AsyncTask;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;

import java.util.ArrayList;
import java.util.List;

public class MedicationsController {

    private final MedicationsControllerListener listener;

    public MedicationsController(MedicationsControllerListener listener) {
        this.listener = listener;
    }

    public void fetchPrescriptions(String userId) {
        new FetchPrescriptionsTask(userId, listener).execute();
    }

    private static class FetchPrescriptionsTask extends AsyncTask<Void, Void, List<Prescription>> {
        private final String userId;
        private final MedicationsControllerListener listener;

        FetchPrescriptionsTask(String userId, MedicationsControllerListener listener) {
            this.userId = userId;
            this.listener = listener;
        }

        @Override
        protected List<Prescription> doInBackground(Void... voids) {
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
            listener.onPrescriptionsFetched(prescriptions);
        }
    }

    public interface MedicationsControllerListener {
        void onPrescriptionsFetched(List<Prescription> prescriptions);
    }
}
