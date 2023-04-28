package com.mypackage.it314_health_center

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.mypackage.it314_health_center.patient_side.ActivitySettings
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class SettingsTest {

    @Test
    fun check_Settngs() {
        val activity = ActivityScenario.launch(ActivitySettings::class.java)

        onView(withId(R.id.mode_switch)).check(matches(isDisplayed()))
        onView(withId(R.id.notifications_switch)).check(matches(isDisplayed()))
        onView(withId(R.id.mode_switch)).perform(click())
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
        onView(withId(R.id.mode_switch)).perform(click())
        onView(withId(R.id.notifications_switch)).perform(click())
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
        onView(withId(R.id.notifications_switch)).perform(click())
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));
        onView(withId(R.id.help_view_text)).perform(click())
        Intents.init()
//            intended(hasComponent(ActivityHelp::class.java.name))
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(1)));

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