package com.android.taxx.model.usagehistory

data class UsageHistoryResponse(
    val success: Boolean,
    val data: ArrayList<UsageHistory>
)
