package com.andback.pocketfridge.present.config

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class ApplicationClass : Application() {
    private val baseUrl = "http://k6d206.p.ssafy.io:8080/"
    private val barcodeUrl = "https://openapi.foodsafetykorea.go.kr/api/"
    private val TIME_OUT = 5000L

    companion object {
        lateinit var retrofit: Retrofit
        lateinit var barcodeRetrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            // Logcat에 'okhttp.OkHttpClient'로 검색하면 http 통신 내용 확인 가능
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS).setLevel(
                    HttpLoggingInterceptor.Level.BODY)
            )
//            .addNetworkInterceptor(XAccessTokenInterceptor())  // JWT 헤더 전송(추가한 부분)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        barcodeRetrofit = Retrofit.Builder()
            .baseUrl(barcodeUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}