package com.andback.pocketfridge.domain.usecase.user

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.model.Password
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class ConfirmPWUseCase @Inject constructor(val repository: UserRepository) {
    operator fun invoke(pw: Password): Single<BaseResponse<Any>> = repository.confirmPW(pw)
}