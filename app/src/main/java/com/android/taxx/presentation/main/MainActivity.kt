package com.android.taxx.presentation.main

import android.content.Intent
import android.os.Bundle
import com.android.taxx.R
import com.android.taxx.databinding.ActivityMainBinding
import com.android.taxx.presentation.announcement.AnnouncementActivity
import com.android.taxx.presentation.mypage.MyPageActivity
import com.android.taxx.presentation.payment.PaymentActivity
import com.android.taxx.presentation.quickrequest.QuickRequestActivity
import com.android.taxx.presentation.usagehistory.UsageHistoryActivity
import com.android.taxx.util.binding.BindingActivity
import com.android.taxx.util.extensions.setSingleOnClickListener

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClickListener()
    }

    private fun initClickListener() {
        binding.ivMainPayment.setSingleOnClickListener {
            val intentToPayment = Intent(this, PaymentActivity::class.java)
            startActivity(intentToPayment)
        }
        binding.ivMainAnnouncement.setSingleOnClickListener {
            val intentToAnnouncement = Intent(this, AnnouncementActivity::class.java)
            startActivity(intentToAnnouncement)
        }
        binding.ivMainQuickStart.setSingleOnClickListener {
            val intentToQuickRequest = Intent(this, QuickRequestActivity::class.java)
            startActivity(intentToQuickRequest)
        }
        binding.ivMainMyPage.setSingleOnClickListener {
            val intentToMyPage = Intent(this, MyPageActivity::class.java)
            startActivity(intentToMyPage)
        }
        binding.ivMainUsageHistory.setSingleOnClickListener {
            val intentToUsageHistory = Intent(this, UsageHistoryActivity::class.java)
            startActivity(intentToUsageHistory)
        }
    }
}
