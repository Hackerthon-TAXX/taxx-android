package com.android.taxx.model.payment

import android.content.Context

data class Card(
    var cardCompany: String,
    var image: String
) {
    fun getImageId(context: Context): Int {
        return context.resources.getIdentifier(image, "drawable", context.packageName)
    }
}
