package com.android.taxx.model.findridermodel

data class FindRiderResponse(
    val success : Boolean,
    val data : ArrayList<RiderInfoData>
)

data class RiderInfoData(
    val id : Int,
    val name : String,
    val image : String,
    val distance : String,
    val rate : Float,
    val count : Int,
    val time : String
)
