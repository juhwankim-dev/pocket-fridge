package com.andback.pocketfridge.domain.usecase.user

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.SignUpEntity
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class GetSignUpUseCase @Inject constructor(val repository: UserRepository) {
    operator fun invoke(signUpEntity: SignUpEntity): Single<BaseResponse<Any>> = repository.signUp(signUpEntity)
}