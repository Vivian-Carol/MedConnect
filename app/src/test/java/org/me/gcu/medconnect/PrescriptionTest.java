//package org.me.gcu.medconnect;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.me.gcu.medconnect.models.Prescription;
//
//import static org.junit.Assert.assertEquals;
//
//public class PrescriptionTest {
//
//    private Prescription prescription;
//
//    @Before
//    public void setUp() {
//        prescription = new Prescription();
//        prescription.setId("123");
//        prescription.setUserId("user123");
//        prescription.setMedicationName("Ibuprofen");
//        prescription.setDosage("200mg");
//        prescription.setFrequency("Twice a day");
//        prescription.setStartDate("2024-01-01");
//        prescription.setEndDate("2024-01-10");
//        prescription.setRefillDate("2024-01-08");
//        prescription.setInstructions("After meal");
//    }
//
//    @Test
//    public void testPrescriptionFields() {
//        assertEquals("123", prescription.getId());
//        assertEquals("user123", prescription.getUserId());
//        assertEquals("Ibuprofen", prescription.getMedicationName());
//        assertEquals("200mg", prescription.getDosage());
//        assertEquals("Twice a day", prescription.getFrequency());
//        assertEquals("2024-01-01", prescription.getStartDate());
//        assertEquals("2024-01-10", prescription.getEndDate());
//        assertEquals("2024-01-08", prescription.getRefillDate());
//        assertEquals("After meal", prescription.getInstructions());
//    }
//}
