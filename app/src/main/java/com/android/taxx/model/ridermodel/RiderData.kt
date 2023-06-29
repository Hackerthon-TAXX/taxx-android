package com.android.taxx.model.ridermodel

data class RiderData(
    val name : String,
    val img : Int,
    val distance : String,
    val rating : String,
    val reviewCount : String,
    var clicked : String
)