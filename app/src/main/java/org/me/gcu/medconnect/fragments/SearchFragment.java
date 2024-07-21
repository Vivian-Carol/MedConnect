//package org.me.gcu.medconnect.fragments;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.adapters.MedicationSearchAdapter;
//import org.me.gcu.medconnect.models.Pharmacy;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//import org.me.gcu.medconnect.network.RegionService;
//import org.me.gcu.medconnect.network.RetrofitClient;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class SearchFragment extends Fragment {
//
//    private EditText searchQuery;
//    private Spinner regionSpinner;
//    private Spinner sortSpinner;
//    private Button searchButton;
//    private RecyclerView recyclerView;
//    private MedicationSearchAdapter medicationAdapter;
//    private DynamoDBMapper dynamoDBMapper;
//    private RegionService regionService;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_search, container, false);
//
//        searchQuery = view.findViewById(R.id.search_query);
//        regionSpinner = view.findViewById(R.id.region_spinner);
//        sortSpinner = view.findViewById(R.id.sort_spinner);
//        searchButton = view.findViewById(R.id.search_button);
//        recyclerView = view.findViewById(R.id.recycler_view);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        medicationAdapter = new MedicationSearchAdapter(new ArrayList<>());
//        recyclerView.setAdapter(medicationAdapter);
//
//        dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
//        regionService = RetrofitClient.getRetrofitInstance().create(RegionService.class);
//
//        fetchRegions();
//
//        searchButton.setOnClickListener(v -> searchMedications());
//
//        return view;
//    }
//
//    private void fetchRegions() {
//        regionService.getRegions().enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<String> regions = response.body();
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, regions);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    regionSpinner.setAdapter(adapter);
//                } else {
//                    Toast.makeText(getContext(), "Failed to load regions", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void searchMedications() {
//        String query = searchQuery.getText().toString();
//        String selectedRegion = regionSpinner.getSelectedItem() != null ? regionSpinner.getSelectedItem().toString() : "";
//        String selectedSortOption = sortSpinner.getSelectedItem() != null ? sortSpinner.getSelectedItem().toString() : "";
//
//        Log.d("SearchFragment", "Search query: " + query);
//        Log.d("SearchFragment", "Selected region: " + selectedRegion);
//        Log.d("SearchFragment", "Selected sort option: " + selectedSortOption);
//
//        new Thread(() -> {
//            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
//            expressionAttributeValues.put(":val", new AttributeValue().withS(query));
//            expressionAttributeValues.put(":region", new AttributeValue().withS(selectedRegion));
//
//            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
//                    .withFilterExpression("contains(Medications.MedicationName, :val) and contains(PharmacyAddress, :region)")
//                    .withExpressionAttributeValues(expressionAttributeValues);
//
//            PaginatedScanList<Pharmacy> scanResult = dynamoDBMapper.scan(Pharmacy.class, scanExpression);
//
//            if (scanResult.isEmpty()) {
//                Log.d("SearchFragment", "No results found for query: " + query + " in region: " + selectedRegion);
//                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show());
//            } else {
//                List<Pharmacy> result = new ArrayList<>(scanResult);
//                Log.d("SearchFragment", "Results found: " + result.size());
//
//                // Sort the results if needed
//                if (selectedSortOption.equals("Price")) {
//                    result.sort(Comparator.comparingDouble(pharmacy -> {
//                        if (pharmacy.getMedications() != null) {
//                            return pharmacy.getMedications().stream()
//                                    .filter(med -> med.getMedicationName() != null && med.getMedicationName().equals(query))
//                                    .mapToDouble(med -> {
//                                        try {
//                                            return Double.parseDouble(med.getPrice().replace(" KES", ""));
//                                        } catch (NumberFormatException e) {
//                                            return Double.MAX_VALUE;
//                                        }
//                                    })
//                                    .min()
//                                    .orElse(Double.MAX_VALUE);
//                        }
//                        return Double.MAX_VALUE;
//                    }));
//                }
//
//                getActivity().runOnUiThread(() -> {
//                    medicationAdapter.updateData(result);
//                    Toast.makeText(getContext(), "Found " + result.size() + " pharmacies", Toast.LENGTH_SHORT).show();
//                });
//            }
//        }).start();
//    }
//}

