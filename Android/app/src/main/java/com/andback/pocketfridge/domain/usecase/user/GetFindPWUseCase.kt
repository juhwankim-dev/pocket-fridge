package com.andback.pocketfridge.domain.usecase.user

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.UserForFindEntity
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class GetFindPWUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userForFind: UserForFindEntity): Single<BaseResponse<Any>>
            = userRepository.findPW(userForFind)
}