package com.mypackage.it314_health_center.adapters

import java.text.SimpleDateFormat
import java.util.*

object ValidationFunctions {

    fun isValidEmail(email: String): Boolean {
        return true
    }

    fun isValidMobile(mobile: String): Boolean {
        return false
    }

    fun convertTimeInMillisToString(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        val date = Date(time)
        return simpleDateFormat.format(date)
    }

    fun convertDateInMillisToString(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(time)
        return simpleDateFormat.format(date)
    }
}