//package org.me.gcu.medconnect.fragments;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.adapters.MedicationSearchAdapter;
//import org.me.gcu.medconnect.models.Pharmacy;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//import org.me.gcu.medconnect.network.RegionService;
//import org.me.gcu.medconnect.network.RetrofitClient;
//import org.me.gcu.medconnect.utils.FilterUtils;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class SearchFragment extends Fragment {
//
//    private EditText searchQuery;
//    private Spinner regionSpinner;
//    private Spinner sortSpinner;
//    private Button searchButton;
//    private RecyclerView recyclerView;
//    private MedicationSearchAdapter medicationAdapter;
//    private DynamoDBMapper dynamoDBMapper;
//    private RegionService regionService;
//
//    private List<Pharmacy> searchResults = new ArrayList<>();
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_search, container, false);
//
//        searchQuery = view.findViewById(R.id.search_query);
//        regionSpinner = view.findViewById(R.id.region_spinner);
//        sortSpinner = view.findViewById(R.id.sort_spinner);
//        searchButton = view.findViewById(R.id.search_button);
//        recyclerView = view.findViewById(R.id.recycler_view);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        medicationAdapter = new MedicationSearchAdapter(new ArrayList<>());
//        recyclerView.setAdapter(medicationAdapter);
//
//        dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
//        regionService = RetrofitClient.getRetrofitInstance().create(RegionService.class);
//
//        fetchRegions();
//
//        searchButton.setOnClickListener(v -> {
//            searchMedications();
//        });
//
//        return view;
//    }
//
//    private void fetchRegions() {
//        regionService.getRegions().enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<String> regions = response.body();
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, regions);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    regionSpinner.setAdapter(adapter);
//                } else {
//                    Toast.makeText(getContext(), "Failed to load regions", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void searchMedications() {
//        String query = searchQuery.getText().toString();
//
//        Log.d("SearchFragment", "Search query: " + query);
//
//        new Thread(() -> {
//            try {
//                Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
//                expressionAttributeValues.put(":val", new AttributeValue().withS(query));
//
//                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
//                        .withFilterExpression("contains(Medications.MedicationName, :val)")
//                        .withExpressionAttributeValues(expressionAttributeValues);
//
//                PaginatedScanList<Pharmacy> scanResult = dynamoDBMapper.scan(Pharmacy.class, scanExpression);
//
//                searchResults.clear();
//                searchResults.addAll(scanResult);
//                Log.d("SearchFragment", "Results found: " + searchResults.size());
//
//                getActivity().runOnUiThread(() -> {
//                    filterAndSortResults();
//                    if (searchResults.isEmpty()) {
//                        Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(), "Found " + searchResults.size() + " pharmacies", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } catch (Exception e) {
//                Log.e("SearchFragment", "Error during search", e);
//                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error during search", Toast.LENGTH_SHORT).show());
//            }
//        }).start();
//    }
//
//    private void filterAndSortResults() {
//        String selectedRegion = regionSpinner.getSelectedItem() != null ? regionSpinner.getSelectedItem().toString() : "";
//        String selectedSortOption = sortSpinner.getSelectedItem() != null ? sortSpinner.getSelectedItem().toString() : "";
//
//        List<Pharmacy> filteredResults = new ArrayList<>();
//
//        // Filter by region
//        for (Pharmacy pharmacy : searchResults) {
//            if (pharmacy.getPharmacyAddress().contains(selectedRegion)) {
//                filteredResults.add(pharmacy);
//            }
//        }
//
//        // Sort by price if needed
//        if (selectedSortOption.equals("Price")) {
//            filteredResults = FilterUtils.sortByPrice(filteredResults);
//        }
//
//        medicationAdapter.updateData(filteredResults);
//    }
//}
//package org.me.gcu.medconnect.fragments;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.adapters.MedicationSearchAdapter;
//import org.me.gcu.medconnect.models.Pharmacy;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//import org.me.gcu.medconnect.network.RegionService;
//import org.me.gcu.medconnect.network.RetrofitClient;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class SearchFragment extends Fragment {
//
//    private EditText searchQuery;
//    private Spinner regionSpinner;
//    private Spinner sortSpinner;
//    private Button searchButton;
//    private RecyclerView recyclerView;
//    private MedicationSearchAdapter medicationAdapter;
//    private DynamoDBMapper dynamoDBMapper;
//    private RegionService regionService;
//
//    private List<Pharmacy> searchResults = new ArrayList<>();
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_search, container, false);
//
//        searchQuery = view.findViewById(R.id.search_query);
//        regionSpinner = view.findViewById(R.id.region_spinner);
//        sortSpinner = view.findViewById(R.id.sort_spinner);
//        searchButton = view.findViewById(R.id.search_button);
//        recyclerView = view.findViewById(R.id.recycler_view);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        medicationAdapter = new MedicationSearchAdapter(new ArrayList<>());
//        recyclerView.setAdapter(medicationAdapter);
//
//        dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
//        regionService = RetrofitClient.getRetrofitInstance().create(RegionService.class);
//
//        fetchRegions();
//
//        searchButton.setOnClickListener(v -> searchMedications());
//
//        // Add listeners to the spinners to filter and sort results dynamically
//        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                filterAndSortResults();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
//
//        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                filterAndSortResults();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
//
//        return view;
//    }
//
//    private void fetchRegions() {
//        regionService.getRegions().enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<String> regions = response.body();
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, regions);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    regionSpinner.setAdapter(adapter);
//                } else {
//                    Toast.makeText(getContext(), "Failed to load regions", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void searchMedications() {
//        String query = searchQuery.getText().toString();
//
//        Log.d("SearchFragment", "Search query: " + query);
//
//        new Thread(() -> {
//            try {
//                Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
//                expressionAttributeValues.put(":val", new AttributeValue().withS(query));
//
//                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
//                        .withFilterExpression("contains(Medications.MedicationName.S, :val)")
//                        .withExpressionAttributeValues(expressionAttributeValues);
//
//                PaginatedScanList<Pharmacy> scanResult = dynamoDBMapper.scan(Pharmacy.class, scanExpression);
//
//                searchResults.clear();
//                searchResults.addAll(scanResult);
//                Log.d("Scan result: ",searchResults.toString());
//                Log.d("SearchFragment", "Results found: " + searchResults.size());
//
//                getActivity().runOnUiThread(() -> {
//                    filterAndSortResults();
//                    if (searchResults.isEmpty()) {
//                        Toast.makeText(getContext(), "No results found for query: " + query, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(), "Found " + searchResults.size() + " pharmacies", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } catch (Exception e) {
//                Log.e("SearchFragment", "Error during search", e);
//                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error during search", Toast.LENGTH_SHORT).show());
//            }
//        }).start();
//    }
//
//    private void filterAndSortResults() {
//        String query = searchQuery.getText().toString();
//        String selectedRegion = regionSpinner.getSelectedItem() != null ? regionSpinner.getSelectedItem().toString() : "";
//        String selectedSortOption = sortSpinner.getSelectedItem() != null ? sortSpinner.getSelectedItem().toString() : "";
//
//        List<Pharmacy> filteredResults = new ArrayList<>();
//
//        for (Pharmacy pharmacy : searchResults) {
//            if (pharmacy.getPharmacyAddress().contains(selectedRegion)) {
//                filteredResults.add(pharmacy);
//            }
//        }
//
//        if (selectedSortOption.equals("Price")) {
//            filteredResults.sort(Comparator.comparingDouble(pharmacy -> {
//                return pharmacy.getMedications().stream()
//                        .filter(med -> med.getMedicationName().equals(query))
//                        .mapToDouble(med -> Double.parseDouble(med.getPrice().replace(" KES", "")))
//                        .min()
//                        .orElse(Double.MAX_VALUE);
//            }));
//        }
//
//        medicationAdapter.updateData(filteredResults);
//    }
//}



