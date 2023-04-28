package com.mypackage.it314_health_center

import com.mypackage.it314_health_center.startups.UserDetailsActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.math.exp

class ProfieTest {
        val testNames=ArrayList<String>()
        val testDOB=ArrayList<String>()
        val testGender=ArrayList<String>()
        val testAddress=ArrayList<String>()
        val testCities=ArrayList<String>()
        val testStates=ArrayList<String>()
        val testCountries=ArrayList<String>()
        val testPincodes=ArrayList<String>()
        var ExpectedOutputs=ArrayList<Boolean>()
        @Before
        fun setup()
        {
            testNames.add("")
            testNames.add("harsh")
            testNames.add("dev")
            testNames.add("ria")
            testNames.add("Eksn ")
            testNames.add("refg")
            testNames.add("tyue")
            testNames.add("regina")
            testNames.add("dev")
            testNames.add("guru")
            testNames.add("itra")
            testNames.add("titra")
            testNames.add("nipu")
            testNames.add("dev")
            testNames.add("raju")
            testNames.add("dev")
            testNames.add("raju")
            testNames.add("kavi")

            testDOB.add("12/05/2003")
            testDOB.add("23/06/2004")
            testDOB.add("29/04/2023")
            testDOB.add("12/05/2004")
            testDOB.add("12/05/2004")
            testDOB.add("12/05/2012")
            testDOB.add("05/05/2004")
            testDOB.add("22/05/2008")
            testDOB.add("22/08/2004")
            testDOB.add("12/05/2009")
            testDOB.add("22/05/2012")
            testDOB.add("12/06/2004")
            testDOB.add("12/05/2004")
            testDOB.add("22/12/2004")
            testDOB.add("12/05/2012")
            testDOB.add("22/05/2004")
            testDOB.add("22/05/2004")
            testDOB.add("12/05/2004")

            testGender.add("male")
            testGender.add("male")
            testGender.add("male")
            testGender.add("female")
            testGender.add("male")
            testGender.add("female")
            testGender.add("other")
            testGender.add("female")
            testGender.add("male")
            testGender.add("male")
            testGender.add("female")
            testGender.add("female")
            testGender.add("male")
            testGender.add("male")
            testGender.add("male")
            testGender.add("male")
            testGender.add("male")
            testGender.add("male")


            testAddress.add("E23 rakshamal societies, near holicut")
            testAddress.add("Kalali bridge, somnath, ghazar road, jaki palace")
            testAddress.add("A22 trupti tenmanets, near folji villa")
            testAddress.add("S20 shinestar society, near Padma Kamal")
            testAddress.add("Snow palace, near harkesh nagar, tuis road")
            testAddress.add("E23 rakshamal societies, near holicut")
            testAddress.add("Snow palace, near harkesh nagar, tuis road")
            testAddress.add("Jk road")
            testAddress.add("Snow palace, near harkesh nagar, tuis road")
            testAddress.add("D23 jk road, himmatnagar, near jodhnagar")
            testAddress.add("Snow palace, near harkesh nagar, tuis road")
            testAddress.add("A 34 Rimesh residency, near gh 0 road, shanti palace")
            testAddress.add("Snow palace, near harkesh nagar, tuis road")
            testAddress.add("Snow palace, near harkesh nagar, tuis road")
            testAddress.add("D23 jk road, himmatnagar, near jodhnagar")
            testAddress.add("A 34 Rimesh residency, near gh 0 road, shanti palace ")
            testAddress.add("S20 shinestar society, near Padma Kamal")
            testAddress.add("A 34 Rimesh residency, near gh 0 road, shanti palace ")


            testCities.add("Baroda")
            testCities.add("Halol")
            testCities.add("Himmat")
            testCities.add("Porbandar")
            testCities.add("Surat")
            testCities.add("Halol")
            testCities.add("Baroda")
            testCities.add("Halol")
            testCities.add("Surat")
            testCities.add("")
            testCities.add("Pune")
            testCities.add("Halol")
            testCities.add("Chapora")
            testCities.add("Halol")
            testCities.add("Surat")
            testCities.add("Surat")
            testCities.add("Hyderabad")
            testCities.add("Chennai")

            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Maharashtra")
            testStates.add("")
            testStates.add("Goa")
            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Gujarat")
            testStates.add("Telangana")
            testStates.add("Kerala")

            testPincodes.add("234567")
            testPincodes.add("567890")
            testPincodes.add("123456")
            testPincodes.add("123456")
            testPincodes.add("234567")
            testPincodes.add("345678")
            testPincodes.add("678901")
            testPincodes.add("423952")
            testPincodes.add("123456")
            testPincodes.add("234567")
            testPincodes.add("345678")
            testPincodes.add("563421")
            testPincodes.add("567890")
            testPincodes.add("123456")
            testPincodes.add("4564223")
            testPincodes.add("3456")
            testPincodes.add("123456")
            testPincodes.add("458901")


            testCountries.add("India")
            testCountries.add("australia")
            testCountries.add("new zealand")
            testCountries.add("russia")
            testCountries.add("china")
            testCountries.add("japan")
            testCountries.add("malaysia")
            testCountries.add("singapore")
            testCountries.add("dubai")
            testCountries.add("India")
            testCountries.add("France ")
            testCountries.add("Pakistan")
            testCountries.add("India")
            testCountries.add("Japan")
            testCountries.add("America")
            testCountries.add("Russia")
            testCountries.add("")
            testCountries.add("china ")

            ExpectedOutputs.add(false)
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(false)
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(false)
            ExpectedOutputs.add(false)
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(false)
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(false)
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(false)
            ExpectedOutputs.add(false)
            ExpectedOutputs.add(false)
            ExpectedOutputs.add(true)

        }

        @Test
        fun profile_test_1()
        {
            val testIndex=0
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_2()
        {
            val testIndex=1
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_3()
        {
            val testIndex=2
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_4()
        {
            val testIndex=3
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_5()
        {
            val testIndex=4
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_6()
        {
            val testIndex=5
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_7()
        {
            val testIndex=6
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_8()
        {
            val testIndex=7
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_9()
        {
            val testIndex=8
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_10()
        {
            val testIndex=9
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_11()
        {
            val testIndex=10
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_12()
        {
            val testIndex=11
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_13()
        {
            val testIndex=12
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_14()
        {
            val testIndex=13
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_15()
        {
            val testIndex=14
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_16()
        {
            val testIndex=15
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_17()
        {
            val testIndex=16
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }

        @Test
        fun profile_test_18()
        {
            val testIndex=17
            val name=testNames[testIndex]
            val dob=testDOB[testIndex]
            val gender=testGender[testIndex]
            val addr=testAddress[testIndex]
            val city=testCities[testIndex]
            val country=testCountries[testIndex]
            val state=testStates[testIndex]
            val pincode=testPincodes[testIndex]
            val expected=ExpectedOutputs[testIndex]
            val actual=UserDetailsActivity.validateProfile(name,dob,gender,addr,city
                        ,state,country,pincode)
            assertEquals(expected,actual)
        }



}