package com.mypackage.it314_health_center

class patientAppointment {

    var patientId:String?= null
    var basicAppointmentList: ArrayList<basicAppointment>? = null

    constructor(){}

    constructor(patientId:String?,basicAppointmentList: ArrayList<basicAppointment>?)
    {
        this.patientId = patientId
        this.basicAppointmentList = basicAppointmentList
    }

}