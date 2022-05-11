package com.andback.pocketfridge.present.config

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.andback.pocketfridge.domain.usecase.datastore.ReadDataStoreUseCase
import com.andback.pocketfridge.present.utils.XAccessTokenInterceptor
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class ApplicationClass : Application(), Configuration.Provider {
    private val baseUrl = "http://k6d206.p.ssafy.io:8080/"
    private val barcodeUrl = "https://openapi.foodsafetykorea.go.kr/api/"
    private val TIME_OUT = 5000L
    @Inject
    lateinit var interceptor: XAccessTokenInterceptor
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    @Inject
    lateinit var readDataStoreUseCase: ReadDataStoreUseCase

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
            .addNetworkInterceptor(interceptor)  // JWT 헤더 전송(추가한 부분)
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        // 채널 생성
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(INGRE_EXPIRY_NOTI_ID, INGRE_EXPIRY_NOTI_NAME, importance)

        // 채널 등록
        val notificationManager =
            this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // hilt work manager Configuration
    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}