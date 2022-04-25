package com.andback.pocketfridge.domain.usecase

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetSignUpUseCase @Inject constructor(val repository: UserRepository) {
    fun execute(req: MutableMap<String, String>): Flowable<BaseResponse<Any>> = repository.signUp(req)
}