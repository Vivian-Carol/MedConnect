package org.me.gcu.medconnect.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.models.Prescription;

import java.util.List;

public class MedicationReminderSummaryAdapter extends RecyclerView.Adapter<MedicationReminderSummaryAdapter.ViewHolder> {

    private List<Prescription> prescriptions;
    private Context context;

    public MedicationReminderSummaryAdapter(List<Prescription> prescriptions, Context context) {
        this.prescriptions = prescriptions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medication_reminder_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prescription prescription = prescriptions.get(position);
        holder.medicationName.setText(prescription.getMedicationName());
        holder.medicationImage.setImageResource(R.drawable.ic_medication_placeholder);
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicationName;
        ImageView medicationImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicationName = itemView.findViewById(R.id.medication_name);
            medicationImage = itemView.findViewById(R.id.medication_image);
        }
    }
}
