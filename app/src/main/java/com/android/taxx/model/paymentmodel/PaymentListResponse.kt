package com.android.taxx.model.paymentmodel

data class PaymentListResponse(
    val success : Boolean,
    val data : ArrayList<PaymentListData>
)

data class PaymentListData(
    val type : String,
    val label : String
)