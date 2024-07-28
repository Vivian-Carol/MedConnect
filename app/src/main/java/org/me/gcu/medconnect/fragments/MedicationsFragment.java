////package org.me.gcu.medconnect.fragments;
////
////import android.content.Context;
////import android.content.SharedPreferences;
////import android.os.Bundle;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.TextView;
////
////import androidx.annotation.NonNull;
////import androidx.annotation.Nullable;
////import androidx.fragment.app.Fragment;
////import androidx.fragment.app.FragmentActivity;
////import androidx.recyclerview.widget.LinearLayoutManager;
////import androidx.recyclerview.widget.RecyclerView;
////
////import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
////import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
////
////import org.me.gcu.medconnect.R;
////import org.me.gcu.medconnect.adapters.MedicationAdapter;
////import org.me.gcu.medconnect.models.Prescription;
////import org.me.gcu.medconnect.network.AwsClientProvider;
////
////import java.util.ArrayList;
////import java.util.List;
////
////public class MedicationsFragment extends Fragment {
////
////    private RecyclerView recyclerView;
////    private MedicationAdapter adapter;
////    private TextView emptyView;
////    private String userId;
////
////    @Nullable
////    @Override
////    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.fragment_medications, container, false);
////        recyclerView = view.findViewById(R.id.recycler_view);
////        emptyView = view.findViewById(R.id.empty_view);
////
////        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////
////        // Get the userId from arguments or SharedPreferences
////        if (getArguments() != null && getArguments().getString("USER_ID") != null) {
////            userId = getArguments().getString("USER_ID");
////        } else {
////            userId = getUserIdFromPreferences();
////        }
////
////        if (userId != null) {
////            fetchPrescriptionsFromDynamoDB(userId);
////        } else {
////            displayEmptyView();
////        }
////
////        return view;
////    }
////
////    private void fetchPrescriptionsFromDynamoDB(String userId) {
////        new Thread(() -> {
////            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
////            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
////            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);
////
////            // Filter prescriptions for the logged-in user
////            List<Prescription> userPrescriptions = new ArrayList<>();
////            for (Prescription prescription : allPrescriptions) {
////                if (prescription.getUserId().equals(userId)) {
////                    userPrescriptions.add(prescription);
////                }
////            }
////
////            FragmentActivity activity = getActivity();
////            if (activity != null && isAdded()) {
////                activity.runOnUiThread(() -> displayPrescriptions(userPrescriptions));
////            }
////        }).start();
////    }
////
////    private void displayPrescriptions(List<Prescription> prescriptions) {
////        if (prescriptions.isEmpty()) {
////            displayEmptyView();
////        } else {
////            hideEmptyView();
////            adapter = new MedicationAdapter(prescriptions, getContext());
////            recyclerView.setAdapter(adapter);
////        }
////    }
////
////    private void displayEmptyView() {
////        emptyView.setVisibility(View.VISIBLE);
////        recyclerView.setVisibility(View.GONE);
////    }
////
////    private void hideEmptyView() {
////        emptyView.setVisibility(View.GONE);
////        recyclerView.setVisibility(View.VISIBLE);
////    }
////
////    private String getUserIdFromPreferences() {
////        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
////        return sharedPreferences.getString("userId", null);
////    }
////}
//
//package org.me.gcu.medconnect.fragments;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.adapters.MedicationAdapter;
//import org.me.gcu.medconnect.models.Prescription;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MedicationsFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private MedicationAdapter adapter;
//    private TextView emptyView;
//    private String userId;
//    private static final String TAG = "MedicationsFragment";
//    private static final String PREFS_NAME = "user_prefs";
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_medications, container, false);
//        recyclerView = view.findViewById(R.id.recycler_view);
//        emptyView = view.findViewById(R.id.empty_view);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Get the userId from arguments or SharedPreferences
//        if (getArguments() != null && getArguments().getString("USER_ID") != null) {
//            userId = getArguments().getString("USER_ID");
//        } else {
//            userId = getUserIdFromPreferences();
//        }
//
//        if (userId != null) {
//            fetchPrescriptionsFromDynamoDB(userId);
//        } else {
//            displayEmptyView();
//        }
//
//        return view;
//    }
//
//    private void fetchPrescriptionsFromDynamoDB(String userId) {
//        new Thread(() -> {
//            DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
//            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//            List<Prescription> allPrescriptions = dynamoDBMapper.scan(Prescription.class, scanExpression);
//
//            // Filter prescriptions for the logged-in user
//            List<Prescription> userPrescriptions = new ArrayList<>();
//            for (Prescription prescription : allPrescriptions) {
//                if (prescription.getUserId().equals(userId)) {
//                    userPrescriptions.add(prescription);
//                }
//            }
//
//            FragmentActivity activity = getActivity();
//            if (activity != null && isAdded()) {
//                activity.runOnUiThread(() -> displayPrescriptions(userPrescriptions));
//            }
//        }).start();
//    }
//
//    private void displayPrescriptions(List<Prescription> prescriptions) {
//        if (prescriptions.isEmpty()) {
//            displayEmptyView();
//        } else {
//            hideEmptyView();
//            adapter = new MedicationAdapter(prescriptions, getContext());
//            recyclerView.setAdapter(adapter);
//        }
//    }
//
//    private void displayEmptyView() {
//        emptyView.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);
//    }
//
//    private void hideEmptyView() {
//        emptyView.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);
//    }
//
//    private String getUserIdFromPreferences() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString("USER_ID", null);
//    }
//}
//package org.me.gcu.medconnect.fragments;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.adapters.MedicationAdapter;
//import org.me.gcu.medconnect.controllers.MedicationsController;
//import org.me.gcu.medconnect.models.Prescription;
//
//import java.util.List;
//
//public class MedicationsFragment extends Fragment implements MedicationsController.MedicationsControllerListener {
//
//    private RecyclerView recyclerView;
//    private MedicationAdapter adapter;
//    private TextView emptyView;
//    private String userId;
//    private MedicationsController controller;
//    private static final String PREFS_NAME = "user_prefs";
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_medications, container, false);
//        recyclerView = view.findViewById(R.id.recycler_view);
//        emptyView = view.findViewById(R.id.empty_view);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Initialize the controller
//        controller = new MedicationsController(this);
//
//        // Get the userId from arguments or SharedPreferences
//        if (getArguments() != null && getArguments().getString("USER_ID") != null) {
//            userId = getArguments().getString("USER_ID");
//        } else {
//            userId = getUserIdFromPreferences();
//        }
//
//        if (userId != null) {
//            controller.fetchPrescriptions(userId);
//        } else {
//            displayEmptyView();
//        }
//
//        return view;
//    }
//
//    @Override
//    public void onPrescriptionsFetched(List<Prescription> prescriptions) {
//        if (prescriptions.isEmpty()) {
//            displayEmptyView();
//        } else {
//            hideEmptyView();
//            adapter = new MedicationAdapter(prescriptions, getContext());
//            recyclerView.setAdapter(adapter);
//        }
//    }
//
//    private void displayEmptyView() {
//        emptyView.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);
//    }
//
//    private void hideEmptyView() {
//        emptyView.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);
//    }
//
//    private String getUserIdFromPreferences() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString("USER_ID", null);
//    }
//}

