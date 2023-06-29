package com.android.taxx.presentation.quickrequest

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.android.taxx.R
import com.android.taxx.databinding.ActivityQuickRequestBinding
import com.android.taxx.presentation.addressweb.AddressWebActivity
import com.android.taxx.presentation.selectrider.SelectriderActivity
import com.android.taxx.util.binding.BindingActivity
import com.android.taxx.util.extensions.setSingleOnClickListener

class QuickRequestActivity :
    BindingActivity<ActivityQuickRequestBinding>(R.layout.activity_quick_request) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClickListener()
    }

    private fun initClickListener() {
        binding.etQuickRequestStart.setSingleOnClickListener {
            val intent = Intent(this, AddressWebActivity::class.java)
            getSearchStartResult.launch(intent)
        }
        binding.etQuickRequestEnd.setSingleOnClickListener {
            val intent = Intent(this, AddressWebActivity::class.java)
            getSearchEndResult.launch(intent)
        }

        binding.tvQuickRequestCall.setSingleOnClickListener { 
            val intent = Intent(this, SelectriderActivity::class.java)
            startActivity(intent)
        }
    }

    private val getSearchStartResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { results ->
            if (results.resultCode == RESULT_OK) {
                if (results.data != null) {
                    val data = results.data!!.getStringExtra("data")
                    binding.etQuickRequestStart?.setText(data)
                }
            }
        }
    private val getSearchEndResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { results ->
            if (results.resultCode == RESULT_OK) {
                if (results.data != null) {
                    val data = results.data!!.getStringExtra("data")
                    binding.etQuickRequestStart?.setText(data)
                }
            }
        }
}
