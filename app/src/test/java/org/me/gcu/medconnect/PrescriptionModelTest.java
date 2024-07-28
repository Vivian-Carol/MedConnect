//package org.me.gcu.medconnect;
//
//import org.junit.Test;
//import org.me.gcu.medconnect.models.Prescription;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//public class PrescriptionModelTest {
//
//    @Test
//    public void testGettersAndSetters() {
//        Prescription prescription = new Prescription();
//        prescription.setMedicationName("Ibuprofen");
//        prescription.setDosage("200mg");
//        prescription.setFrequency("Twice a day");
//
//        assertEquals("Ibuprofen", prescription.getMedicationName());
//        assertEquals("200mg", prescription.getDosage());
//        assertEquals("Twice a day", prescription.getFrequency());
//    }
//
//    @Test
//    public void testDataValidation() {
//        Prescription prescription = new Prescription();
//
//        // Test valid data
//        prescription.setDosage("200mg");
//        assertEquals("200mg", prescription.getDosage());
//
//        // Test invalid data
//        try {
//            prescription.setDosage("");
//            fail("Should have thrown an exception for invalid dosage");
//        } catch (IllegalArgumentException e) {
//            // Expected exception
//        }
//    }
//}
