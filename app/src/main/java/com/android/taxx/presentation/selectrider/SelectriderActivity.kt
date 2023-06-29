package com.android.taxx.presentation.selectrider

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.taxx.R
import com.android.taxx.config.BaseActivity
import com.android.taxx.databinding.ActivitySelectRiderBinding
import com.android.taxx.model.ridermodel.RiderData
import com.android.taxx.presentation.selectrider.adapter.RiderAdapter
import com.android.taxx.util.paymentdialog.PaymentDialog

class SelectriderActivity : BaseActivity<ActivitySelectRiderBinding>(ActivitySelectRiderBinding::inflate){


    val data1 = RiderData("김철중", R.drawable.rider,"210m","5.0","(12)","White")
    val datas = arrayOf(data1,data1,data1,data1,data1,data1,data1,data1,data1,data1,data1,data1,data1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeRecycler()

        binding.btnCall.setOnClickListener {
            val bottomSheet = PaymentDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

    }

    fun makeRecycler(){
        val adapter = RiderAdapter(this,datas)
        binding.recyclerRiders.adapter = adapter
        binding.recyclerRiders.layoutManager = LinearLayoutManager(this)
    }


}