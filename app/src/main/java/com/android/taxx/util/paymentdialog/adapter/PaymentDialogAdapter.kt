package com.android.taxx.util.paymentdialog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.taxx.databinding.ItemDialogpaymentCardsBinding
import com.android.taxx.model.paymentdialogmodel.PaymentDialogData

class PaymentDialogAdapter(val context : Context, val datas : List<PaymentDialogData>)
    : RecyclerView.Adapter<PaymentDialogAdapter.ViewHolder>(){


    inner class ViewHolder(private val viewBinding: ItemDialogpaymentCardsBinding)
        : RecyclerView.ViewHolder(viewBinding.root){


        // 데이터 (coinPriceList) 를 view에 삽입
        fun bind(item : PaymentDialogData){
            viewBinding.ivCard.setImageResource(item.img)
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