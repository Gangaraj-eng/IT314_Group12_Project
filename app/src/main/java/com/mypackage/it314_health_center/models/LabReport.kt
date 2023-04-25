package com.mypackage.it314_health_center.models

class LabReport {
    var test_name = ""
    var issued_by = ""
    var testDate = ""
    var issuedDate = ""
    var id = ""
    var ReportURL = ""

    constructor()

    constructor(tname: String, issuedBy: String, tdate: String, issDate: String) {
        test_name = tname
        issued_by = issuedBy
        testDate = tdate
        issuedDate = issDate
    }

}