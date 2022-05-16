package com.andback.pocketfridge.data.repository.notification

import com.andback.pocketfridge.data.api.NotificationApi
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.NotificationEntity
import io.reactivex.Single
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val api: NotificationApi
): NotificationRemoteDataSource {

    override fun getNotifications(): Single<BaseResponse<List<NotificationEntity>>> {
        return api.getNotifications()
    }
}