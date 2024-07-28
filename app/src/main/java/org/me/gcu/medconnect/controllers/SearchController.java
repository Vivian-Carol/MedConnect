package org.me.gcu.medconnect.controllers;

import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;

import org.me.gcu.medconnect.models.Pharmacy;
import org.me.gcu.medconnect.network.AwsClientProvider;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchController {

    private final SearchControllerListener listener;
    private final ExecutorService executorService;

    public SearchController(SearchControllerListener listener) {
        this.listener = listener;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void fetchPharmacies() {
        executorService.execute(() -> {
            try {
                DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                PaginatedScanList<Pharmacy> scanResult = dynamoDBMapper.scan(Pharmacy.class, scanExpression);
                if (listener != null) {
                    listener.onPharmaciesFetched(scanResult);
                }
            } catch (Exception e) {
                Log.e("SearchController", "Error fetching pharmacies", e);
            }
        });
    }

    public interface SearchControllerListener {
        void onPharmaciesFetched(List<Pharmacy> pharmacies);
    }
}
