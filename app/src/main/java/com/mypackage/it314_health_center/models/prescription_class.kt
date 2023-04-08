package com.mypackage.it314_health_center.models

class prescription_class {

    var date: Long = 0
    lateinit var patient_id: String
    lateinit var appointment_id: String
    lateinit var issued_by: String
    lateinit var image_url: String

    constructor()
    constructor(patient_id: String, appointment_id: String, image_url: String, issued_by: String) {
        date = System.currentTimeMillis()
        this.issued_by = issued_by
        this.appointment_id = appointment_id
        this.patient_id = patient_id
        this.image_url = image_url
    }


}

