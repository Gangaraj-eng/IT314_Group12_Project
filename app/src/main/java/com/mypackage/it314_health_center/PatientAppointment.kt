package com.mypackage.it314_health_center

class PatientAppointment {

    var patientId: String? = null
    var basicAppiontmentList: ArrayList<BasicAppiontment>? = null

    constructor() {}

    constructor(patientId: String?, basicAppiontmentList: ArrayList<BasicAppiontment>?) {
        this.patientId = patientId
        this.basicAppiontmentList = basicAppiontmentList
    }

}