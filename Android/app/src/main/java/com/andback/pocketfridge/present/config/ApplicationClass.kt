package com.andback.pocketfridge.present.config

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass : Application() {
    private val baseUrl = ""
    companion object {
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

//        retrofit = Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
    }
}