package org.me.gcu.medconnect.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;

import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.adapters.MedicationSearchAdapter;
import org.me.gcu.medconnect.models.Medication;
import org.me.gcu.medconnect.models.Pharmacy;
import org.me.gcu.medconnect.network.AwsClientProvider;
import org.me.gcu.medconnect.utils.FilterUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchQuery;
    private Spinner regionSpinner;
    private Spinner sortSpinner;
    private Button searchButton;
    private RecyclerView recyclerView;
    private MedicationSearchAdapter medicationAdapter;
    private DynamoDBMapper dynamoDBMapper;
    private ProgressBar progressBar;

    private List<Pharmacy> allPharmacies = new ArrayList<>();
    private List<Pharmacy> searchResults = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchQuery = view.findViewById(R.id.search_query);
        regionSpinner = view.findViewById(R.id.region_spinner);
        sortSpinner = view.findViewById(R.id.sort_spinner);
        searchButton = view.findViewById(R.id.search_button);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        medicationAdapter = new MedicationSearchAdapter(new ArrayList<>(), "");
        recyclerView.setAdapter(medicationAdapter);

        dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();

        initializeSpinners();
        fetchDataFromDynamoDB();

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

    private void initializeSpinners() {
        List<String> regionList = new ArrayList<>();
        regionList.add(getString(R.string.region_hint));
        regionList.addAll(Arrays.asList(getResources().getStringArray(R.array.regions_array)));

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(
                getContext(), R.layout.spinner_item, regionList);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionAdapter);

        // Initialize Sort Spinner
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.sort_array, R.layout.spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);
    }

    private void fetchDataFromDynamoDB() {
        showLoadingIndicator();
        new Thread(() -> {
            try {
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                PaginatedScanList<Pharmacy> scanResult = dynamoDBMapper.scan(Pharmacy.class, scanExpression);
                allPharmacies.clear();
                allPharmacies.addAll(scanResult);

                if (isAdded()) {
                    getActivity().runOnUiThread(() -> {
                        Log.d("SearchFragment", "Fetched " + allPharmacies.size() + " pharmacies from DynamoDB");
                        Toast.makeText(getContext(), "Data fetched successfully", Toast.LENGTH_SHORT).show();
                        hideLoadingIndicator();
                    });
                }
            } catch (Exception e) {
                Log.e("SearchFragment", "Error fetching data from DynamoDB", e);
                if (isAdded()) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                        hideLoadingIndicator();
                    });
                }
            }
        }).start();
    }

    private void searchMedications() {
        String query = searchQuery.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a medication name", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("SearchFragment", "Search query: " + query);
        showLoadingIndicator();

        new Thread(() -> {
            searchResults.clear();
            for (Pharmacy pharmacy : allPharmacies) {
                boolean hasMedication = false;
                for (Medication medication : pharmacy.getMedications()) {
                    if (medication.getMedicationName().equalsIgnoreCase(query)) {
                        hasMedication = true;
                        break;
                    }
                }
                if (hasMedication) {
                    searchResults.add(pharmacy);
                }
            }

            if (isAdded()) {
                getActivity().runOnUiThread(() -> {
                    // Navigate to SearchResultsFragment with results
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("searchResults", new ArrayList<>(searchResults));
                    bundle.putString("searchQuery", query);

                    NavHostFragment.findNavController(SearchFragment.this)
                            .navigate(R.id.action_searchFragment_to_searchResultsFragment, bundle);
                    hideLoadingIndicator();
                });
            }
        }).start();
    }

    private void filterAndSortResults() {
        showLoadingIndicator();
        new Thread(() -> {
            String query = searchQuery.getText().toString().trim();
            String selectedRegion = regionSpinner.getSelectedItem() != null ? regionSpinner.getSelectedItem().toString() : "";
            String selectedSortOption = sortSpinner.getSelectedItem() != null ? sortSpinner.getSelectedItem().toString() : "";

            Log.d("SearchFragment", "Filtering results for region: " + selectedRegion + ", sort option: " + selectedSortOption);

            List<Pharmacy> filteredResults = new ArrayList<>();

            // Filter by region
            for (Pharmacy pharmacy : searchResults) {
                if (selectedRegion.equals(getString(R.string.region_hint)) || pharmacy.getPharmacyAddress().contains(selectedRegion)) {
                    filteredResults.add(pharmacy);
                }
            }

            // Sort by price if applicable
            if (selectedSortOption.equals("Low to High")) {
                Log.d("SearchFragment", "Sorting by price: Low to High");
                filteredResults = FilterUtils.sortByPrice(filteredResults, query);
            } else if (selectedSortOption.equals("High to Low")) {
                Log.d("SearchFragment", "Sorting by price: High to Low");
                filteredResults = FilterUtils.sortByPrice(filteredResults, query);
                Collections.reverse(filteredResults);
            }

            if (isAdded()) {
                List<Pharmacy> finalFilteredResults = filteredResults;
                getActivity().runOnUiThread(() -> {
                    Log.d("SearchFragment", "Filtered results count: " + finalFilteredResults.size());
                    medicationAdapter.updateData(finalFilteredResults, query);
                    hideLoadingIndicator();
                });
            }
        }).start();
    }

    private void showLoadingIndicator() {
        if (progressBar != null) {
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
        }
    }

    private void hideLoadingIndicator() {
        if (progressBar != null) {
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
        }
    }
}
