package com.android.taxx.presentation.usagehistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.taxx.R
import com.android.taxx.databinding.ActivityUsagehistoryBinding
import com.android.taxx.model.usagehistorymodel.HistoryData
import com.android.taxx.presentation.usagehistory.adapter.HistoryAdapter

class UsagehistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityUsagehistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsagehistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var uhArray = mutableListOf<HistoryData>(
            HistoryData("물품 : 서류 봉투, 박스 1개", "2023. 06. 29. 07:45 pm", "출발지 : 서울 용산구 이태원로 125-1", "도착지 : 서울 광진구 성수로 34-2"),
            HistoryData("물품 : 박스 2개", "2023 .06. 29. 11:27 am", "출발지 : 서울 광진구 건대입구로 13-4", "도착지 : 서울 동대문구 답십리길 1-6"),
            HistoryData("물품 : 서류 봉투 2개", "2023. 06. 28 09:45 pm", "출발지 : 서울 용산구 이태원로 125-1", "도착지 : 서울 동대문구 답십리길 1-6")
        )

        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerHistory.adapter = HistoryAdapter(this, uhArray)

        binding.recyclerHistory.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

    }
}