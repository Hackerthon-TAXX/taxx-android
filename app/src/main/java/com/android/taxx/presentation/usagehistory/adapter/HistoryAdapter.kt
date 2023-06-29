package com.android.taxx.presentation.usagehistory.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.taxx.databinding.ItemUsagehistoryBinding
import com.android.taxx.model.usagehistorymodel.HistoryData

class HistoryAdapter (val context : Context, val datas : MutableList<HistoryData>)
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemUsagehistoryBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item : HistoryData){
            binding.historyTitle.text = item.title
            binding.historyTime.text = item.time
            binding.historyStart.text = item.startAddress
            binding.historyArrive.text = item.arriveAddress
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemUsagehistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}