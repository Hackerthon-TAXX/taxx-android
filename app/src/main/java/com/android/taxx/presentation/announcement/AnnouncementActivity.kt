package com.android.taxx.presentation.announcement

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import com.android.taxx.R
import com.android.taxx.databinding.ActivityAnnouncementBinding
import com.android.taxx.util.binding.BindingActivity
import com.android.taxx.util.extensions.setSingleOnClickListener

class AnnouncementActivity :
    BindingActivity<ActivityAnnouncementBinding>(R.layout.activity_announcement) {
    private lateinit var browser: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackButton()
        browser = binding.webAnnouncement
        browser.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
        }
        browser.loadUrl("https://test.gwansik.dev/static/notice/")
    }

    private fun initBackButton() {
        binding.ivAnnouncementBack.setSingleOnClickListener {
            finish()
        }
    }
}
