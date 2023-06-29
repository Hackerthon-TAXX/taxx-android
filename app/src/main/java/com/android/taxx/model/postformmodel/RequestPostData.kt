package com.android.taxx.model.postformmodel

data class RequestPostData(
    var usersId : Long,
    var ridersId : Int,
    var size : String,
    var request : String,
    var payments : String,
    var startLongitude : Double,
    var startLatitude :Double,
    var arrivalLatitude : Double,
    var arrivalLongitude : Double
)
