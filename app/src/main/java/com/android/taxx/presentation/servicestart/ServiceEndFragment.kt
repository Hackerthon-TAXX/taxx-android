package com.android.taxx.presentation.servicestart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.android.taxx.R
import com.android.taxx.config.BaseFragment
import com.android.taxx.databinding.FragmentServiceEndBinding
import com.android.taxx.model.postformmodel.postFormData
import com.android.taxx.model.reviewmodel.ReviewData
import com.android.taxx.model.reviewmodel.ReviewResponse
import com.android.taxx.model.startservicemodel.GetMovingResponse
import com.android.taxx.model.startservicemodel.MarkerData
import com.android.taxx.presentation.main.MainActivity
import com.android.taxx.presentation.servicestart.network.GetmovingAPI
import com.android.taxx.presentation.servicestart.network.ReviewAPI
import com.android.taxx.util.RetrofitInterface
import com.android.taxx.util.ReviewDialog
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceEndFragment : BaseFragment<FragmentServiceEndBinding>(FragmentServiceEndBinding::bind, R.layout.fragment_service_end) {

    private val TAG = "debugging"
    val dialog  = ReviewDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(postFormData.riderImg)
            .into(binding.ivRider)
        binding.tvRiderName.text = postFormData.riderName


        binding.ratingBar.setOnRatingBarChangeListener{ ratingBar, rating, fromUser ->
            binding.ratingBar.rating =rating
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnNow.setOnClickListener {
            dialog.show(parentFragmentManager,"loading")
            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
                postReview()
            }, 2500)
        }

    }

    private fun postReview(){

        val data = ReviewData(postFormData.uuid, postFormData.ridersId, binding.ratingBar.rating ,binding.etEstimate.text.toString())

        Log.d(TAG,data.toString())
        RetrofitInterface().getInstance().create(ReviewAPI::class.java)
            .postReview(data).enqueue(object:
                Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    Log.d(TAG,response.raw().toString())
                    if(response.body() != null){
                        if(response.body()!!.success){
                            Log.d(TAG,response.body().toString())
                            val intent = Intent(requireContext(),MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    Log.d(TAG,t.message.toString())
                }
            })
    }
}