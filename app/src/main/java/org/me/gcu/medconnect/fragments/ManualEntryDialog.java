//package org.me.gcu.medconnect.fragments;
//
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.models.Prescription;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//
//import java.util.Calendar;
//import java.util.UUID;
//
//public class ManualEntryDialog extends DialogFragment {
//
//    private static final String TAG = "ManualEntryDialog";
//    private EditText etStartDate, etEndDate, etRefillDate;
//    private Calendar calendar;
//    private String userId;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.dialog_manual_entry, container, false);
//
//        etStartDate = view.findViewById(R.id.et_start_date);
//        etEndDate = view.findViewById(R.id.et_end_date);
//        etRefillDate = view.findViewById(R.id.et_refill_date);
//        Spinner spinnerFrequency = view.findViewById(R.id.spinner_frequency);
//        Spinner spinnerInstructions = view.findViewById(R.id.spinner_instructions);
//        Button btnSave = view.findViewById(R.id.btn_save);
//
//        calendar = Calendar.getInstance();
//
//        etStartDate.setOnClickListener(v -> showDatePickerDialog(etStartDate));
//        etEndDate.setOnClickListener(v -> showDatePickerDialog(etEndDate));
//        etRefillDate.setOnClickListener(v -> showDatePickerDialog(etRefillDate));
//
//        ArrayAdapter<CharSequence> frequencyAdapter = ArrayAdapter.createFromResource(getContext(), R.array.frequency_array, android.R.layout.simple_spinner_item);
//        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerFrequency.setAdapter(frequencyAdapter);
//
//        ArrayAdapter<CharSequence> instructionsAdapter = ArrayAdapter.createFromResource(getContext(), R.array.instructions_array, android.R.layout.simple_spinner_item);
//        instructionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerInstructions.setAdapter(instructionsAdapter);
//
//        // Retrieve userId from shared preferences
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
//        userId = sharedPreferences.getString("USER_ID", null);
//        Log.d(TAG, "Retrieved userId: " + userId);
//
//        btnSave.setOnClickListener(v -> savePrescription(view, spinnerFrequency.getSelectedItem().toString(), spinnerInstructions.getSelectedItem().toString()));
//
//        return view;
//    }
//
//    private void showDatePickerDialog(EditText editText) {
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
//            String date = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
//            editText.setText(date);
//        }, year, month, day);
//
//        datePickerDialog.show();
//    }
//
//    private void savePrescription(View view, String frequency, String instructions) {
//        EditText etMedicationName = view.findViewById(R.id.et_medication_name);
//        EditText etDosage = view.findViewById(R.id.et_dosage);
//
//        String medicationName = etMedicationName.getText().toString();
//        String dosage = etDosage.getText().toString();
//        String startDate = etStartDate.getText().toString();
//        String endDate = etEndDate.getText().toString();
//        String refillDate = etRefillDate.getText().toString();
//
//        // Validate inputs and check for missing fields
//        if (medicationName.isEmpty() || dosage.isEmpty() || frequency.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || refillDate.isEmpty()) {
//            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Create a new Prescription object
//        Prescription prescription = new Prescription();
//        prescription.setId(UUID.randomUUID().toString());
//        prescription.setUserId(userId);
//        prescription.setMedicationName(medicationName);
//        prescription.setDosage(dosage);
//        prescription.setFrequency(frequency);
//        prescription.setStartDate(startDate);
//        prescription.setEndDate(endDate);
//        prescription.setRefillDate(refillDate);
//        prescription.setInstructions(instructions);
//
//        Log.d(TAG, "Saving prescription: " + prescription.toString());
//
//        // Save to DynamoDB
//        new Thread(() -> {
//            try {
//                DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AwsClientProvider.getDynamoDBClient());
//                dynamoDBMapper.save(prescription);
//                Log.d(TAG, "Prescription saved successfully");
//            } catch (Exception e) {
//                Log.e(TAG, "Error saving prescription: " + e.getMessage(), e);
//            }
//        }).start();
//
//        dismiss();
//    }
//}

// ManualEntryDialog.java
package org.me.gcu.medconnect.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.controllers.ManualEntryController;
import org.me.gcu.medconnect.models.Prescription;

import java.util.Calendar;
import java.util.UUID;

public class ManualEntryDialog extends DialogFragment implements ManualEntryController.ManualEntryControllerListener {

    private static final String TAG = "ManualEntryDialog";
    private EditText etStartDate, etEndDate, etRefillDate;
    private Calendar calendar;
    private String userId;
    private ManualEntryController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_manual_entry, container, false);

        etStartDate = view.findViewById(R.id.et_start_date);
        etEndDate = view.findViewById(R.id.et_end_date);
        etRefillDate = view.findViewById(R.id.et_refill_date);
        Spinner spinnerFrequency = view.findViewById(R.id.spinner_frequency);
        Spinner spinnerInstructions = view.findViewById(R.id.spinner_instructions);
        Button btnSave = view.findViewById(R.id.btn_save);

        calendar = Calendar.getInstance();
        controller = new ManualEntryController(this);

        etStartDate.setOnClickListener(v -> showDatePickerDialog(etStartDate));
        etEndDate.setOnClickListener(v -> showDatePickerDialog(etEndDate));
        etRefillDate.setOnClickListener(v -> showDatePickerDialog(etRefillDate));

        ArrayAdapter<CharSequence> frequencyAdapter = ArrayAdapter.createFromResource(getContext(), R.array.frequency_array, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(frequencyAdapter);

        ArrayAdapter<CharSequence> instructionsAdapter = ArrayAdapter.createFromResource(getContext(), R.array.instructions_array, android.R.layout.simple_spinner_item);
        instructionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstructions.setAdapter(instructionsAdapter);

        // Retrieve userId from shared preferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("USER_ID", null);
        Log.d(TAG, "Retrieved userId: " + userId);

        btnSave.setOnClickListener(v -> savePrescription(view, spinnerFrequency.getSelectedItem().toString(), spinnerInstructions.getSelectedItem().toString()));

        return view;
    }

    private void showDatePickerDialog(EditText editText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
            String date = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
            editText.setText(date);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void savePrescription(View view, String frequency, String instructions) {
        EditText etMedicationName = view.findViewById(R.id.et_medication_name);
        EditText etDosage = view.findViewById(R.id.et_dosage);

        String medicationName = etMedicationName.getText().toString();
        String dosage = etDosage.getText().toString();
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        String refillDate = etRefillDate.getText().toString();

        // Validate inputs and check for missing fields
        if (medicationName.isEmpty() || dosage.isEmpty() || frequency.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || refillDate.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Prescription object
        Prescription prescription = new Prescription();
        prescription.setId(UUID.randomUUID().toString());
        prescription.setUserId(userId);
        prescription.setMedicationName(medicationName);
        prescription.setDosage(dosage);
        prescription.setFrequency(frequency);
        prescription.setStartDate(startDate);
        prescription.setEndDate(endDate);
        prescription.setRefillDate(refillDate);
        prescription.setInstructions(instructions);

        Log.d(TAG, "Saving prescription: " + prescription.toString());

        // Save to DynamoDB using the controller
        controller.savePrescription(prescription);
    }

    @Override
    public void onPrescriptionSaved(boolean success) {
        if (success) {
            Toast.makeText(getContext(), "Prescription saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error saving prescription", Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }
}
