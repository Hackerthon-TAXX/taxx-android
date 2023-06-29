package com.android.taxx.util.paymentdialog.network

import com.android.taxx.model.paymentmodel.PaymentListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PaymentAPI {
    @GET("users/payments/{id}")
    fun getPaymentList(
        @Path("id") id : Long?
    ) : Call<PaymentListResponse>
}