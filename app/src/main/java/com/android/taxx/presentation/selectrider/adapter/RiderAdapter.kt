package com.android.taxx.presentation.selectrider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.taxx.databinding.ItemSelectriderRidersBinding
import com.android.taxx.model.ridermodel.RiderData

class RiderAdapter(val context : Context, val datas : Array<RiderData>)
    : RecyclerView.Adapter<RiderAdapter.ViewHolder>(){

    private val TAG = "debugging"

    inner class ViewHolder(private val viewBinding: ItemSelectriderRidersBinding)
        : RecyclerView.ViewHolder(viewBinding.root){


        fun bind(item : RiderData){
            viewBinding.ivRider.setImageResource(item.img)
            viewBinding.tvRiderName.text = item.name
            viewBinding.tvDistance.text = item.distance
            viewBinding.tvRating.text = item.rating
            viewBinding.tvReviewCount.text = item.reviewCount

            viewBinding.layout.setOnClickListener {

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