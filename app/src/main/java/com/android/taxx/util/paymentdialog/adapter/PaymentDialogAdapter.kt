package com.android.taxx.util.paymentdialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.taxx.R
import com.android.taxx.databinding.ItemDialogpaymentCardsBinding
import com.android.taxx.model.paymentmodel.PaymentListData
import com.android.taxx.model.postformmodel.postFormData
import com.android.taxx.util.paymentdialog.OnPaymentClick

class PaymentDialogAdapter(val link: OnPaymentClick, val datas: ArrayList<PaymentListData>) :
    RecyclerView.Adapter<PaymentDialogAdapter.ViewHolder>() {
    private val TAG = "debugging"

    inner class ViewHolder(private val viewBinding: ItemDialogpaymentCardsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        // 데이터 (coinPriceList) 를 view에 삽입
        fun bind(item: PaymentListData) {
            if (item.type == "kakao") {
                viewBinding.ivCard.setImageResource(R.drawable.payment_dialog_kapay)
            } else if (item.type == "sinhan") {
                viewBinding.ivCard.setImageResource(R.drawable.payment_dialog_sinhan)
            } else if (item.type == "uri") {
                viewBinding.ivCard.setImageResource(R.drawable.payment_dialog_uri)
            } else if (item.type == "toss") {
                viewBinding.ivCard.setImageResource(R.drawable.payment_dialog_toss)
            }

            viewBinding.layout.setOnClickListener {
                postFormData.payments = item.label
                link.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemDialogpaymentCardsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}
