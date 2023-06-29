package com.android.taxx.presentation.quickrequest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.taxx.databinding.ItemQuickRequestBinding
import com.android.taxx.model.postformmodel.postFormData
import com.android.taxx.model.quickrequest.Size

class QuickRequestSizeAdapter(context: Context, private val boxSizeList: List<Size>) :
    RecyclerView.Adapter<QuickRequestSizeAdapter.SearchKeywordViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchKeywordViewHolder {
        val binding = ItemQuickRequestBinding.inflate(inflater, parent, false)
        return SearchKeywordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchKeywordViewHolder, position: Int) {
        holder.onBind(boxSizeList[position])
    }

    override fun getItemCount(): Int {
        return boxSizeList.size
    }

    class SearchKeywordViewHolder(private val binding: ItemQuickRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Size) {
            binding.ivQuickRequestItem.setImageResource(data.image)
            binding.tvQuickRequestItem.text = data.size
            binding.rootview.setOnClickListener {
                postFormData.size = data.size
            }
        }
    }
}
