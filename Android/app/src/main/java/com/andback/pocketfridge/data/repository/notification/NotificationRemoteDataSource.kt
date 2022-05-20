package com.andback.pocketfridge.data.repository.notification

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.NotificationEntity
import io.reactivex.Single

interface NotificationRemoteDataSource {
    fun getNotifications(): Single<BaseResponse<List<NotificationEntity>>>

    fun readNotifications(): Single<BaseResponse<Unit>>

    fun checkNewNotification(): Single<BaseResponse<Boolean>>
}