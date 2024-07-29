//package org.me.gcu.medconnect.controllers;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//
//import org.me.gcu.medconnect.models.Prescription;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//
//public class ManualEntryController {
//
//    private static final String TAG = "ManualEntryController";
//    private ManualEntryControllerListener listener;
//
//    public ManualEntryController(ManualEntryControllerListener listener) {
//        this.listener = listener;
//    }
//
//    public void savePrescription(Prescription prescription) {
//        new Thread(() -> {
//            try {
//                DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AwsClientProvider.getDynamoDBClient());
//                dynamoDBMapper.save(prescription);
//                Log.d(TAG, "Prescription saved successfully");
//                postResult(true);
//            } catch (Exception e) {
//                Log.e(TAG, "Error saving prescription: " + e.getMessage(), e);
//                postResult(false);
//            }
//        }).start();
//    }
//
//    private void postResult(boolean success) {
//        new Handler(Looper.getMainLooper()).post(() -> listener.onPrescriptionSaved(success));
//    }
//
//    public interface ManualEntryControllerListener {
//        void onPrescriptionSaved(boolean success);
//    }
//}

package org.me.gcu.medconnect.controllers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;

public class ManualEntryController {

    private static final String TAG = "ManualEntryController";
    private ManualEntryControllerListener listener;

    public ManualEntryController(ManualEntryControllerListener listener) {
        this.listener = listener;
    }

    public void savePrescription(Prescription prescription) {
        new Thread(() -> {
            try {
                DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
                dynamoDBMapper.save(prescription);
                Log.d(TAG, "Prescription saved successfully");
                postResult(true);
            } catch (Exception e) {
                Log.e(TAG, "Error saving prescription: " + e.getMessage(), e);
                postResult(false);
            }
        }).start();
    }

    private void postResult(boolean success) {
        new Handler(Looper.getMainLooper()).post(() -> listener.onPrescriptionSaved(success));
    }

    public interface ManualEntryControllerListener {
        void onPrescriptionSaved(boolean success);
    }
}
