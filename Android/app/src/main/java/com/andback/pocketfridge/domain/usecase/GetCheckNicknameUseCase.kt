package com.andback.pocketfridge.domain.usecase

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetCheckNicknameUseCase @Inject constructor(val repository: UserRepository) {
    fun execute(nickname: String): Observable<BaseResponse<Any>> = repository.checkNickname(nickname)
}