package com.android.taxx.config

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App : Application() {
    //  앱의 context 를 instance 변수에 저장
    init{
        instance=this
    }


    companion object{
        private var instance : App? = null

        lateinit var sharedPreferences: SharedPreferences

        // 앱의 context 를 불러오는 함수
        fun context() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        sharedPreferences =
            applicationContext.getSharedPreferences("taxx", MODE_PRIVATE)
    }

}