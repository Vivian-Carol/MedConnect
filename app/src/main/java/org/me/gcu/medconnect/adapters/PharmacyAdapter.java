package org.me.gcu.medconnect.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.models.Pharmacy;

import java.util.List;
import java.util.Map;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.ViewHolder> {
    private List<Pharmacy> pharmacies;

    public PharmacyAdapter(List<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
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

        holder.pharmacyName.setText(pharmacy.getPharmacyName());
        holder.pharmacyPhoneNumber.setText(pharmacy.getPharmacyPhoneNumber());
        holder.pharmacyAddress.setText(pharmacy.getPharmacyAddress());
        holder.town.setText(pharmacy.getTown());

        // Displaying the first medication as an example
        if (pharmacy.getMedications() != null && !pharmacy.getMedications().isEmpty()) {
            Map<String, Object> firstMed = (Map<String, Object>) pharmacy.getMedications().get(0);
            holder.medicationName.setText((String) firstMed.get("MedicationName"));
            holder.milligrams.setText((String) firstMed.get("Milligrams"));
            holder.price.setText((String) firstMed.get("Price"));
        } else {
            holder.medicationName.setText("N/A");
            holder.milligrams.setText("N/A");
            holder.price.setText("N/A");
        }
    }

    @Override
    public int getItemCount() {
        return pharmacies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicationName, milligrams, price, pharmacyName, pharmacyPhoneNumber, pharmacyAddress, town;

        public ViewHolder(@NonNull View itemView) {
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
