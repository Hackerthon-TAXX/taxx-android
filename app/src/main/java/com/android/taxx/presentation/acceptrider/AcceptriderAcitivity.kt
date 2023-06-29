package com.android.taxx.presentation.acceptrider

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.android.taxx.config.BaseActivity
import com.android.taxx.databinding.ActivityAcceptRiderBinding
import com.android.taxx.presentation.servicestart.ServicestartActivity

class AcceptriderAcitivity : BaseActivity<ActivityAcceptRiderBinding>(ActivityAcceptRiderBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ServicestartActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

}