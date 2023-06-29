package com.android.taxx.presentation.login.network

import com.android.taxx.model.checkusermodel.CheckuserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CheckuserAPI {

    @GET("users")
    fun checkUser(
        @Query("id") id : Long
    ) : Call<CheckuserResponse>
}