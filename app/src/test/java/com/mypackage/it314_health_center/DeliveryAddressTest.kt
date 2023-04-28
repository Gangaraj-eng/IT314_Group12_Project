package com.mypackage.it314_health_center

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.math.exp

class DeliveryAddressTest {
    val testCities=ArrayList<String>()
    val testStates=ArrayList<String>()
    val testAddrs=ArrayList<String>()
    val testPincodes=ArrayList<String>()
    val ExpectedOutputs=ArrayList<Boolean>()

    @Before
    fun setup()
    {
            testCities.add("")
            testCities.add("Chapora")
            testCities.add("Pune")
            testCities.add("Tezpur")
            testCities.add("Patna")
            testCities.add("Gulmarg")
            testCities.add("Kochi")
            testCities.add("Chennai")
            testCities.add("Warangal")
            testCities.add("Chapora")
            testCities.add("Gulmarg")
            testCities.add("Patna")
            testCities.add("Kochi")
            testCities.add("Pune")
            testCities.add("Pune")


            testStates.add("gujarat")
            testStates.add("goa")
            testStates.add("")
            testStates.add("assam")
            testStates.add("bihar")
            testStates.add("kashmir")
            testStates.add("kerala")
            testStates.add("Tamil nadu")
            testStates.add("Telangana ")
            testStates.add("Telangana")
            testStates.add("Tamil nadu")
            testStates.add("kerala")
            testStates.add("bihar")
            testStates.add("bihar")
            testStates.add("assam")

            testAddrs.add("A 34 Rimesh residency, near gh 0 road, shanti palace")
            testAddrs.add("E23 rakshamal societies, near holicut")
            testAddrs.add("E23 rakshamal societies, near holicut")
            testAddrs.add("Kalali bridge, somnath, ghazar road, jaki palace")
            testAddrs.add("Jk road")
            testAddrs.add("Snow palace, near harkesh nagar, tuis road")
            testAddrs.add("A22 trupti tenmanets, near folji villa")
            testAddrs.add("S20 shinestar society, near Padma Kamal")
            testAddrs.add("D23 jk road, himmatnagar, near jodhnagar")
            testAddrs.add("Jk road gh0, near")
            testAddrs.add("Mk road gh34, near lt")
            testAddrs.add("Lok road gh56, near halol")
            testAddrs.add("Jk road gh0, near halol, kalali bridge")
            testAddrs.add("D23 jk road, himmatnagar, near jodhnagar")
            testAddrs.add("Kalali bridge, somnath, ghazar road, jaki palace")

            testPincodes.add("343223")
            testPincodes.add("390009")
            testPincodes.add("234242")
            testPincodes.add("234232")
            testPincodes.add("534234")
            testPincodes.add("385535")
            testPincodes.add("308224")
            testPincodes.add("23422342")
            testPincodes.add("232")
            testPincodes.add("795124")
            testPincodes.add("745721")
            testPincodes.add("124568")
            testPincodes.add("352342")
            testPincodes.add("23423")
            testPincodes.add("2342532")

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
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(true)
            ExpectedOutputs.add(false)
            ExpectedOutputs.add(false)
    }

    @Test
    fun addr_test_1()
    {
        val testIndex=0
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_2()
    {
        val testIndex=1
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_3()
    {
        val testIndex=2
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_4()
    {
        val testIndex=3
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_5()
    {
        val testIndex=4
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_6()
    {
        val testIndex=5
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_7()
    {
        val testIndex=6
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_8()
    {
        val testIndex=7
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_9()
    {
        val testIndex=8
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_10()
    {
        val testIndex=9
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_11()
    {
        val testIndex=10
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_12()
    {
        val testIndex=11
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_13()
    {
        val testIndex=12
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_14()
    {
        val testIndex=13
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }

@Test
    fun addr_test_15()
    {
        val testIndex=14
        val city=testCities[testIndex]
        val state=testStates[testIndex]
        val addr=testAddrs[testIndex]
        val pincode=testPincodes[testIndex]
        val expected=ExpectedOutputs[testIndex]
        var actual=false
        if(ConfirmOrder.validateDeliveryAddress(city,state,addr,pincode).isEmpty())
            actual=true
        assertEquals(expected,actual)
    }


}