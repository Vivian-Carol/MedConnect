package org.me.gcu.medconnect.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {
    private final List<Prescription> prescriptions;
    private final DynamoDBMapper dynamoDBMapper;
    private final Context context;

    public MedicationAdapter(List<Prescription> prescriptions, Context context) {
        this.prescriptions = new ArrayList<>(prescriptions); // Create a modifiable copy of the list
        this.dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
        this.context = context;
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
        holder.dosage.setText(prescription.getDosage());
        holder.frequency.setText(prescription.getFrequency());
        holder.startDate.setText(prescription.getStartDate());
        holder.endDate.setText(prescription.getEndDate());
        holder.refillDate.setText(prescription.getRefillDate());
        holder.instructions.setText(prescription.getInstructions());

        holder.editButton.setOnClickListener(v -> showEditDialog(prescription, position));
        holder.deleteButton.setOnClickListener(v -> confirmDeleteMedication(prescription, position));
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    public void updatePrescriptions(List<Prescription> newPrescriptions) {
        prescriptions.clear();
        prescriptions.addAll(newPrescriptions);
        notifyDataSetChanged();
    }

    private void showEditDialog(Prescription prescription, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_medication, null);
        builder.setView(dialogView);

        EditText etMedicationName = dialogView.findViewById(R.id.et_medication_name);
        EditText etDosage = dialogView.findViewById(R.id.et_dosage);
        Spinner spinnerFrequency = dialogView.findViewById(R.id.spinner_frequency);
        EditText etStartDate = dialogView.findViewById(R.id.et_start_date);
        EditText etEndDate = dialogView.findViewById(R.id.et_end_date);
        EditText etRefillDate = dialogView.findViewById(R.id.et_refill_date);
        Spinner spinnerInstructions = dialogView.findViewById(R.id.spinner_instructions);
        Button btnSave = dialogView.findViewById(R.id.btn_save);

        // Prefill current values
        etMedicationName.setText(prescription.getMedicationName());
        etDosage.setText(prescription.getDosage());
        etStartDate.setText(prescription.getStartDate());
        etEndDate.setText(prescription.getEndDate());
        etRefillDate.setText(prescription.getRefillDate());
        setUpDatePicker(etStartDate);
        setUpDatePicker(etEndDate);
        setUpDatePicker(etRefillDate);

        // Initialize spinners with options and prefill
        ArrayAdapter<CharSequence> frequencyAdapter = ArrayAdapter.createFromResource(context, R.array.frequency_array, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(frequencyAdapter);
        int frequencyPosition = frequencyAdapter.getPosition(prescription.getFrequency());
        spinnerFrequency.setSelection(frequencyPosition);

        ArrayAdapter<CharSequence> instructionsAdapter = ArrayAdapter.createFromResource(context, R.array.instructions_array, android.R.layout.simple_spinner_item);
        instructionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstructions.setAdapter(instructionsAdapter);
        int instructionsPosition = instructionsAdapter.getPosition(prescription.getInstructions());
        spinnerInstructions.setSelection(instructionsPosition);

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(v -> {
            prescription.setMedicationName(etMedicationName.getText().toString());
            prescription.setDosage(etDosage.getText().toString());
            prescription.setFrequency(spinnerFrequency.getSelectedItem().toString());
            prescription.setStartDate(etStartDate.getText().toString());
            prescription.setEndDate(etEndDate.getText().toString());
            prescription.setRefillDate(etRefillDate.getText().toString());
            prescription.setInstructions(spinnerInstructions.getSelectedItem().toString());

            new Thread(() -> {
                try {
                    dynamoDBMapper.save(prescription); // Update database
                    ((Activity) context).runOnUiThread(() -> {
                        prescriptions.set(position, prescription); // Update list
                        notifyItemChanged(position); // Notify adapter
                        dialog.dismiss(); // Close dialog
                        Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    ((Activity) context).runOnUiThread(() -> {
                        notifyItemChanged(position);
                        Toast.makeText(context, "Error saving changes", Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        });

        dialog.show();
    }


    private void confirmDeleteMedication(Prescription prescription, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Medication")
                .setMessage("Are you sure you want to delete this medication?")
                .setPositiveButton("Yes", (dialog, which) -> deleteMedication(prescription, position))
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteMedication(Prescription prescription, int position) {
        new Thread(() -> {
            try {
                dynamoDBMapper.delete(prescription); // Delete from database
                ((Activity) context).runOnUiThread(() -> {
                    prescriptions.remove(position); // Remove from list
                    notifyItemRemoved(position); // Notify adapter
                    checkEmptyView(); // Check if the list is empty and show the empty view
                    Toast.makeText(context, "Medication deleted", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                Log.e("MedicationAdapter", "Error deleting medication", e);
                ((Activity) context).runOnUiThread(() -> {
                    notifyItemChanged(position);
                    Toast.makeText(context, "Error deleting medication", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void checkEmptyView() {
        if (prescriptions.isEmpty()) {
            ((Activity) context).findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            ((Activity) context).findViewById(R.id.recycler_view).setVisibility(View.GONE);
        } else {
            ((Activity) context).findViewById(R.id.empty_view).setVisibility(View.GONE);
            ((Activity) context).findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicationName, dosage, frequency, startDate, endDate, refillDate, instructions;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicationName = itemView.findViewById(R.id.medication_name);
            dosage = itemView.findViewById(R.id.dosage);
            frequency = itemView.findViewById(R.id.frequency);
            startDate = itemView.findViewById(R.id.start_date);
            endDate = itemView.findViewById(R.id.end_date);
            refillDate = itemView.findViewById(R.id.refill_date);
            instructions = itemView.findViewById(R.id.instructions);
            editButton = itemView.findViewById(R.id.btn_edit);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }

    private void setUpDatePicker(EditText editText) {
        editText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, selectedYear, selectedMonth, selectedDay) -> {
                editText.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
            }, year, month, day);
            datePickerDialog.show();
        });
    }
}
