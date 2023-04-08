package com.mypackage.it314_health_center.models

class Doctor {
    var name = ""
    var email = ""
    var id = ""
    var type = ""

    constructor()
    constructor(id: String, name: String, email: String, type: String = "General") {
        this.name = name
        this.email = email
        this.id = id
        this.type = type
    }
}