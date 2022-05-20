package com.andback.pocketfridge.domain.usecase.notification

import com.andback.pocketfridge.domain.repository.NotificationRepository
import javax.inject.Inject

class ReadNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke() = notificationRepository.readNotifications()
}