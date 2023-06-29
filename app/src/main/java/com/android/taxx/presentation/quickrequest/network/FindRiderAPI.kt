package com.android.taxx.presentation.quickrequest.network

import com.android.taxx.model.findridermodel.FindRiderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FindRiderAPI {
    @GET("/riders/location/{latitude}/{longitude}")
    fun findRider(
        @Path("latitude") latitude : Double,
        @Path("longitude") longitude : Double
    ) : Call<FindRiderResponse>
}