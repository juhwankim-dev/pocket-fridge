package com.andback.pocketfridge.domain.usecase.notification

import com.andback.pocketfridge.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationListUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke() = repository.getNotifications()
}