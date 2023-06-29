package com.android.taxx.presentation.selectrider.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.taxx.databinding.ItemSelectriderRidersBinding
import com.android.taxx.model.postformmodel.postFormData
import com.android.taxx.model.ridermodel.RiderData
import com.android.taxx.presentation.selectrider.SelectriderActivity

class RiderAdapter(val context : Context, val datas : Array<RiderData>)
    : RecyclerView.Adapter<RiderAdapter.ViewHolder>(){

    private val TAG = "debugging"
    var selectPos = -1

    inner class ViewHolder(private val viewBinding: ItemSelectriderRidersBinding)
        : RecyclerView.ViewHolder(viewBinding.root){

        // check 표시여부 저장할 데이터사이즈와 동일한 크기의 배열 생성.
        var checkarr= Array(datas.size){false}

        fun bind(item : RiderData){
            viewBinding.ivRider.setImageResource(item.img)
            viewBinding.tvRiderName.text = item.name
            viewBinding.tvDistance.text = item.distance
            viewBinding.tvRating.text = item.rating
            viewBinding.tvReviewCount.text = item.reviewCount

            if(checkarr[absoluteAdapterPosition]){
                viewBinding.layout.cardElevation = 20F
            }else{
                viewBinding.layout.cardElevation = 0F
            }

            viewBinding.layout.setOnClickListener {
                if(checkarr[absoluteAdapterPosition]){
                    viewBinding.layout.cardElevation = 0F
                    checkarr[absoluteAdapterPosition] = false
                }else{
                    viewBinding.layout.cardElevation = 20F
                    checkarr[absoluteAdapterPosition] = true
                }

                postFormData.riderName = item.name
                postFormData.riderDistance = item.distance
                postFormData.riderRating = item.rating
                postFormData.riderReviewCount = item.reviewCount
                postFormData.riderImg = "url"

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemSelectriderRidersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }


}