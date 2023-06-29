package com.android.taxx.model.usagehistory

data class UsageHistory(
    val id: Int,
    val size: String,
    val request: String,
    val payments: String,
    val startAddress: String,
    val arrivalAddress: String,
    val createTime: String
)
