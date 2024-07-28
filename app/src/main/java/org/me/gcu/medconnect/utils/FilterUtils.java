package org.me.gcu.medconnect.utils;

import android.util.Log;

import org.me.gcu.medconnect.models.Medication;
import org.me.gcu.medconnect.models.Pharmacy;

import java.util.Comparator;
import java.util.List;

public class FilterUtils {

    // Sort pharmacies by the lowest medication price for the given query
    public static List<Pharmacy> sortByPrice(List<Pharmacy> pharmacies, String query) {
        Log.d("FilterUtils", "Sorting by price for query: " + query);
        pharmacies.sort(Comparator.comparingDouble(pharmacy -> getLowestPrice(pharmacy.getMedications(), query)));
        return pharmacies;
    }

    // Helper method to get the lowest price for the given query from the medications list
    private static double getLowestPrice(List<Medication> medications, String query) {
        return medications.stream()
                .filter(med -> med.getMedicationName().equalsIgnoreCase(query))
                .mapToDouble(med -> {
                    try {
                        return Double.parseDouble(med.getPrice().replace(" KES", ""));
                    } catch (NumberFormatException e) {
                        Log.e("FilterUtils", "Error parsing price for medication: " + med.getMedicationName(), e);
                        return Double.MAX_VALUE;
                    }
                })
                .min()
                .orElse(Double.MAX_VALUE);
    }
}
