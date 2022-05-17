package com.andback.pocketfridge.domain.usecase.notification

import com.andback.pocketfridge.domain.repository.NotificationRepository
import javax.inject.Inject

class CheckNewNotiUseCase @Inject constructor(
    private val repository: NotificationRepository
){
    operator fun invoke() = repository.checkNewNotification()
}