package com.android.taxx.util.paymentdialog

import com.android.taxx.model.paymentmodel.PaymentListData

interface OnPaymentClick {
    fun onClick(item : PaymentListData)
}