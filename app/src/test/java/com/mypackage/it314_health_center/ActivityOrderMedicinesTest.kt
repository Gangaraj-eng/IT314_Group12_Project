package com.mypackage.it314_health_center

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule
import org.junit.Test

class ActivityOrderMedicinesTest {

    @get:Rule
    val asrule = activityScenarioRule<ActivityOrderMedicines>()

    @Test
    fun test_upload_picture() {
        onView(withId(R.id.capture_picture)).perform(click())
    }

}
