package org.me.gcu.medconnect.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.me.gcu.medconnect.R;
import org.me.gcu.medconnect.adapters.MedicationSearchAdapter;
import org.me.gcu.medconnect.models.Pharmacy;
import org.me.gcu.medconnect.utils.FilterUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchResultsFragment extends Fragment {

    private RecyclerView recyclerView;
    private MedicationSearchAdapter medicationAdapter;
    private List<Pharmacy> searchResults = new ArrayList<>();
    private String searchQuery;
    private Spinner regionSpinner;
    private Spinner sortSpinner;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        progressBar = view.findViewById(R.id.progress_bar);

        if (getArguments() != null) {
            searchResults = getArguments().getParcelableArrayList("searchResults");
            searchQuery = getArguments().getString("searchQuery");
        }

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        medicationAdapter = new MedicationSearchAdapter(searchResults, searchQuery);
        recyclerView.setAdapter(medicationAdapter);

        // Set up sort and filter spinners
        regionSpinner = view.findViewById(R.id.region_spinner);
        sortSpinner = view.findViewById(R.id.sort_spinner);
        initializeSpinners(regionSpinner, sortSpinner);

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

        // Set up back button
        View goBackContainer = view.findViewById(R.id.go_back_container);
        goBackContainer.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void initializeSpinners(Spinner regionSpinner, Spinner sortSpinner) {
        // Initialize region spinner
        List<String> regionList = new ArrayList<>();
        regionList.add(getString(R.string.region_hint));
        regionList.addAll(Arrays.asList(getResources().getStringArray(R.array.regions_array)));

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(
                getContext(), R.layout.spinner_item, regionList);
        regionAdapter.setDropDownViewResource(R.layout.spinner_item);
        regionSpinner.setAdapter(regionAdapter);

        // Initialize sort spinner
        List<String> sortOptions = new ArrayList<>();
        sortOptions.add(getString(R.string.sort_by_price_hint));
        sortOptions.addAll(Arrays.asList(getResources().getStringArray(R.array.sort_array)));

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(
                getContext(), R.layout.spinner_item, sortOptions);
        sortAdapter.setDropDownViewResource(R.layout.spinner_item);
        sortSpinner.setAdapter(sortAdapter);
    }

    private void filterAndSortResults() {
        showLoadingIndicator();
        new Thread(() -> {
            String selectedRegion = regionSpinner.getSelectedItem() != null ? regionSpinner.getSelectedItem().toString() : "";
            String selectedSortOption = sortSpinner.getSelectedItem() != null ? sortSpinner.getSelectedItem().toString() : "";

            Log.d("SearchResultsFragment", "Filtering results for region: " + selectedRegion + ", sort option: " + selectedSortOption);

            List<Pharmacy> filteredResults = new ArrayList<>();

            // Filter by region
            for (Pharmacy pharmacy : searchResults) {
                if (selectedRegion.equals(getString(R.string.region_hint)) || pharmacy.getPharmacyAddress().contains(selectedRegion)) {
                    filteredResults.add(pharmacy);
                }
            }

            // Sort by price if applicable
            if (selectedSortOption.equals("Low to High")) {
                Log.d("SearchResultsFragment", "Sorting by price: Low to High");
                filteredResults = FilterUtils.sortByPrice(filteredResults, searchQuery);
            } else if (selectedSortOption.equals("High to Low")) {
                Log.d("SearchResultsFragment", "Sorting by price: High to Low");
                filteredResults = FilterUtils.sortByPrice(filteredResults, searchQuery);
                Collections.reverse(filteredResults);
            }

            if (isAdded()) {
                List<Pharmacy> finalFilteredResults = filteredResults;
                getActivity().runOnUiThread(() -> {
                    Log.d("SearchResultsFragment", "Filtered results count: " + finalFilteredResults.size());
                    medicationAdapter.updateData(finalFilteredResults, searchQuery);
                    hideLoadingIndicator();
                });
            }
        }).start();
    }

    private void showLoadingIndicator() {
        getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
    }

    private void hideLoadingIndicator() {
        getActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
    }
}
