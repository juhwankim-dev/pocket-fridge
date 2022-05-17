package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.NotificationEntity
import com.andback.pocketfridge.data.repository.notification.NotificationRemoteDataSource
import com.andback.pocketfridge.domain.repository.NotificationRepository
import io.reactivex.Single
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val remoteDataSource: NotificationRemoteDataSource
): NotificationRepository {
    override fun getNotifications(): Single<BaseResponse<List<NotificationEntity>>> {
        return remoteDataSource.getNotifications()
    }

    override fun readNotifications(): Single<BaseResponse<Unit>> {
        return remoteDataSource.readNotifications()
    }

    override fun checkNewNotification(): Single<BaseResponse<Boolean>> {
        return remoteDataSource.checkNewNotification()
    }
}