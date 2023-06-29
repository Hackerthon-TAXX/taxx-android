package com.android.taxx.presentation.selectrider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.taxx.databinding.ItemSelectriderRidersBinding
import com.android.taxx.model.findridermodel.RiderInfoData
import com.android.taxx.model.postformmodel.postFormData
import com.android.taxx.presentation.selectrider.SelectriderActivity
import com.bumptech.glide.Glide

class RiderAdapter(
    val context: Context,
    val datas: ArrayList<RiderInfoData>,
    val link: SelectriderActivity.RoomToAdapter
) : RecyclerView.Adapter<RiderAdapter.ViewHolder>() {

    private val TAG = "debugging"
    var selectPos = -1

    inner class ViewHolder(private val viewBinding: ItemSelectriderRidersBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        // check 표시여부 저장할 데이터사이즈와 동일한 크기의 배열 생성.
        var checkarr = Array(datas.size) { false }

        fun bind(item: RiderInfoData) {
            Glide.with(context)
                .load(item.image)
                .into(viewBinding.ivRider)
            viewBinding.tvRiderName.text = item.name
            viewBinding.tvDistance.text = item.distance
            viewBinding.tvRating.text = item.rate.toString()
            viewBinding.tvReviewCount.text = "(" + item.count.toString() + ")"
            viewBinding.ratingBar.rating = item.rate

            if (checkarr[absoluteAdapterPosition]) {
                viewBinding.layout.cardElevation = 20F
            } else {
                viewBinding.layout.cardElevation = 0F
            }

            viewBinding.layout.setOnClickListener {
                if (checkarr[absoluteAdapterPosition]) {
                    viewBinding.layout.cardElevation = 0F
                    checkarr[absoluteAdapterPosition] = false
                    link.changeBtnGray()
                } else {
                    viewBinding.layout.cardElevation = 20F
                    checkarr[absoluteAdapterPosition] = true
                    link.changeBtnRed()
                }

                postFormData.ridersId = item.id
                postFormData.riderName = item.name
                postFormData.riderDistance = item.distance
                postFormData.riderRating = item.rate.toString()
                postFormData.riderReviewCount = item.count.toString()
                postFormData.riderImg = item.image
                postFormData.time = item.time
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            ItemSelectriderRidersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}
