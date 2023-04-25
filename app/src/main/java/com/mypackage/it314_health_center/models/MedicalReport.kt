package com.mypackage.it314_health_center.models

class MedicalReport {
    var reportName = ""
    var reportId = ""
    var uploadedOnDate = ""
    var uploadedOnTime = ""
    var reportType = ""
    var reportURL = ""

    constructor()
    constructor(name: String, date: String, time: String, type: String, url: String) {
        this.reportName = name
        this.uploadedOnDate = date
        this.uploadedOnTime = time
        this.reportType = type
        this.reportURL = url
    }

}