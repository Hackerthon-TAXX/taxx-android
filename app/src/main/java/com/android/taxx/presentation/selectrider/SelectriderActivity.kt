package com.android.taxx.presentation.selectrider

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.taxx.R
import com.android.taxx.config.BaseActivity
import com.android.taxx.databinding.ActivitySelectRiderBinding
import com.android.taxx.model.findridermodel.FindRiderResponse
import com.android.taxx.model.findridermodel.RiderInfoData
import com.android.taxx.model.postformmodel.postFormData
import com.android.taxx.presentation.quickrequest.network.FindRiderAPI
import com.android.taxx.presentation.selectrider.adapter.RiderAdapter
import com.android.taxx.util.RetrofitInterface
import com.android.taxx.util.extensions.setSingleOnClickListener
import com.android.taxx.util.paymentdialog.PaymentDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectriderActivity :
    BaseActivity<ActivitySelectRiderBinding>(ActivitySelectRiderBinding::inflate) {

    var datas = arrayListOf<RiderInfoData>()
    private val TAG = "debugging"

    inner class RoomToAdapter() {

        fun changeBtnRed() {
            binding.btnCall.setBackgroundResource(R.drawable.shape_red_fill_10_rect)
        }

        fun changeBtnGray() {
            binding.btnCall.setBackgroundResource(R.drawable.shape_gray_fill_10_rect)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postStartLocation()
        initBackButton()

        binding.btnCall.setOnClickListener {
            val bottomSheet = PaymentDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    private fun postStartLocation() {
        RetrofitInterface().getInstance().create(FindRiderAPI::class.java)
            .findRider(postFormData.startLatitude, postFormData.startLongitude).enqueue(object :
                    Callback<FindRiderResponse> {
                    override fun onResponse(
                        call: Call<FindRiderResponse>,
                        response: Response<FindRiderResponse>
                    ) {
                        Log.d(TAG, "${response.raw()}")

                        if (response.body() != null) {
                            Log.d(TAG, "${response.body()!!.data}")
                            if (response.body()!!.success) {
                                datas = response.body()!!.data
                                makeRecycler()
                            } else {
                                Log.d(TAG, "false")
                            }
                        }
                    }

                    override fun onFailure(call: Call<FindRiderResponse>, t: Throwable) {
                    }
                })
    }

    fun makeRecycler() {
        val adapter = RiderAdapter(this, datas, RoomToAdapter())
        binding.recyclerRiders.adapter = adapter
        binding.recyclerRiders.layoutManager = LinearLayoutManager(this)
    }

    fun initBackButton() {
        binding.ivBack.setSingleOnClickListener { finish() }
    }
}
