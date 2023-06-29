package com.android.taxx.presentation.usagehistory

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.taxx.databinding.ItemUsageHistoryBinding
import com.android.taxx.model.usagehistory.UsageHistory

class UsageHistoryAdapter(context: Context, private val historyDataList: ArrayList<UsageHistory>) :
    RecyclerView.Adapter<UsageHistoryAdapter.SearchUsageHistoryViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchUsageHistoryViewHolder {
        Log.d("adapter", "hi")
        val binding = ItemUsageHistoryBinding.inflate(inflater, parent, false)
        return SearchUsageHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchUsageHistoryViewHolder, position: Int) {
        holder.onBind(historyDataList[position])
    }

    override fun getItemCount(): Int {
        return historyDataList.size
    }

    class SearchUsageHistoryViewHolder(private val binding: ItemUsageHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: UsageHistory) {
            binding.tvUsageHistoryArrive.text = data.arrivalAddress
            binding.tvUsageHistoryTitle.text = data.request
            binding.tvUsageHistoryStart.text = data.startAddress
            binding.tvUsageHistoryTime.text = data.createTime
        }
    }
}
