package com.android.taxx.util.paymentdialog.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.taxx.databinding.ItemDialogpaymentCardsBinding
import com.android.taxx.model.paymentdialogmodel.PaymentDialogData
import com.android.taxx.util.paymentdialog.OnPaymentClick

class PaymentDialogAdapter(val link : OnPaymentClick, val datas : List<PaymentDialogData>)
    : RecyclerView.Adapter<PaymentDialogAdapter.ViewHolder>(){
    private val TAG = "debugging"

    inner class ViewHolder(private val viewBinding: ItemDialogpaymentCardsBinding)
        : RecyclerView.ViewHolder(viewBinding.root){


        // 데이터 (coinPriceList) 를 view에 삽입
        fun bind(item : PaymentDialogData){
            viewBinding.ivCard.setImageResource(item.img)
            viewBinding.layout.setOnClickListener {
                link.onClick(item)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemDialogpaymentCardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }

}