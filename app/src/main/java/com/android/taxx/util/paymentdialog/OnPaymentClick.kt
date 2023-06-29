package com.android.taxx.util.paymentdialog

import com.android.taxx.model.paymentdialogmodel.PaymentDialogData

interface OnPaymentClick {
    fun onClick(item : PaymentDialogData)
}