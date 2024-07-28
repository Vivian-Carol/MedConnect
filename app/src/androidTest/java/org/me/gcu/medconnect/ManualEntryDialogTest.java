//package org.me.gcu.medconnect;
//
//import androidx.fragment.app.testing.FragmentScenario;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.me.gcu.medconnect.fragments.ManualEntryDialog;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.replaceText;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class ManualEntryDialogTest {
//
//    @Test
//    public void testSavePrescription() {
//        FragmentScenario.launchInContainer(ManualEntryDialog.class);
//
//        onView(withId(R.id.et_medication_name)).perform(typeText("Ibuprofen"));
//        onView(withId(R.id.et_dosage)).perform(typeText("200mg"));
//        onView(withId(R.id.et_start_date)).perform(click());
//        // Add date picker actions here
//        onView(withId(R.id.et_end_date)).perform(click());
//        // Add date picker actions here
//        onView(withId(R.id.et_refill_date)).perform(click());
//        // Add date picker actions here
//
//        onView(withId(R.id.btn_save)).perform(click());
//        // Validate saving with mock or check UI state change
//    }
//}
//
