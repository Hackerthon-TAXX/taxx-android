package com.android.taxx.presentation.servicestart

import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.taxx.config.BaseActivity
import com.android.taxx.databinding.ActivityServiceStartBinding
import com.android.taxx.model.postformmodel.postFormData
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
import java.net.Socket

class ServicestartActivity : BaseActivity<ActivityServiceStartBinding>(ActivityServiceStartBinding::inflate) {

    private val TAG = "debugging"
    private var distance = 1500L
    private var startDistance = 1500L
    private var text = ""
    private var status = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val Data = RiderLocationData(postFormData.ridersId,postFormData.startLatitude,postFormData.startLongitude,0,"test")
        val Data2 = RiderLocationData(postFormData.ridersId, postFormData.arrivalLatitue,postFormData.arrivalLongitude,0,"test")
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
                        text = response.body()!!.data.text
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
                startDistance = distance
                status = false
            }
        }
    }

    private fun startThread(startData : RiderLocationData, endData : RiderLocationData){
        Thread{
            val bar = binding.progressBar
            while(distance > 500){
                getMovingRider(startData)
                runOnUiThread {
                    binding.tvDistance.text = text
                    val percent = ((startDistance.toDouble() - distance.toDouble()) / startDistance.toDouble()) * 50
                    bar.progress = percent.toInt()
                }
                Thread.sleep(500)
            }
            bar.progress = 50
            status = true
            distance = 600

            runOnUiThread {

                binding.tvAnnounce.text = "기사님이 물품을 수령 했습니다."
                binding.tvDistance.visibility = View.INVISIBLE
                binding.tvAnnounce3.visibility = View.INVISIBLE
                binding.tvAnnounce2.text = "기사님꼐서 곧 출발합니다"
            }

            Thread.sleep(2000)
            startSecondThread(endData)
        }.start()
    }


    private fun startSecondThread(endData : RiderLocationData){
        Thread{
            val bar = binding.progressBar

            runOnUiThread {

                binding.tvAnnounce.text = "기사님이 도착지를 향해 출발 했습니다."
                binding.tvDistance.visibility = View.VISIBLE
                binding.tvAnnounce3.visibility = View.VISIBLE
                binding.tvAnnounce2.text = "기사님께서 도착지까지"
                binding.tvAnnounce3.text = "남았습니다."
            }

            while(distance > 500){
                getMovingRider(endData)
                runOnUiThread {
                    binding.tvDistance.text = text
                    val percent = ((startDistance.toDouble() - distance.toDouble()) / startDistance.toDouble()) * 50 + 50
                    bar.progress = percent.toInt()
                }
                Thread.sleep(500)
            }
            bar.progress = 100
        }.start()
    }

}