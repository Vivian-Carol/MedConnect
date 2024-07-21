package org.me.gcu.medconnect.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.models.Prescription;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {
    private final List<Prescription> prescriptions;

    public MedicationAdapter(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prescription prescription = prescriptions.get(position);
        holder.medicationName.setText(prescription.getMedicationName());
        holder.milligrams.setText(String.valueOf(prescription.getMilligrams()));
        holder.dosage.setText(prescription.getDosage());
        holder.frequency.setText(prescription.getFrequency());
        holder.startDate.setText(prescription.getStartDate());
        holder.endDate.setText(prescription.getEndDate());
        holder.refillDate.setText(prescription.getRefillDate());
        holder.instructions.setText(prescription.getInstructions());
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicationName, milligrams, dosage, frequency, startDate, endDate, refillDate, instructions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicationName = itemView.findViewById(R.id.medication_name);
            milligrams = itemView.findViewById(R.id.milligrams);
            dosage = itemView.findViewById(R.id.dosage);
            frequency = itemView.findViewById(R.id.frequency);
            startDate = itemView.findViewById(R.id.start_date);
            endDate = itemView.findViewById(R.id.end_date);
            refillDate = itemView.findViewById(R.id.refill_date);
            instructions = itemView.findViewById(R.id.instructions);
        }
    }
}
