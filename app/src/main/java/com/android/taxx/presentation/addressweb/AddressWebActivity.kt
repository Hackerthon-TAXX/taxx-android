package com.android.taxx.presentation.addressweb

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.android.taxx.R
import com.android.taxx.databinding.ActivityAddressWebBinding
import com.android.taxx.util.binding.BindingActivity

class AddressWebActivity :
    BindingActivity<ActivityAddressWebBinding>(R.layout.activity_address_web) {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.webAddress.settings.javaScriptEnabled = true
        binding.webAddress.addJavascriptInterface(KaKaoJavaScriptInterface(), "Android")
        binding.webAddress.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                // page loading을 끝냈을 때 호출되는 콜백 메서드
                // 안드로이드에서 자바스크립트 메서드 호출
                binding.webAddress.loadUrl("javascript:sample4_execDaumPostcode()")
            }
        }
        // 최초로 웹뷰 로딩
        binding.webAddress.loadUrl("https://test.gwansik.dev/public/kakao")
    }

    inner class KaKaoJavaScriptInterface {
        @JavascriptInterface
        fun processDATA(data: String?) {
            val extra = Bundle()
            val intent = Intent()

            extra.putString("data", data)
            intent.putExtras(extra)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
