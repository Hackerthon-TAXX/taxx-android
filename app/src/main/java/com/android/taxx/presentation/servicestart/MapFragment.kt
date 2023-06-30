package com.android.taxx.presentation.servicestart

import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.taxx.R
import com.android.taxx.config.App
import com.android.taxx.config.BaseFragment
import com.android.taxx.databinding.FragmentMapBinding
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

class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map) {

    private val TAG = "debugging"
    private var distance = 1500L
    private var startDistance = 1500L
    private var text = ""
    private var status = true
    private var floor = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val Data = RiderLocationData(
            postFormData.ridersId,
            postFormData.startLongitude,
            postFormData.startLatitude,0,"test")
        val Data2 = RiderLocationData(
            postFormData.ridersId, postFormData.arrivalLongitude,
            postFormData.arrivalLatitue,0,"test")

        startThread(Data,Data2)

    }

    private fun getMovingRider(data : RiderLocationData){
        RetrofitInterface().getInstance().create(GetmovingAPI::class.java)
            .getRidersLocation(data.id,data.latitude,data.longitude).enqueue(object:
                Callback<GetMovingResponse> {
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

        binding.mapView.removeAllPOIItems()
        val markerArr : ArrayList<MapPOIItem> = arrayListOf()
        val startm = MapPOIItem()
        val endm = MapPOIItem()

        val startmp = MapPoint.mapPointWithGeoCoord(start.x, start.y)
        val endmp = MapPoint.mapPointWithGeoCoord(end.x,end.y)

        startm.itemName = start.placeName
        endm.itemName = end.placeName

        startm.mapPoint = startmp
        endm.mapPoint = endmp



        if(floor == 0){
            startm.apply{
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.marker_taxi
            }
            endm.apply{
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.marker_box
            }
        }else{
            startm.apply{
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.marker_boxi
            }
            endm.apply{
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.marker_arrive
            }
        }

        markerArr.add(startm)
        markerArr.add(endm)

        binding.mapView.addPOIItems(markerArr.toTypedArray())

        if(status){
            binding.mapView.fitMapViewAreaToShowAllPOIItems()
            binding.mapView.zoomOut(true)
            startDistance = distance
            status = false
        }

    }

    private fun startThread(startData : RiderLocationData, endData : RiderLocationData){
        floor = 0
        Thread{
            while(distance > 500){
                getMovingRider(startData)


                (activity as ServicestartActivity).RoomToFrag().updateDistance(text)
                val percent = ((startDistance.toDouble() - distance.toDouble()) / startDistance.toDouble()) * 50

                (activity as ServicestartActivity).RoomToFrag().updateProgress(percent.toInt())
                Thread.sleep(150)
            }

            (activity as ServicestartActivity).RoomToFrag().updateProgress(50)
            status = true
            distance = 600



            (activity as ServicestartActivity).RoomToFrag().updateAnnounce1("기사님이 물품을 수령 했습니다.")

            (activity as ServicestartActivity).RoomToFrag().updateAnnounce2("기사님꼐서 곧 출발합니다")

            (activity as ServicestartActivity).RoomToFrag().show1(false)


            Thread.sleep(2000)
            startSecondThread(endData)
        }.start()
    }


    private fun startSecondThread(endData : RiderLocationData){
        floor = 1
        Thread{



            (activity as ServicestartActivity).RoomToFrag().show1(true)

            (activity as ServicestartActivity).RoomToFrag().updateAnnounce1("기사님이 도착지를 향해 출발 했습니다.")

            (activity as ServicestartActivity).RoomToFrag().updateAnnounce2( "기사님께서 도착지까지")

            (activity as ServicestartActivity).RoomToFrag().updateAnnounce3( "남았습니다.")


            while(distance > 500){
                getMovingRider(endData)
                (activity as ServicestartActivity).RoomToFrag().updateDistance(text)
                val percent = ((startDistance.toDouble() - distance.toDouble()) / startDistance.toDouble()) * 50 + 50
                (activity as ServicestartActivity).RoomToFrag().updateProgress(percent.toInt())

                Thread.sleep(150)
            }

            (activity as ServicestartActivity).RoomToFrag().updateProgress(100)
            (activity as ServicestartActivity).RoomToFrag().updateAnnounce1("기사님이 퀵 배송을 완료 했습니다.")
            (activity as ServicestartActivity).RoomToFrag().show2()

            parentFragmentManager.beginTransaction()
                .replace(R.id.frame,ServiceEndFragment())
                .commitAllowingStateLoss()
        }.start()
    }
}