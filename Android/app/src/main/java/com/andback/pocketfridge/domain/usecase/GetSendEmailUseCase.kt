package com.andback.pocketfridge.domain.usecase

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetSendEmailUseCase @Inject constructor(val repository: UserRepository) {
    fun execute(email: String): Observable<BaseResponse<String>> = repository.sendEmail(email)
}