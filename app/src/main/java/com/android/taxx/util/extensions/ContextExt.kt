package com.android.taxx.util.extensions // ktlint-disable filename

import android.view.View
import com.android.taxx.util.OnSingleClickListener

fun View.setSingleOnClickListener(onSingleClick: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener { onSingleClick(it) })
}
