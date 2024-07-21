package org.me.gcu.medconnect.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;
import org.me.gcu.medconnect.utils.FileUtils;
import org.me.gcu.medconnect.utils.OCRUtils;
import org.me.gcu.medconnect.utils.PDFUtils;
import org.me.gcu.medconnect.utils.TextExtractionUtils;

import java.io.File;
import java.util.UUID;

public class PrescriptionsFragment extends Fragment {

    private ProgressBar progressBar;

    private final ActivityResultLauncher<Intent> pickPdfLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                    Uri fileUri = result.getData().getData();
                    File file = FileUtils.getFileFromUri(getContext(), fileUri);
                    if (file != null) {
                        uploadFileToS3(file);
                    } else {
                        Toast.makeText(getContext(), "Failed to get file from Uri", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private final ActivityResultLauncher<Intent> scanImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getExtras() != null) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    processScannedImage(bitmap);
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prescriptions, container, false);

        Button addPrescriptionButton = view.findViewById(R.id.btn_add_prescription);
        progressBar = view.findViewById(R.id.progress_bar);

        addPrescriptionButton.setOnClickListener(v -> showPrescriptionOptions());

        return view;
    }

    private void showPrescriptionOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Prescription")
                .setItems(new String[]{"Upload", "Scan", "Manual Entry"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            uploadPrescription();
                            break;
                        case 1:
                            scanPrescription();
                            break;
                        case 2:
                            manualEntryPrescription();
                            break;
                    }
                });
        builder.create().show();
    }

    private void uploadPrescription() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        pickPdfLauncher.launch(intent);
    }

    private void scanPrescription() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        scanImageLauncher.launch(intent);
    }

    private void manualEntryPrescription() {
        ManualEntryDialog dialog = new ManualEntryDialog();
        dialog.show(getChildFragmentManager(), "ManualEntryDialog");
    }

    private void uploadFileToS3(File file) {
        TransferUtility transferUtility = TransferUtility.builder()
                .context(getContext())
                .s3Client(AwsClientProvider.getS3Client())
                .build();
        TransferObserver uploadObserver = transferUtility.upload("medconnect-prescriptions", file.getName(), file);

        progressBar.setVisibility(View.VISIBLE);

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    progressBar.setVisibility(View.GONE);
                    processUploadedPrescription(file);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                // Update progress bar
            }

            @Override
            public void onError(int id, Exception ex) {
                progressBar.setVisibility(View.GONE);
                // Handle errors
            }
        });
    }

    private void processUploadedPrescription(File file) {
        String text = PDFUtils.extractTextFromPDF(file);
        if (text != null) {
            savePrescriptionToDynamoDB(text);
        } else {
            Toast.makeText(getContext(), "Failed to extract text from PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void processScannedImage(Bitmap bitmap) {
        String text = OCRUtils.extractTextFromImage(getContext(), bitmap);
        if (text != null) {
            savePrescriptionToDynamoDB(text);
        } else {
            Toast.makeText(getContext(), "Failed to extract text from image", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePrescriptionToDynamoDB(String text) {
        String medicationName = TextExtractionUtils.extractMedicationName(text);
        String dosage = TextExtractionUtils.extractDosage(text);
        String frequency = TextExtractionUtils.extractFrequency(text);
        String startDate = TextExtractionUtils.extractStartDate(text);
        String endDate = TextExtractionUtils.extractEndDate(text);
        String refillDate = TextExtractionUtils.extractRefillDate(text);
        String instructions = TextExtractionUtils.extractInstructions(text);

        Prescription prescription = new Prescription();
        prescription.setId(UUID.randomUUID().toString());
        prescription.setMedicationName(medicationName);
        prescription.setDosage(dosage);
        prescription.setFrequency(frequency);
        prescription.setStartDate(startDate);
        prescription.setEndDate(endDate);
        prescription.setRefillDate(refillDate);
        prescription.setInstructions(instructions);

        new Thread(() -> {
            DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AwsClientProvider.getDynamoDBClient());
            dynamoDBMapper.save(prescription);
        }).start();
    }
}
