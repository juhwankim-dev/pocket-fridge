package com.andback.pocketfridge.domain.usecase.user

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.model.Token
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class SocialLoginUseCase @Inject constructor(val repository: UserRepository) {
    operator fun invoke(socialType: String, code: String): Single<BaseResponse<Token>>
            = repository.socialLogin(socialType, code)
}