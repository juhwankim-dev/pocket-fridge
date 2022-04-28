package com.andback.pocketfridge.domain.usecase.user

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetSignUpUseCase @Inject constructor(val repository: UserRepository) {
    fun execute(req: MutableMap<String, String>): Single<BaseResponse<Any>> = repository.signUp(req)
}