//package org.me.gcu.medconnect.fragments;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.adapters.MedicationAdapter;
//import org.me.gcu.medconnect.controllers.MedicationsController;
//import org.me.gcu.medconnect.models.Prescription;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//import org.me.gcu.medconnect.utils.FileUtils;
//import org.me.gcu.medconnect.utils.OCRUtils;
//import org.me.gcu.medconnect.utils.PDFUtils;
//import org.me.gcu.medconnect.utils.TextExtractionUtils;
//
//import java.io.File;
//import java.util.List;
//import java.util.UUID;
//
//public class MedicationsFragment extends Fragment implements MedicationsController.MedicationsControllerListener {
//
//    private RecyclerView recyclerView;
//    private MedicationAdapter adapter;
//    private TextView emptyView;
//    private FloatingActionButton btnAddPrescription;
//    private String userId;
//    private MedicationsController controller;
//    private static final String PREFS_NAME = "user_prefs";
//
//    private final ActivityResultLauncher<Intent> pickPdfLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
//                    Uri fileUri = result.getData().getData();
//                    File file = FileUtils.getFileFromUri(getContext(), fileUri);
//                    if (file != null) {
//                        uploadFileToS3(file);
//                    } else {
//                        Toast.makeText(getContext(), "Failed to get file from Uri", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//    private final ActivityResultLauncher<Intent> scanImageLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getExtras() != null) {
//                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
//                    processScannedImage(bitmap);
//                }
//            });
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_medications, container, false);
//        recyclerView = view.findViewById(R.id.recycler_view);
//        emptyView = view.findViewById(R.id.empty_view);
//        btnAddPrescription = view.findViewById(R.id.btn_add_prescription);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Initialize the controller
//        controller = new MedicationsController(this);
//
//        // Get the userId from arguments or SharedPreferences
//        if (getArguments() != null && getArguments().getString("USER_ID") != null) {
//            userId = getArguments().getString("USER_ID");
//        } else {
//            userId = getUserIdFromPreferences();
//        }
//
//        if (userId != null) {
//            controller.fetchPrescriptions(userId);
//        } else {
//            displayEmptyView();
//        }
//
//        btnAddPrescription.setOnClickListener(v -> showPrescriptionOptions());
//
//        return view;
//    }
//
//    @Override
//    public void onPrescriptionsFetched(List<Prescription> prescriptions) {
//        if (prescriptions.isEmpty()) {
//            displayEmptyView();
//        } else {
//            hideEmptyView();
//            adapter = new MedicationAdapter(prescriptions, getContext());
//            recyclerView.setAdapter(adapter);
//        }
//    }
//
//    private void displayEmptyView() {
//        emptyView.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);
//    }
//
//    private void hideEmptyView() {
//        emptyView.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);
//    }
//
//    private String getUserIdFromPreferences() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString("USER_ID", null);
//    }
//
//    private void showPrescriptionOptions() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Add Prescription")
//                .setItems(new String[]{"Upload", "Scan", "Manual Entry"}, (dialog, which) -> {
//                    switch (which) {
//                        case 0:
//                            uploadPrescription();
//                            break;
//                        case 1:
//                            scanPrescription();
//                            break;
//                        case 2:
//                            manualEntryPrescription();
//                            break;
//                    }
//                });
//        builder.create().show();
//    }
//
//    private void uploadPrescription() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/pdf");
//        pickPdfLauncher.launch(intent);
//    }
//
//    private void scanPrescription() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        scanImageLauncher.launch(intent);
//    }
//
//    private void manualEntryPrescription() {
//        ManualEntryDialog dialog = new ManualEntryDialog();
//        dialog.show(getChildFragmentManager(), "ManualEntryDialog");
//    }
//
//    private void uploadFileToS3(File file) {
//        TransferUtility transferUtility = TransferUtility.builder()
//                .context(getContext())
//                .s3Client(AwsClientProvider.getS3Client())
//                .build();
//        TransferObserver uploadObserver = transferUtility.upload("medconnect-prescriptions", file.getName(), file);
//
//        uploadObserver.setTransferListener(new TransferListener() {
//            @Override
//            public void onStateChanged(int id, TransferState state) {
//                if (state == TransferState.COMPLETED) {
//                    processUploadedPrescription(file);
//                }
//            }
//
//            @Override
//            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//                // Update progress bar if needed
//            }
//
//            @Override
//            public void onError(int id, Exception ex) {
//                // Handle errors
//            }
//        });
//    }
//
//    private void processUploadedPrescription(File file) {
//        String text = PDFUtils.extractTextFromPDF(file);
//        if (text != null) {
//            savePrescriptionToDynamoDB(text);
//        } else {
//            Toast.makeText(getContext(), "Failed to extract text from PDF", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void processScannedImage(Bitmap bitmap) {
//        String text = OCRUtils.extractTextFromImage(getContext(), bitmap);
//        if (text != null) {
//            savePrescriptionToDynamoDB(text);
//        } else {
//            Toast.makeText(getContext(), "Failed to extract text from image", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void savePrescriptionToDynamoDB(String text) {
//        String medicationName = TextExtractionUtils.extractMedicationName(text);
//        String dosage = TextExtractionUtils.extractDosage(text);
//        String frequency = TextExtractionUtils.extractFrequency(text);
//        String startDate = TextExtractionUtils.extractStartDate(text);
//        String endDate = TextExtractionUtils.extractEndDate(text);
//        String refillDate = TextExtractionUtils.extractRefillDate(text);
//        String instructions = TextExtractionUtils.extractInstructions(text);
//
//        Prescription prescription = new Prescription();
//        prescription.setId(UUID.randomUUID().toString());
//        prescription.setUserId(userId); // Set the userId
//        prescription.setMedicationName(medicationName);
//        prescription.setDosage(dosage);
//        prescription.setFrequency(frequency);
//        prescription.setStartDate(startDate);
//        prescription.setEndDate(endDate);
//        prescription.setRefillDate(refillDate);
//        prescription.setInstructions(instructions);
//
//        new Thread(() -> {
//            DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(AwsClientProvider.getDynamoDBClient());
//            dynamoDBMapper.save(prescription);
//        }).start();
//    }
//}

