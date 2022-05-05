package com.andback.pocketfridge.domain.usecase.user

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.LoginEntity
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(val repository: UserRepository)  {
    operator fun invoke(loginEntity: LoginEntity): Single<BaseResponse<String>> = repository.login(loginEntity)
}