package com.android.taxx.presentation.quickrequest

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.android.taxx.R
import com.android.taxx.databinding.ActivityQuickRequestBinding
import com.android.taxx.model.quickrequest.Size
import com.android.taxx.presentation.addressweb.AddressWebActivity
import com.android.taxx.presentation.selectrider.SelectriderActivity
import com.android.taxx.util.binding.BindingActivity
import com.android.taxx.util.extensions.setSingleOnClickListener

class QuickRequestActivity :
    BindingActivity<ActivityQuickRequestBinding>(R.layout.activity_quick_request) {
    private val boxSizeList = arrayListOf<Size>(
        Size(1, "손바닥만 해요!", R.drawable.ic_hand),
        Size(2, "팔 정도 해요!", R.drawable.ic_arm),
        Size(3, "몸통만 해요!", R.drawable.ic_back)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClickListener()
        initAdapter()
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
                    binding.etQuickRequestStart.setText(data)
                }
            }
        }
    private val getSearchEndResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { results ->
            if (results.resultCode == RESULT_OK) {
                if (results.data != null) {
                    val data = results.data!!.getStringExtra("data")
                    binding.etQuickRequestEnd.setText(data)
                }
            }
        }

    private fun initAdapter() {
        val quickRequestSizeAdapter = QuickRequestSizeAdapter(this, boxSizeList)
        binding.rvQuickRequest.adapter = quickRequestSizeAdapter
    }
}
