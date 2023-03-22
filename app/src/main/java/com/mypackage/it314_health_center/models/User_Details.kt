package com.mypackage.it314_health_center.models

class User_Details {
    var userName:String?=null
    var date_of_birth:String?=null
    var age:Int=0
    var gender:String?=null
    var address_line_1:String?=null
    var address_line_2:String?=null
    var city:String?=null
    var state:String?=null
    var country:String?=null
    var pincode:Int=0
    constructor()
    constructor(userName:String?,dob:String?,age:Int?,gender:String?,a1:String?,a2:String?,
    city:String?,state:String,country:String?,pincode:Int?)
    {
            this.userName=userName
        this.date_of_birth=dob
        if (age != null) {
            this.age=age
        }
        this.gender=gender
        this.address_line_1=a1
        this.address_line_2=a2
        this.city=city
        this.state=state
        this.country=country
        this.pincode=pincode!!
    }
}