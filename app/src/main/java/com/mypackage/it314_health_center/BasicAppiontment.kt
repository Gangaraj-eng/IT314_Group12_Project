package com.mypackage.it314_health_center

class BasicAppiontment {


    var date: String? = null
    var time: String? = null
    var timeStamp = 0L
    var problemDescription: String? = null
    var type: String? = null
    var name: String? = null
    var patientID: String = ""
    var doctorType: String = ""
    var doctorId: String = ""

    constructor() {}

    constructor(
        name: String?,
        date: String?,
        time: String?,
        problemDescription: String?,
        type: String?,
        doctorType: String,
        doctorId: String
    ) {
        this.name = name
        this.date = date
        this.time = time
        this.problemDescription = problemDescription
        this.type = type
        this.doctorType = doctorType
        this.doctorId = doctorId
    }
}