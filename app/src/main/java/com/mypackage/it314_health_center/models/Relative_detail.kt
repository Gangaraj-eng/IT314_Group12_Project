package com.mypackage.it314_health_center.models

class Relative_detail {
    var relativeName: String? = null
    var relativeContact: String? = null


    constructor()

    constructor(name: String, contact: String) {
        this.relativeName = name
        this.relativeContact = contact
    }

}