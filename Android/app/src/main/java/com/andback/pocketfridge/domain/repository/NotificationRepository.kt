package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.NotificationEntity
import io.reactivex.Single

interface NotificationRepository {
    fun getNotifications(): Single<BaseResponse<List<NotificationEntity>>>
}