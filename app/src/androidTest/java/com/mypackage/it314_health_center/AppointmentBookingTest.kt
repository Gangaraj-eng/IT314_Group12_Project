package com.mypackage.it314_health_center

import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.util.*
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
class AppointmentBookingTest {
            @Test
            fun AcheckOnlineAppointment()
            {
                val act=ActivityScenario.launch(BookAppointment::class.java)

                val year=Calendar.getInstance().get(Calendar.YEAR)
                val monthOfYear=Calendar.getInstance().get(Calendar.MONTH)+1
                val dayOfMonth=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                Log.d("123", "$year/$monthOfYear/$dayOfMonth")
                onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(4)));
                onView(withId(R.id.edtDate)).perform(click())
                onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
                    PickerActions.setDate(year,monthOfYear,dayOfMonth)
                )
                onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
                onView(withText("OK")).perform(click())
                onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
                onView(withId(R.id.edtTime)).perform(click())
                onView(withClassName(Matchers.equalTo(TimePicker::class.java.name))).perform(
                    PickerActions.setTime(Calendar.getInstance().get(Calendar.HOUR),Calendar.getInstance().get(Calendar.MINUTE)+2)
                )
                onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
                onView(withText("OK")).perform(click())
                onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
                onView(withId(R.id.problemDescription)).perform(typeText("General Health Problem"),
                    closeSoftKeyboard()
                )
                onView(withText("Online")).perform(click())
                onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
                onView(withId(R.id.btnBookAppointment)).perform(click())
                onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
            }

            @Test
            fun cancelAppointment()
            {
                val act=ActivityScenario.launch(BookAppointment::class.java)
                onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(4)));
                onView(withId(R.id.cancel_booking)).perform(click())

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