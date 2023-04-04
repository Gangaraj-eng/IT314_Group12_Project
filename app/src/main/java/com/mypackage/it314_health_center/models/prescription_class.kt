package com.mypackage.it314_health_center.models

import java.time.Duration
import java.time.chrono.ChronoLocalDateTime
import java.util.Date
import kotlin.properties.Delegates
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class prescription_class {

    var date:Date = Date()
    private lateinit var patient_id: String
    private lateinit var appointment_id :String
    lateinit var issued_by: String
    lateinit var image_url: String

    constructor(patient_id: String, appointment_id: String, image_url: String, issued_by: String){
        this.issued_by=issued_by
        this.appointment_id=appointment_id
        this.patient_id=patient_id
        this.image_url=image_url
    }


}

