package com.mypackage.it314_health_center

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BookAppointmentTest {
        val testDates=ArrayList<String>()
        val testTimes=ArrayList<String>()
        val testTypes=ArrayList<String>()
        val ExpectedOutputs=ArrayList<Boolean>()

        @Before
        fun setup()
        {
                testDates.add("29/04/2023")
                testDates.add("27/04/2023")
                testDates.add("24/04/2023")
                testDates.add("27/04/2023")
                testDates.add("27/04/2023")
                testDates.add("27/04/2023")
                testDates.add("09/11/2023")
                testDates.add("01/05/2023")
                testDates.add("31/04/2023")
                testDates.add("26/04/2023")
                testDates.add("27/04/2023")
                testDates.add("28/04/2023")
                testDates.add("29/04/2023")
                testDates.add("27/04/2023")
                testDates.add("01/05/2023")


                testTimes.add("05:40")
                testTimes.add("14:50")
                testTimes.add("09:30")
                testTimes.add("21:48")
                testTimes.add("17:30")
                testTimes.add("14:20")
                testTimes.add("18:39")
                testTimes.add("19:20")
                testTimes.add("22:00")
                testTimes.add("05:40")
                testTimes.add("17:50")
                testTimes.add("04:30")
                testTimes.add("17:29")
                testTimes.add("17:30")
                testTimes.add("17:31")


                testTypes.add("offline")
                testTypes.add("offline")
                testTypes.add("online")
                testTypes.add("offline")
                testTypes.add("online")
                testTypes.add("online")
                testTypes.add("offline")
                testTypes.add("online")
                testTypes.add("none")
                testTypes.add("offline")
                testTypes.add("offline")
                testTypes.add("offline")
                testTypes.add("offline")
                testTypes.add("offline")
                testTypes.add("offline")

                ExpectedOutputs.add(true)
                ExpectedOutputs.add(false)
                ExpectedOutputs.add(false)
                ExpectedOutputs.add(true)
                ExpectedOutputs.add(false)
                ExpectedOutputs.add(false)
                ExpectedOutputs.add(true)
                ExpectedOutputs.add(true)
                ExpectedOutputs.add(false)
                ExpectedOutputs.add(false)
                ExpectedOutputs.add(true)
                ExpectedOutputs.add(true)
                ExpectedOutputs.add(true)
                ExpectedOutputs.add(false)
                ExpectedOutputs.add(true)
        }

        @Test
        fun apt_test_1()
        {
                val testIndex=0
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_2()
        {
                val testIndex=1
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_3()
        {
                val testIndex=2
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_4()
        {
                val testIndex=3
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_5()
        {
                val testIndex=4
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_6()
        {
                val testIndex=5
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_7()
        {
                val testIndex=6
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_8()
        {
                val testIndex=7
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_9()
        {
                val testIndex=8
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_10()
        {
                val testIndex=9
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_11()
        {
                val testIndex=10
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_12()
        {
                val testIndex=11
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_13()
        {
                val testIndex=12
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_14()
        {
                val testIndex=13
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }

        @Test
        fun apt_test_15()
        {
                val testIndex=14
                val date=testDates[testIndex]
                val time=testTimes[testIndex]
                val type=testTypes[testIndex]
                val expected=ExpectedOutputs[testIndex]
                val actual=BookAppointment.validateAppointmentDetails(date.trim(),time.trim(),type.trim())
                assertEquals(expected,actual)
        }


}