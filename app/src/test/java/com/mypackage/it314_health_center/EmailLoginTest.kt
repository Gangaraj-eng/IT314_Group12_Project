package com.mypackage.it314_health_center

import com.mypackage.it314_health_center.startups.Login
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class EmailLoginTest {

     private val testEmails=ArrayList<String>()
     private val testPasswords=ArrayList<String>()
     private val ExpectedOutputs=ArrayList<Boolean>()


     @Before
     fun setup()
     {
           // initalizing email test cases
            testEmails.add("devkabra@gmail.com")
            testEmails.add("helloWorldgmail.com")
            testEmails.add("nipun41@gmail.com")
            testEmails.add("pabloEsco@yahoo.com")

            // initalizing mobile numbers
            testPasswords.add("abcde@1")
            testPasswords.add("abc23df")
            testPasswords.add("xy@!sf")
            testPasswords.add("abd")

            ExpectedOutputs.add(true)
            ExpectedOutputs.add(false)
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(false)
     }

     @Test
     fun email_test_case_1()
     {
            val testCaseIndex=1
            val testCaseMail=testEmails[testCaseIndex]
           val testCasePassword=testPasswords[testCaseIndex]
           val expected=ExpectedOutputs[testCaseIndex]
          val actual= Login.validate_emailLogin(testCaseMail,testCasePassword)
           assertEquals(expected,actual)
     }


     @Test
     fun email_test_case_2()
     {
            val testCaseIndex=1
            val testCaseMail=testEmails[testCaseIndex]
           val testCasePassword=testPasswords[testCaseIndex]
           val expected=ExpectedOutputs[testCaseIndex]
          val actual= Login.validate_emailLogin(testCaseMail,testCasePassword)
           assertEquals(expected,actual)
     }


     @Test
     fun email_test_case_3()
     {
         val testCaseIndex=2
            val testCaseMail=testEmails[testCaseIndex]
           val testCasePassword=testPasswords[testCaseIndex]
           val expected=ExpectedOutputs[testCaseIndex]
          val actual= Login.validate_emailLogin(testCaseMail,testCasePassword)
           assertEquals(expected,actual)
     }


     @Test
     fun email_test_case_4()
     {
           val testCaseIndex=3
            val testCaseMail=testEmails[testCaseIndex]
           val testCasePassword=testPasswords[testCaseIndex]
           val expected=ExpectedOutputs[testCaseIndex]
          val actual= Login.validate_emailLogin(testCaseMail,testCasePassword)
           assertEquals(expected,actual)
     }

    @Test
     fun email_test_case_5()
     {
           val testCaseIndex=3
            val testCaseMail=testEmails[testCaseIndex]
           val testCasePassword=testPasswords[testCaseIndex]
           val expected=ExpectedOutputs[testCaseIndex]
          val actual= Login.validate_emailLogin(testCaseMail,testCasePassword)
           assertEquals(expected,actual)
     }

    @Test
     fun email_test_case_6()
     {
           val testCaseIndex=3
            val testCaseMail=testEmails[testCaseIndex]
           val testCasePassword=testPasswords[testCaseIndex]
           val expected=ExpectedOutputs[testCaseIndex]
          val actual= Login.validate_emailLogin(testCaseMail,testCasePassword)
           assertEquals(expected,actual)
     }

    @Test
     fun email_test_case_7()
     {
           val testCaseIndex=3
            val testCaseMail=testEmails[testCaseIndex]
           val testCasePassword=testPasswords[testCaseIndex]
           val expected=ExpectedOutputs[testCaseIndex]
          val actual= Login.validate_emailLogin(testCaseMail,testCasePassword)
           assertEquals(expected,actual)
     }


}