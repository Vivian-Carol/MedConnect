//package org.me.gcu.medconnect.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.models.Pharmacy;
//
//import java.util.List;
//
//public class MedicationSearchAdapter extends RecyclerView.Adapter<MedicationSearchAdapter.ViewHolder> {
//
//    private List<Pharmacy> pharmacies;
//
//    public MedicationSearchAdapter(List<Pharmacy> pharmacies) {
//        this.pharmacies = pharmacies;
//    }
//
//    public void updateData(List<Pharmacy> newPharmacies) {
//        this.pharmacies = newPharmacies;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pharmacy, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Pharmacy pharmacy = pharmacies.get(position);
//        holder.pharmacyName.setText(pharmacy.getPharmacyName());
//        holder.pharmacyAddress.setText(pharmacy.getPharmacyAddress());
//        // Add more fields as needed
//    }
//
//    @Override
//    public int getItemCount() {
//        return pharmacies.size();
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView pharmacyName;
//        TextView pharmacyAddress;
//        // Add more views as needed
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            pharmacyName = itemView.findViewById(R.id.pharmacy_name);
//            pharmacyAddress = itemView.findViewById(R.id.pharmacy_address);
//            // Initialize more views as needed
//        }
//    }
//}

package org.me.gcu.medconnect.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.models.Medication;
import org.me.gcu.medconnect.models.Pharmacy;

import java.util.List;

public class MedicationSearchAdapter extends RecyclerView.Adapter<MedicationSearchAdapter.ViewHolder> {

    private List<Pharmacy> pharmacies;

    public MedicationSearchAdapter(List<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }

    public void updateData(List<Pharmacy> newPharmacies) {
        this.pharmacies = newPharmacies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pharmacy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pharmacy pharmacy = pharmacies.get(position);
        if (pharmacy.getMedications() != null && !pharmacy.getMedications().isEmpty()) {
            Medication medication = pharmacy.getMedications().get(0); // Assuming the first medication for simplicity
            holder.medicationName.setText(medication.getMedicationName());
            holder.milligrams.setText(medication.getMilligrams());
            holder.price.setText(medication.getPrice());
        }

        holder.pharmacyName.setText(pharmacy.getPharmacyName());
        holder.pharmacyAddress.setText(pharmacy.getPharmacyAddress());
        holder.pharmacyPhoneNumber.setText(pharmacy.getPharmacyPhoneNumber());
        holder.town.setText(pharmacy.getTown());
    }

    @Override
    public int getItemCount() {
        return pharmacies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicationName;
        TextView milligrams;
        TextView price;
        TextView pharmacyName;
        TextView pharmacyPhoneNumber;
        TextView pharmacyAddress;
        TextView town;

        ViewHolder(View itemView) {
            super(itemView);
            medicationName = itemView.findViewById(R.id.medication_name);
            milligrams = itemView.findViewById(R.id.milligrams);
            price = itemView.findViewById(R.id.price);
            pharmacyName = itemView.findViewById(R.id.pharmacy_name);
            pharmacyPhoneNumber = itemView.findViewById(R.id.pharmacy_phone_number);
            pharmacyAddress = itemView.findViewById(R.id.pharmacy_address);
            town = itemView.findViewById(R.id.town);
        }
    }
}
