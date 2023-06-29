package com.android.taxx.presentation.servicestart.network

import com.android.taxx.model.startservicemodel.GetMovingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetmovingAPI {
    @GET("/v1/path/{riderId}/{latitude}/{longitude}")
    fun getRidersLocation(
        @Path("riderId") riderId : Int,
        @Path("latitude") latitude : Double,
        @Path("longitude") longitude : Double
    ) : Call<GetMovingResponse>
}