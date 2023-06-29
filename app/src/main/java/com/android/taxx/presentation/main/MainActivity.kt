package com.android.taxx.presentation.main

import android.content.Intent
import android.os.Bundle
import com.android.taxx.R
import com.android.taxx.databinding.ActivityMainBinding
import com.android.taxx.presentation.quickrequest.QuickRequestActivity
import com.android.taxx.presentation.usagehistory.UsageHistoryActivity
import com.android.taxx.util.binding.BindingActivity
import com.android.taxx.util.extensions.setSingleOnClickListener

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClickListener()
        // TODO initBackPressedCallback()
    }

    private fun initClickListener() {
        binding.ivMainPayment.setSingleOnClickListener {
            // TODO 결제 수단 화면
        }
        binding.ivMainAnnouncement.setSingleOnClickListener {
            // TODO 공지사항 웹 뷰
        }
        binding.ivMainQuickStart.setSingleOnClickListener {
            val intentToQuickRequest = Intent(this, QuickRequestActivity::class.java)
            startActivity(intentToQuickRequest)
        }
        binding.ivMainMyPage.setSingleOnClickListener {
            // TODO 마이 페이지
        }
        binding.ivMainUsageHistory.setSingleOnClickListener {
            val intentToUsageHistory = Intent(this, UsageHistoryActivity::class.java)
            startActivity(intentToUsageHistory)
        }
    }
}
