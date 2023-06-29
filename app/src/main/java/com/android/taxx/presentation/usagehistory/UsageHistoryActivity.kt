package com.android.taxx.presentation.usagehistory

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.taxx.databinding.ActivityUsageHistoryBinding
import com.android.taxx.model.usagehistory.UsageHistory
import com.android.taxx.model.usagehistory.UsageHistoryResponse
import com.android.taxx.presentation.usagehistory.network.GetUsageHistory
import com.android.taxx.util.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsageHistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityUsageHistoryBinding
    private val TAG = "debugging"
    var historyDataList = arrayListOf<UsageHistory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsageHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUsageHistory()
    }

    private fun initAdapter() {
        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerHistory.adapter = UsageHistoryAdapter(this, historyDataList)
        binding.recyclerHistory.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }

    private fun getUsageHistory() {
        RetrofitInterface().getInstance().create(GetUsageHistory::class.java)
            .getUsageHistory(2877359669).enqueue(object : Callback<UsageHistoryResponse> {
                override fun onResponse(
                    call: Call<UsageHistoryResponse>,
                    response: Response<UsageHistoryResponse>
                ) {
                    Log.d(TAG, response.raw().toString())
                    if (response.body() != null) {
                        Log.d(TAG, response.body().toString())
                        historyDataList = response.body()!!.data
                        initAdapter()
                    }
                }

                override fun onFailure(call: Call<UsageHistoryResponse>, t: Throwable) {
                    Log.d(TAG, "${t.message}")
                }
            })
    }
}
