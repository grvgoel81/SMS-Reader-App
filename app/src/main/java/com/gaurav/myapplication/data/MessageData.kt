package com.gaurav.myapplication.data

data class MessageData (
    val messageBody: String?,
    val person: String?,
    val time: String,
    val address: String?,
    var expanded: Boolean = false
)