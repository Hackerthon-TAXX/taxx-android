package com.android.taxx.presentation.usagehistory.network

import com.android.taxx.model.usagehistory.UsageHistoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetUsageHistory {
    @GET("/users/histories/{id}")
    fun getUsageHistory(
        @Path("id") id: Long
    ): Call<UsageHistoryResponse>
}
