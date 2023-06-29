package com.android.taxx.util.paymentdialog

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.taxx.R
import com.android.taxx.databinding.DialogPaymentBinding
import com.android.taxx.model.paymentmodel.PaymentListData
import com.android.taxx.model.paymentmodel.PaymentListResponse
import com.android.taxx.model.postformmodel.RequestPostData
import com.android.taxx.model.postformmodel.RequestResponse
import com.android.taxx.model.postformmodel.postFormData
import com.android.taxx.presentation.acceptrider.AcceptriderAcitivity
import com.android.taxx.util.LoadingDialog
import com.android.taxx.util.RetrofitInterface
import com.android.taxx.util.paymentdialog.adapter.PaymentDialogAdapter
import com.android.taxx.util.paymentdialog.network.PaymentAPI
import com.android.taxx.util.paymentdialog.network.RequestAPI
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentDialog : BottomSheetDialogFragment(),OnPaymentClick {

    private var _binding : DialogPaymentBinding? =null
    private val binding get() = _binding!!
    private val dialog = LoadingDialog()
    private val TAG = "debugging"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPaymentLists()

    }


    override fun onClick(item: PaymentListData) {
        dialog.show(parentFragmentManager,"loading")
        postFormData.payments = item.label
        Handler(Looper.getMainLooper()).postDelayed({
            postRequest()
        }, 1500)
    }

    private fun postRequest(){
        val formDatas = RequestPostData(postFormData.uuid,postFormData.ridersId, postFormData.size,
        postFormData.request, postFormData.payments, postFormData.startAddress,postFormData.endAddress)

        Log.d(TAG,formDatas.toString())
        RetrofitInterface().getInstance().create(RequestAPI::class.java)
            .postRequest(formDatas).enqueue(object: Callback<RequestResponse>{
                override fun onResponse(
                    call: Call<RequestResponse>,
                    response: Response<RequestResponse>
                ) {
                    Log.d(TAG,response.raw().toString())

                    if(response.body() != null){
                        Log.d(TAG,response.body().toString())
                        if(response.body()!!.success){
                            val intent = Intent(requireContext(), AcceptriderAcitivity::class.java)
                            startActivity(intent)
                        }else{
                            Log.d(TAG,"error")
                        }
                    }
                }
                override fun onFailure(call: Call<RequestResponse>, t: Throwable) {
                    Log.d(TAG,t.message.toString())
                }
            })
    }

    private fun getPaymentLists(){
        RetrofitInterface().getInstance().create(PaymentAPI::class.java)
            .getPaymentList(postFormData.uuid).enqueue(object: Callback<PaymentListResponse> {
                override fun onResponse(
                    call: Call<PaymentListResponse>,
                    response: Response<PaymentListResponse>
                ) {
                    Log.d(TAG,"${response.raw()}")

                    if(response.body() != null){
                        Log.d(TAG,"${response.body()}")
                        makeRecycler(response.body()!!.data)
                    }
                }
                override fun onFailure(call: Call<PaymentListResponse>, t: Throwable) {
                    Log.d(TAG, "${t.message.toString()}")
                }
            })

    }

    private fun makeRecycler(datas : ArrayList<PaymentListData>){
        val adapter = PaymentDialogAdapter(this, datas)
        binding.recyclerPayment.adapter = adapter
        binding.recyclerPayment.layoutManager = LinearLayoutManager(context)
    }

}