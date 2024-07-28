//package org.me.gcu.medconnect;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.lifecycle.Lifecycle;
//import androidx.navigation.NavController;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.test.core.app.ActivityScenario;
//import androidx.test.core.app.ApplicationProvider;
//
//import com.google.ar.core.Config;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.controllers.HomeController;
//import org.me.gcu.medconnect.fragments.HomeFragment;
//import org.me.gcu.medconnect.models.Prescription;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.robolectric.annotation.Config;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//@RunWith(MockitoJUnitRunner.class)
//@Config(sdk = 29)
//public class HomeFragmentTest {
//    @Mock private LayoutInflater mockInflater;
//    @Mock private ViewGroup mockContainer;
//    @Mock private View mockFragmentView;
//    @Mock
//    private TextView mockWelcomeTextView;
//    @Mock
//    private RecyclerView mockReminderRecyclerView;
//    @Mock
//    private RecyclerView mockMedicationRecyclerView;
//    @Mock
//    private TextView mockViewMoreReminders;
//    @Mock private TextView mockViewMoreMedications;
//    @Mock private NavController mockNavController;
//
//    private HomeFragment fragment;
//    private SharedPreferences mockSharedPreferences;
//    private SharedPreferences.Editor mockEditor;
//    private Bundle savedInstanceState;
//
//    @Before
//    public void setup() {
//        mockSharedPreferences = mock(SharedPreferences.class);
//        mockEditor = mock(SharedPreferences.Editor.class);
//        savedInstanceState = new Bundle();
//
//        when(mockContainer.getContext()).thenReturn(ApplicationProvider.getApplicationContext());
//        when(mockInflater.inflate(R.layout.fragment_home, mockContainer, false)).thenReturn(mockFragmentView);
//        when(mockFragmentView.findViewById(R.id.welcomeTextView)).thenReturn(mockWelcomeTextView);
//        when(mockFragmentView.findViewById(R.id.reminderRecyclerView)).thenReturn(mockReminderRecyclerView);
//        when(mockFragmentView.findViewById(R.id.medicationRecyclerView)).thenReturn(mockMedicationRecyclerView);
//        when(mockFragmentView.findViewById(R.id.viewMoreReminders)).thenReturn(mockViewMoreReminders);
//        when(mockFragmentView.findViewById(R.id.viewMoreMedications)).thenReturn(mockViewMoreMedications);
//        when(mockContainer.getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
//        when(mockSharedPreferences.getString("USERNAME", null)).thenReturn("John");
//        when(mockSharedPreferences.getString("USER_ID", null)).thenReturn("1234");
//
//        fragment = new HomeFragment();
//        fragment.onCreateView(mockInflater, mockContainer, savedInstanceState);
//    }
//
//    @Test
//    public void testUsernameNotNull() {
//        verify(mockWelcomeTextView).setText("Hi John!");
//        verify(mockNavController, never()).navigate(anyInt());
//    }
//
//    @Test
//    public void testUserIDNotNull() {
//        verify(mockSharedPreferences).getString("USER_ID", null);
//    }
//
//    @Test
//    public void testOnRemindersFetchedEmpty() {
//        fragment.onRemindersFetched(Collections.emptyList());
//        verify(mockReminderRecyclerView, never()).setAdapter(any());
//    }
//
//    @Test
//    public void testOnMedicationsFetchedNotEmpty() {
//        ArrayList<Prescription> prescriptions = new ArrayList<>();
//        prescriptions.add(new Prescription());
//        fragment.onMedicationsFetched(prescriptions);
//        verify(mockMedicationRecyclerView).setAdapter(any());
//    }
//}
