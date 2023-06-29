package com.android.taxx.presentation.addressweb

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.android.taxx.R
import com.android.taxx.databinding.ActivityAddressWebBinding
import com.android.taxx.model.quickrequest.Location
import com.android.taxx.util.binding.BindingActivity

class AddressWebActivity :
    BindingActivity<ActivityAddressWebBinding>(R.layout.activity_address_web) {
    private lateinit var browser: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        browser = binding.webAddress
        browser.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
        }
        browser.addJavascriptInterface(KaKaoJavaScriptInterface(), "Android")
        browser.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        browser.loadUrl("https://test.gwansik.dev/static/kakao/")
    }

    inner class KaKaoJavaScriptInterface {
        @JavascriptInterface
        fun processDATA(data: String) {
            val extra = Bundle()
            val intent = Intent()
            val str = data.split(":")
            val location = Location(str[0], str[1], str[2])
            Log.d("split", str[0])
            extra.putString("data", location.address_name)
            intent.putExtras(extra)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
