package com.android.taxx.presentation.acceptrider

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.android.taxx.config.BaseActivity
import com.android.taxx.databinding.ActivityAcceptRiderBinding
import com.android.taxx.model.postformmodel.postFormData
import com.android.taxx.presentation.servicestart.ServicestartActivity
import com.bumptech.glide.Glide

class AcceptriderAcitivity : BaseActivity<ActivityAcceptRiderBinding>(ActivityAcceptRiderBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this)
            .load(postFormData.riderImg)
            .into(binding.ivRider)
        binding.tvDistance.text = postFormData.riderDistance
        binding.tvRating.text = postFormData.riderRating
        binding.tvReviewCount.text = "(" + postFormData.riderReviewCount + ")"
        binding.tvRiderName.text = postFormData.riderName
        binding.tvTime.text = "도착 예상 시간 : " + postFormData.time


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ServicestartActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

}