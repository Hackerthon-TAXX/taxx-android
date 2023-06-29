package com.android.taxx.presentation.quickrequest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.android.taxx.R
import com.android.taxx.databinding.ActivityQuickRequestBinding
import com.android.taxx.model.findridermodel.FindRiderResponse
import com.android.taxx.model.postformmodel.postFormData
import com.android.taxx.model.quickrequest.Size
import com.android.taxx.presentation.addressweb.AddressWebActivity
import com.android.taxx.presentation.quickrequest.network.FindRiderAPI
import com.android.taxx.presentation.selectrider.SelectriderActivity
import com.android.taxx.util.RetrofitInterface
import com.android.taxx.util.binding.BindingActivity
import com.android.taxx.util.extensions.setSingleOnClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuickRequestActivity :
    BindingActivity<ActivityQuickRequestBinding>(R.layout.activity_quick_request) {


    private val TAG = "debugging"

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
            postFormData.request = binding.etRequestText.text.toString()
            val intent = Intent(this,SelectriderActivity::class.java)
            startActivity(intent)
        }

    }

    private val getSearchStartResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { results ->
            if (results.resultCode == RESULT_OK) {
                if (results.data != null) {
                    val addressName = results.data!!.getStringExtra("address_name")
                    postFormData.startLatitude = results.data!!.getStringExtra("x")!!.toDouble()
                    postFormData.startLongitude = results.data!!.getStringExtra("y")!!.toDouble()
                    binding.etQuickRequestStart.setText(addressName)

                    if(binding.etQuickRequestStart.text.isNotBlank() && binding.etQuickRequestEnd.text.isNotBlank()){
                        binding.tvQuickRequestCall.setBackgroundResource(R.drawable.shape_red_fill_10_rect)
                    }else{
                        binding.tvQuickRequestCall.setBackgroundResource(R.drawable.shape_gray_fill_10_rect)
                    }
                }
            }
        }
    private val getSearchEndResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { results ->
            if (results.resultCode == RESULT_OK) {
                if (results.data != null) {
                    val addressName = results.data!!.getStringExtra("address_name")
                    postFormData.arrivalLatitue = results.data!!.getStringExtra("x")!!.toDouble()
                    postFormData.arrivalLongitude = results.data!!.getStringExtra("y")!!.toDouble()
                    binding.etQuickRequestEnd.setText(addressName)

                    if(binding.etQuickRequestStart.text.isNotBlank() && binding.etQuickRequestEnd.text.isNotBlank()){
                        binding.tvQuickRequestCall.setBackgroundResource(R.drawable.shape_red_fill_10_rect)
                    }else{
                        binding.tvQuickRequestCall.setBackgroundResource(R.drawable.shape_gray_fill_10_rect)
                    }
                }
            }
        }

    private fun initAdapter() {
        val quickRequestSizeAdapter = QuickRequestSizeAdapter(this, boxSizeList)
        binding.rvQuickRequest.adapter = quickRequestSizeAdapter
    }
}
