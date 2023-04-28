package com.mypackage.it314_health_center

import com.mypackage.it314_health_center.startups.Login
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MobileLoginTest {
    private val testMobiles = ArrayList<String>()
    private val testOTPs = ArrayList<String>()
    private val ExpectedOutputs = ArrayList<Boolean>()


    @Before
    fun setup() {
        // initalizing email test cases
        testMobiles.add("924567889")
        testMobiles.add("3858686857")
        testMobiles.add("55865949494")
        testMobiles.add("6725044254")
        testMobiles.add("8032972475")
        testMobiles.add("9312931843")

        // initalizing mobile numbers
        testOTPs.add("123456")
        testOTPs.add("094356")
        testOTPs.add("092345")
        testOTPs.add("13452")
        testOTPs.add("232324")
        testOTPs.add("4523223")

        ExpectedOutputs.add(false)
        ExpectedOutputs.add(true)
        ExpectedOutputs.add(false)
        ExpectedOutputs.add(false)
        ExpectedOutputs.add(true)
        ExpectedOutputs.add(false)

    }

    @Test
    fun mobile_test_case_1() {
        val testCaseIndex = 0
        val testCaseMail = testMobiles[testCaseIndex]
        val testCasePassword = testOTPs[testCaseIndex]
        val expected = ExpectedOutputs[testCaseIndex]
        val actual = Login.validate_mobileLogin(testCaseMail, testCasePassword)
        assertEquals(expected, actual)
    }

    @Test
    fun mobile_test_case_2() {
        val testCaseIndex = 1
        val testCaseMail = testMobiles[testCaseIndex]
        val testCasePassword = testOTPs[testCaseIndex]
        val expected = ExpectedOutputs[testCaseIndex]
        val actual = Login.validate_mobileLogin(testCaseMail, testCasePassword)
        assertEquals(expected, actual)
    }

    @Test
    fun mobile_test_case_3() {
        val testCaseIndex = 2
        val testCaseMail = testMobiles[testCaseIndex]
        val testCasePassword = testOTPs[testCaseIndex]
        val expected = ExpectedOutputs[testCaseIndex]
        val actual = Login.validate_mobileLogin(testCaseMail, testCasePassword)
        assertEquals(expected, actual)
    }

    @Test
    fun mobile_test_case_4() {
        val testCaseIndex = 3
        val testCaseMail = testMobiles[testCaseIndex]
        val testCasePassword = testOTPs[testCaseIndex]
        val expected = ExpectedOutputs[testCaseIndex]
        val actual = Login.validate_mobileLogin(testCaseMail, testCasePassword)
        assertEquals(expected, actual)
    }

    @Test
    fun mobile_test_case_5() {
        val testCaseIndex = 4
        val testCaseMail = testMobiles[testCaseIndex]
        val testCasePassword = testOTPs[testCaseIndex]
        val expected = ExpectedOutputs[testCaseIndex]
        val actual = Login.validate_mobileLogin(testCaseMail, testCasePassword)
        assertEquals(expected, actual)
    }

    @Test
    fun mobile_test_case_6() {
        val testCaseIndex = 5
        val testCaseMail = testMobiles[testCaseIndex]
        val testCasePassword = testOTPs[testCaseIndex]
        val expected = ExpectedOutputs[testCaseIndex]
        val actual = Login.validate_mobileLogin(testCaseMail, testCasePassword)
        assertEquals(expected, actual)
    }



}