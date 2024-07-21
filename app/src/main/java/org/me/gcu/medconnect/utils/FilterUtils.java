//package org.me.gcu.medconnect.utils;
//
//import org.me.gcu.medconnect.models.Pharmacy;
//import org.me.gcu.medconnect.models.Medication;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class FilterUtils {
//
//    // Filter pharmacies by region
//    public static List<Pharmacy> filterByRegion(List<Pharmacy> pharmacies, String region) {
//        return pharmacies.stream()
//                .filter(pharmacy -> pharmacy.getTown().equalsIgnoreCase(region))
//                .collect(Collectors.toList());
//    }
//
//    // Sort pharmacies by the lowest medication price
//    public static List<Pharmacy> sortByPrice(List<Pharmacy> pharmacies) {
//        pharmacies.sort(Comparator.comparingDouble(pharmacy -> getLowestPrice(pharmacy.getMedications())));
//        return pharmacies;
//    }
//
//    // Helper method to get the lowest price from the medications list
//    private static double getLowestPrice(List<Medication> medications) {
//        return medications.stream()
//                .mapToDouble(med -> Double.parseDouble(med.getPrice().replace(" KES", "")))
//                .min()
//                .orElse(Double.MAX_VALUE);
//    }
//}

package org.me.gcu.medconnect.utils;

import org.me.gcu.medconnect.models.Medication;
import org.me.gcu.medconnect.models.Pharmacy;

import java.util.Comparator;
import java.util.List;

public class FilterUtils {

    // Sort pharmacies by the lowest medication price
    public static List<Pharmacy> sortByPrice(List<Pharmacy> pharmacies) {
        pharmacies.sort(Comparator.comparingDouble(pharmacy -> getLowestPrice(pharmacy.getMedications())));
        return pharmacies;
    }

    // Helper method to get the lowest price from the medications list
    private static double getLowestPrice(List<Medication> medications) {
        return medications.stream()
                .mapToDouble(med -> Double.parseDouble(med.getPrice().replace(" KES", "")))
                .min()
                .orElse(Double.MAX_VALUE);
    }
}
