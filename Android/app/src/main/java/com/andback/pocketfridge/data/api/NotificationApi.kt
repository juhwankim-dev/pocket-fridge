package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.NotificationEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.PUT

interface NotificationApi {
    @GET("notification")
    fun getNotifications(): Single<BaseResponse<List<NotificationEntity>>>

    // TODO: 알림 리스트를 성공적으로 보내줬다면 서버에서 true로 변경 가능할 듯
    @PUT("notification")
    fun readNotifications(): Single<BaseResponse<Unit>>

    @GET("notification/read")
    fun checkNewNotification(): Single<BaseResponse<Boolean>>
}