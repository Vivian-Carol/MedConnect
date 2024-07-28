//package org.me.gcu.medconnect;
//
//import androidx.fragment.app.testing.FragmentScenario;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.me.gcu.medconnect.fragments.MedicationRemindersFragment;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class MedicationRemindersFragmentTest {
//
//    @Test
//    public void testFetchesAndDisplaysReminders() {
//        FragmentScenario.launchInContainer(MedicationRemindersFragment.class);
//
//        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
//    }
//}
//
