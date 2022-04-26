package com.andback.pocketfridge.domain.usecase.user

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetSendEmailUseCase @Inject constructor(val repository: UserRepository) {
    fun execute(email: String): Flowable<BaseResponse<String>> = repository.sendEmail(email)
}