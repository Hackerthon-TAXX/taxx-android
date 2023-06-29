package com.android.taxx.presentation.login.network

import com.android.taxx.model.makeusermodel.MakeuserPostData
import com.android.taxx.model.makeusermodel.MakeuserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MakeuserAPI {

    @POST("users")
    fun postMakeuser(
        @Body params : MakeuserPostData
    ) : Call<MakeuserResponse>

}