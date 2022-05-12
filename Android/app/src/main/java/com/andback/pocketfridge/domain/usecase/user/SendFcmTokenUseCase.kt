package com.andback.pocketfridge.domain.usecase.user

import com.andback.pocketfridge.data.model.FcmTokenEntity
import com.andback.pocketfridge.domain.repository.UserRepository
import javax.inject.Inject

class SendFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(fcmTokenEntity: FcmTokenEntity) = userRepository.updateFcmToken(fcmTokenEntity)
}