package org.me.gcu.medconnect.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.adapters.MedicationAdapter;
import org.me.gcu.medconnect.controllers.MedicationsController;
import org.me.gcu.medconnect.models.Prescription;
import org.me.gcu.medconnect.network.AwsClientProvider;
import org.me.gcu.medconnect.utils.FileUtils;
import org.me.gcu.medconnect.utils.OCRUtils;
import org.me.gcu.medconnect.utils.PDFUtils;
import org.me.gcu.medconnect.utils.TextExtractionUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class MedicationsFragment extends Fragment implements MedicationsController.MedicationsControllerListener, ManualEntryDialog.ManualEntryDialogListener {

    private RecyclerView recyclerView;
    private MedicationAdapter adapter;
    private TextView emptyView;
    private FloatingActionButton btnAddPrescription;
    private String userId;
    private MedicationsController controller;
    private static final String PREFS_NAME = "user_prefs";

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medications, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        emptyView = view.findViewById(R.id.empty_view);
        btnAddPrescription = view.findViewById(R.id.btn_add_prescription);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the controller
        controller = new MedicationsController(this);

        // Get the userId from arguments or SharedPreferences
        if (getArguments() != null && getArguments().getString("USER_ID") != null) {
            userId = getArguments().getString("USER_ID");
        } else {
            userId = getUserIdFromPreferences();
        }

        if (userId != null) {
            controller.fetchPrescriptions(userId);
        } else {
            displayEmptyView();
        }

        btnAddPrescription.setOnClickListener(v -> showPrescriptionOptions());

        return view;
    }

    @Override
    public void onPrescriptionsFetched(List<Prescription> prescriptions) {
        if (prescriptions.isEmpty()) {
            displayEmptyView();
        } else {
            hideEmptyView();
            if (adapter == null) {
                adapter = new MedicationAdapter(prescriptions, getContext());
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updatePrescriptions(prescriptions);
            }
        }
    }

    private void displayEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private String getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("USER_ID", null);
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
        dialog.setListener(this); // Set the listener
        dialog.show(getChildFragmentManager(), "ManualEntryDialog");
    }

    private void uploadFileToS3(File file) {
        TransferUtility transferUtility = TransferUtility.builder()
                .context(getContext())
                .s3Client(AwsClientProvider.getS3Client())
                .build();
        TransferObserver uploadObserver = transferUtility.upload("medconnect-prescriptions", file.getName(), file);

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    processUploadedPrescription(file);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                // Update progress bar if needed
            }

            @Override
            public void onError(int id, Exception ex) {
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
        prescription.setUserId(userId); // Set the userId
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
            getActivity().runOnUiThread(this::refreshPrescriptions);
        }).start();
    }

    private void refreshPrescriptions() {
        if (userId != null) {
            controller.fetchPrescriptions(userId);
        }
    }

    @Override
    public void onPrescriptionAdded() {
        refreshPrescriptions();
    }
}
