package com.mypackage.it314_health_center

import com.mypackage.it314_health_center.adapters.ValidationFunctions
import com.mypackage.it314_health_center.startups.Login
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AdaptersTest {
    private lateinit var validationFunctions: ValidationFunctions;

    @Before
    fun setUp() {
        // initializing the variables
        validationFunctions = ValidationFunctions
    }

    @Test
    fun test_valid_email() {
        val email1 = "gangarajbopparam@gmail.com"
        val email2 = "thisIsAnEmailId"
        val output1 = Login.Companion.isValidMail(email1)
        val output2 = Login.Companion.isValidMail(email2)
        assertEquals(true, output1)
        assertEquals(false, output2)
    }

    @Test
    fun test_valid_mobile() {
        val mobile1 = "6301231332"
        val mobile2 = "2342"
        val mobile3 = "23423rwe242"
        val output1 = Login.Companion.isValidMobile(mobile1)
        val output2 = Login.Companion.isValidMobile(mobile2)
        val output3 = Login.Companion.isValidMobile(mobile3)
        assertEquals(true, output1)
        assertEquals(false, output2)
        assertEquals(false, output3)
    }

    @Test
    fun testTimetoString() {
        val t = System.currentTimeMillis()
        val expected = "04:58 PM" // current time
        val actual = validationFunctions.convertTimeInMillisToString(t)
        assertEquals(expected, actual)
    }

    @Test
    fun testDateToString() {
        val t = System.currentTimeMillis()
        val expected = "19/04/2023"
        val actual = validationFunctions.convertDateInMillisToString(t)
        assertEquals(expected, actual)
    }

}