package com.andback.pocketfridge.domain.usecase.user

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class GetSignUpUseCase @Inject constructor(val repository: UserRepository) {
    operator fun invoke(userEntity: UserEntity): Single<BaseResponse<Any>> = repository.signUp(userEntity)
}