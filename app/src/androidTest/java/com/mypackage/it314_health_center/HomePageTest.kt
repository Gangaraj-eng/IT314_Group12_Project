package com.mypackage.it314_health_center

import android.view.Gravity
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.mypackage.it314_health_center.patient_side.ActivityDownloadReports
import com.mypackage.it314_health_center.patient_side.my_prescriptions
import com.mypackage.it314_health_center.videocalling.PatientOnlineConsultation
import org.hamcrest.Matcher
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
class HomePageTest {

    @Test
    fun canNavigate() {
        val activity = ActivityScenario.launch(PatientHomePage::class.java)
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
            .perform(DrawerActions.open());

        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
        //check  is opened
        onView(withId(R.id.drawer_layout)).check(matches(isOpen(Gravity.LEFT)))


        onView(withId(R.id.drawer_layout))
            .perform(DrawerActions.close());
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT)))


    }

    @Test
    fun isNotificationOpen() {
        val activity = ActivityScenario.launch(PatientHomePage::class.java)
        Intents.init()
        onView(withId(R.id.notifications)).perform(click())
        intended(hasComponent(NotificationsActivity::class.java.name))
        onView(withId(R.id.back_btn)).perform(click())
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
        Intents.release()
    }

    @Test
    fun checkAllPages() {

        val activity = ActivityScenario.launch(PatientHomePage::class.java)
        Intents.init()
        onView(withId(R.id.book_appointment_view)).perform(click())
        intended(hasComponent(BookAppointment::class.java.name))
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(2)));
        onView(isRoot()).perform(ViewActions.pressBack());


        onView(withId(R.id.online_consultation_view)).perform(click())
        intended(hasComponent(PatientOnlineConsultation::class.java.name))
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(2)));
        onView(isRoot()).perform(ViewActions.pressBack());

        onView(withId(R.id.update_medical_hist)).perform(click())
        intended(hasComponent(ActivityUpdateMedicalHistory::class.java.name))
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(2)));
        onView(isRoot()).perform(ViewActions.pressBack());

        onView(withId(R.id.download_reports)).perform(click())
        intended(hasComponent(ActivityDownloadReports::class.java.name))
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(2)));
        onView(isRoot()).perform(ViewActions.pressBack());


        onView(withId(R.id.my_prescription_view)).perform(click())
        intended(hasComponent(my_prescriptions::class.java.name))
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(2)));
        onView(isRoot()).perform(ViewActions.pressBack());

        onView(withId(R.id.orderMedicines)).perform(click())
        intended(hasComponent(ActivityOrderMedicines::class.java.name))
        onView(isRoot()).perform(ViewActions.pressBack());
        Intents.release()
    }


    fun waitFor(millis: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "Wait for $millis milliseconds."
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(millis)
            }
        }
    }

}