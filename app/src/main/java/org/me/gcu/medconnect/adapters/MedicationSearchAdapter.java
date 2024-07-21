package org.me.gcu.medconnect.adapters;

import android.util.Log;
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
    private String searchQuery;

    public MedicationSearchAdapter(List<Pharmacy> pharmacies, String searchQuery) {
        this.pharmacies = pharmacies;
        this.searchQuery = searchQuery;
    }

    public void updateData(List<Pharmacy> newPharmacies, String newSearchQuery) {
        this.pharmacies = newPharmacies;
        this.searchQuery = newSearchQuery;
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

        Medication matchedMedication = null;
        for (Medication medication : pharmacy.getMedications()) {
            if (medication.getMedicationName().equalsIgnoreCase(searchQuery)) {
                matchedMedication = medication;
                break;
            }
        }

        if (matchedMedication != null) {
            holder.medicationName.setText(matchedMedication.getMedicationName());
            holder.milligrams.setText(matchedMedication.getMilligrams());
            holder.price.setText(matchedMedication.getPrice());

            // Debug logs
            Log.d("Adapter", "Medication Name: " + matchedMedication.getMedicationName());
            Log.d("Adapter", "Milligrams: " + matchedMedication.getMilligrams());
            Log.d("Adapter", "Price: " + matchedMedication.getPrice());
        } else {
            holder.medicationName.setText("N/A");
            holder.milligrams.setText("N/A");
            holder.price.setText("N/A");

            Log.d("Adapter", "No medications found for pharmacy: " + pharmacy.getPharmacyName());
        }

        holder.pharmacyName.setText(pharmacy.getPharmacyName());
        holder.pharmacyAddress.setText(pharmacy.getPharmacyAddress());
        holder.pharmacyPhoneNumber.setText(pharmacy.getPharmacyPhoneNumber());
        holder.town.setText(pharmacy.getTown());

        // Debug logs
        Log.d("Adapter", "Pharmacy Name: " + pharmacy.getPharmacyName());
        Log.d("Adapter", "Pharmacy Address: " + pharmacy.getPharmacyAddress());
        Log.d("Adapter", "Pharmacy Phone Number: " + pharmacy.getPharmacyPhoneNumber());
        Log.d("Adapter", "Town: " + pharmacy.getTown());
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
