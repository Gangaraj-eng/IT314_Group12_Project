package com.mypackage.it314_health_center

import java.util.UUID
import kotlin.random.Random

class basicAppointment {

    var date:String?= null
    var time:String?= null
    var problemDescription:String?= null
    var type:String?= null
    var name:String?=null

    constructor(){}

    constructor(name:String?, date:String?,time:String?,problemDescription:String?,type:String?)
    {
        this.name = name
        this.date = date
        this.time = time
        this.problemDescription = problemDescription
        this.type = type
    }
}