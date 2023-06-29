package com.android.taxx.model.startservicemodel

data class GetMovingResponse(
    val success : Boolean,
    val data : RiderLocationData
)

data class RiderLocationData(
    val id : Int,
    val latitude : Double,
    val longitude : Double,
    val distance : Long,
    val text : String
)
