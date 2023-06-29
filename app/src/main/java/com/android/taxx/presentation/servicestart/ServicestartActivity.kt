package com.android.taxx.presentation.servicestart

import android.os.Bundle
import android.util.Log
import com.android.taxx.config.BaseActivity
import com.android.taxx.databinding.ActivityServiceStartBinding
import com.android.taxx.model.startservicemodel.GetMovingResponse
import com.android.taxx.model.startservicemodel.MarkerData
import com.android.taxx.model.startservicemodel.RiderLocationData
import com.android.taxx.presentation.servicestart.network.GetmovingAPI
import com.android.taxx.util.RetrofitInterface
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicestartActivity : BaseActivity<ActivityServiceStartBinding>(ActivityServiceStartBinding::inflate) {


    private val TAG = "debugging"
    private var distance = 1500L
    private var startDistance = 1500L
    private var status = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val Data = RiderLocationData(1,36.7503046,127.2920744,0)
        val Data2 = RiderLocationData(1, 36.7,127.29,0)

        startThread(Data,Data2)
    }

    private fun getMovingRider(data : RiderLocationData){
        RetrofitInterface().getInstance().create(GetmovingAPI::class.java)
            .getRidersLocation(data.id,data.latitude,data.longitude).enqueue(object: Callback<GetMovingResponse> {
                override fun onResponse(
                    call: Call<GetMovingResponse>,
                    response: Response<GetMovingResponse>
                ) {
                    Log.d(TAG,response.raw().toString())
                    if(response.body() != null){
                        Log.d(TAG, response.body().toString())

                        val startData = MarkerData("출발", response.body()!!.data.latitude, response.body()!!.data.longitude)
                        val endData = MarkerData("도착", data.latitude,data.longitude)
                        distance = response.body()!!.data.distance
                        setMarkers(startData, endData)
                    }
                }
                override fun onFailure(call: Call<GetMovingResponse>, t: Throwable) {

                }
            })
    }

    // 출발지, 도착지 지도 이동 함수
    private fun setMarkers(start : MarkerData, end : MarkerData){

        runOnUiThread{
            binding.mapView.removeAllPOIItems()
            val markerArr : ArrayList<MapPOIItem> = arrayListOf()
            val mpArr : Array<MapPoint> = arrayOf()
            val startm = MapPOIItem()
            val endm = MapPOIItem()

            val startmp = MapPoint.mapPointWithGeoCoord(start.x, start.y)
            val endmp = MapPoint.mapPointWithGeoCoord(end.x,end.y)

            startm.itemName = start.placeName
            endm.itemName = end.placeName

            startm.mapPoint = startmp
            endm.mapPoint = endmp

            markerArr.add(startm)
            markerArr.add(endm)
            mpArr.plus(startmp)
            mpArr.plus(endmp)

            binding.mapView.addPOIItems(markerArr.toTypedArray())

            if(status){
                binding.mapView.fitMapViewAreaToShowAllPOIItems()
                binding.mapView.zoomOut(true)
                status = false
            }
        }
    }

    private fun startThread(startData : RiderLocationData, endData : RiderLocationData){
        Thread{
            val bar = binding.progress
            while(distance > 500){
                getMovingRider(startData)
                binding.tvDistance.text = "기사님께서 출발지까지 ${distance}km 남았습니다"
                Thread.sleep(1500)
            }
            distance = 200000
            status = true
            Thread.sleep(1000)
            startSecondThread(endData)
        }.start()
    }


    private fun startSecondThread(endData : RiderLocationData){
        Thread{
            while(distance > 500){
                getMovingRider(endData)
                Thread.sleep(1500)
            }
        }.start()
    }
}