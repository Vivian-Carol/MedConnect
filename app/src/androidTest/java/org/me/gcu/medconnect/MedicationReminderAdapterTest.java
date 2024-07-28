//package org.me.gcu.medconnect;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.me.gcu.medconnect.R;
//import org.me.gcu.medconnect.adapters.MedicationReminderAdapter;
//import org.me.gcu.medconnect.models.Prescription;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class MedicationReminderAdapterTest {
//
//    @Test
//    public void testAdapterBindsDataToViews() {
//        List<Prescription> prescriptions = new ArrayList<>();
//        Prescription prescription = new Prescription();
//        prescription.setMedicationName("Ibuprofen");
//        prescription.setDosage("200mg");
//        prescriptions.add(prescription);
//
//        MedicationReminderAdapter adapter = new MedicationReminderAdapter(prescriptions, mock(Context.class));
//        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(mock(ViewGroup.class), 0);
//        adapter.onBindViewHolder((MedicationReminderAdapter.ViewHolder) viewHolder, 0);
//
//        TextView medicationName = viewHolder.itemView.findViewById(R.id.medication_name);
//        assertEquals("Ibuprofen", medicationName.getText().toString());
//    }
//}