package org.me.gcu.medconnect.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.adapters.MedicationSearchAdapter;
import org.me.gcu.medconnect.models.Medication;
import org.me.gcu.medconnect.models.Pharmacy;
import org.me.gcu.medconnect.network.AwsClientProvider;
import org.me.gcu.medconnect.network.RegionService;
import org.me.gcu.medconnect.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private EditText searchQuery;
    private Spinner regionSpinner;
    private Spinner sortSpinner;
    private Button searchButton;
    private RecyclerView recyclerView;
    private MedicationSearchAdapter medicationAdapter;
    private DynamoDBMapper dynamoDBMapper;
    private RegionService regionService;

    private List<Pharmacy> searchResults = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchQuery = view.findViewById(R.id.search_query);
        regionSpinner = view.findViewById(R.id.region_spinner);
        sortSpinner = view.findViewById(R.id.sort_spinner);
        searchButton = view.findViewById(R.id.search_button);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        medicationAdapter = new MedicationSearchAdapter(new ArrayList<>());
        recyclerView.setAdapter(medicationAdapter);

        dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
        regionService = RetrofitClient.getRetrofitInstance().create(RegionService.class);

        fetchRegions();

        searchButton.setOnClickListener(v -> searchMedications());

        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterAndSortResults();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterAndSortResults();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return view;
    }

    private void fetchRegions() {
        regionService.getRegions().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> regions = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, regions);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    regionSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to load regions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMedications() {
        String query = searchQuery.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a medication name", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("SearchFragment", "Search query: " + query);

        new Thread(() -> {
            try {
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                PaginatedScanList<Pharmacy> scanResult = dynamoDBMapper.scan(Pharmacy.class, scanExpression);

                searchResults.clear();
                Log.d("SearchFragment", "Total pharmacies scanned: " + scanResult.size());

                for (Pharmacy pharmacy : scanResult) {
                    boolean hasMedication = false;
                    List<Medication> medicationsList = pharmacy.getMedications();

                    Log.d("SearchFragment", "Pharmacy ID: " + pharmacy.getPharmacyID() + " - Number of medications: " + medicationsList.size());

                    for (Medication medication : medicationsList) {
                        Log.d("Medication Details", medication.toString());

                        String medicationName = medication.getMedicationName();
                        Log.d("SearchFragment", "Checking medication: " + medicationName);

                        if (medicationName.equalsIgnoreCase(query)) {
                            hasMedication = true;
                            Log.d("SearchFragment", "Medication found: " + medicationName);
                            break;
                        }
                    }

                    if (hasMedication) {
                        searchResults.add(pharmacy);
                        Log.d("SearchFragment", "Pharmacy added: " + pharmacy.getPharmacyID());
                    }
                }

                Log.d("SearchFragment", "Results found: " + searchResults.size());

                getActivity().runOnUiThread(() -> {
//                    filterAndSortResults();
                    if (searchResults.isEmpty()) {
                        Toast.makeText(getContext(), "No results found for query: " + query, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Found " + searchResults.size() + " pharmacies", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.e("SearchFragment", "Error during search", e);
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error during search", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }




    private void filterAndSortResults() {
        String query = searchQuery.getText().toString().trim();
        String selectedRegion = regionSpinner.getSelectedItem() != null ? regionSpinner.getSelectedItem().toString() : "";
        String selectedSortOption = sortSpinner.getSelectedItem() != null ? sortSpinner.getSelectedItem().toString() : "";

        List<Pharmacy> filteredResults = new ArrayList<>();

        for (Pharmacy pharmacy : searchResults) {
            if (pharmacy.getPharmacyAddress().contains(selectedRegion)) {
                filteredResults.add(pharmacy);
            }
        }

        if (selectedSortOption.equals("Price")) {
            filteredResults.sort(Comparator.comparingDouble(pharmacy -> {
                return pharmacy.getMedications().stream()
                        .filter(med -> med.getMedicationName().equalsIgnoreCase(query))
                        .mapToDouble(med -> Double.parseDouble(med.getPrice().replace(" KES", "")))
                        .min()
                        .orElse(Double.MAX_VALUE);
            }));
        }

        medicationAdapter.updateData(filteredResults);
    }
}

