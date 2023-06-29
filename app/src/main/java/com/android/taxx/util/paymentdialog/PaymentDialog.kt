package com.android.taxx.util.paymentdialog

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.taxx.R
import com.android.taxx.databinding.DialogPaymentBinding
import com.android.taxx.model.paymentdialogmodel.PaymentDialogData
import com.android.taxx.presentation.LoginActivity
import com.android.taxx.presentation.SplashActivity
import com.android.taxx.presentation.acceptrider.AcceptriderAcitivity
import com.android.taxx.util.LoadingDialog
import com.android.taxx.util.paymentdialog.adapter.PaymentDialogAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaymentDialog : BottomSheetDialogFragment(),OnPaymentClick {

    private var _binding : DialogPaymentBinding? =null
    private val binding get() = _binding!!
    private val dialog = LoadingDialog()
    private val TAG = "debugging"

    val data1 = PaymentDialogData(R.drawable.payment_dialog_toss)
    val data2 = PaymentDialogData(R.drawable.payment_dialog_kapay)
    val data3 = PaymentDialogData(R.drawable.payment_dialog_uri)
    val data4 = PaymentDialogData(R.drawable.payment_dialog_sinhan)
    val datas = listOf(data1,data2,data3,data4)

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

        val adapter = PaymentDialogAdapter(this, datas)
        binding.recyclerPayment.adapter = adapter
        binding.recyclerPayment.layoutManager = LinearLayoutManager(context)

    }

    override fun onClick(item: PaymentDialogData) {
        dialog.show(parentFragmentManager,"loading")
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(requireContext(), AcceptriderAcitivity::class.java)
            startActivity(intent)
        }, 3000)
    }

}