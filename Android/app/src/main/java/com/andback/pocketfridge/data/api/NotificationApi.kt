package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.NotificationEntity
import io.reactivex.Single
import retrofit2.http.GET

interface NotificationApi {
    @GET("notification")
    fun getNotifications(): Single<BaseResponse<List<NotificationEntity>>>
}