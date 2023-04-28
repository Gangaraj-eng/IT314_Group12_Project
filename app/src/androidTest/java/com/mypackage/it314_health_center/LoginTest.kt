package com.mypackage.it314_health_center

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.mypackage.it314_health_center.startups.Login
import com.mypackage.it314_health_center.startups.Signup
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginTest {

    @Test
    fun check_valid_Login() {
        val activity = ActivityScenario.launch(Login::class.java)
        onView(withId(R.id.radio_email_login)).perform(click())
        onView(withId(R.id.email_input)).perform(typeText("gangarajbopparam@gmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("abc@123"))
        onView(withId(R.id.email_login_button)).check(matches(isDisplayed()))
        onView(withId(R.id.email_login_button)).perform(click())
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(3)));
        onView(withId(R.id.book_appointment_view)).check(matches(isDisplayed()))

    }


    @Test
    fun check_invalid_login() {
        ActivityScenario.launch(Login::class.java)
        onView(withId(R.id.radio_email_login)).perform(click())
        onView(withId(R.id.email_input)).perform(typeText("abasdfsdf"));
        onView(withId(R.id.password_input)).perform(typeText("sgsdfasdf"))
        onView(withId(R.id.email_login_button)).check(matches(isDisplayed()))
        onView(withId(R.id.email_login_button)).perform(click())
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(3)));
        onView(withId(R.id.failed_title)).check(matches(isDisplayed()))
    }

    @Test
    fun can_go_toSingup() {
        ActivityScenario.launch(Login::class.java)
        Intents.init()
        onView(withId(R.id.signupText)).perform(click())
        intended(hasComponent(Signup::class.java.name))
        Intents.release()
        onView(isRoot()).perform(waitFor(TimeUnit.SECONDS.toMillis(2)));

    }

    //        onView(withId(R.id.update_medical_hist)).perform(click())
//        onView(withId(R.id.choose_file)).check(matches(isDisplayed()))
//        onView(withId(R.id.choose_file)).perform(click())
//        onView(withText("Choose Image")).check(matches(isDisplayed()))
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