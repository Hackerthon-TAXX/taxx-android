package com.android.taxx.util.paymentdialog.network

import com.android.taxx.model.postformmodel.RequestPostData
import com.android.taxx.model.postformmodel.RequestResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestAPI {
    @POST("/histories")
    fun postRequest(
        @Body params : RequestPostData
    ) : Call<RequestResponse>
}