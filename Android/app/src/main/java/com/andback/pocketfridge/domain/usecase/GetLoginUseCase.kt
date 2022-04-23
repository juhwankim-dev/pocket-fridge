package com.andback.pocketfridge.domain.usecase

import com.andback.pocketfridge.data.model.BaseReponse
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(val repository: UserRepository) {
    fun execute(req: MutableMap<String, String>): Flowable<BaseReponse<Any>> = repository.signUp(req)
}