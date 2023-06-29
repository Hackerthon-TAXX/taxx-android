package com.android.taxx.presentation.servicestart

import android.os.Bundle
import com.android.taxx.config.BaseActivity
import com.android.taxx.databinding.ActivityServiceStartBinding

class ServicestartActivity : BaseActivity<ActivityServiceStartBinding>(ActivityServiceStartBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}