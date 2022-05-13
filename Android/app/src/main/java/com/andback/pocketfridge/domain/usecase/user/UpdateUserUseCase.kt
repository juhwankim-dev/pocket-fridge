package com.andback.pocketfridge.domain.usecase.user

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.UserEditEntity
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(val repository: UserRepository) {
    operator fun invoke(userEditEntity: UserEditEntity): Single<BaseResponse<Any>> = repository.updateUser(userEditEntity)
}