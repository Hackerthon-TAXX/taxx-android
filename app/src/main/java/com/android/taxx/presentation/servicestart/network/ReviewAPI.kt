package com.android.taxx.presentation.servicestart.network

import com.android.taxx.model.reviewmodel.ReviewData
import com.android.taxx.model.reviewmodel.ReviewResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReviewAPI {
    @POST("/evals")
    fun postReview(
        @Body params : ReviewData
    ) : Call<ReviewResponse>
}