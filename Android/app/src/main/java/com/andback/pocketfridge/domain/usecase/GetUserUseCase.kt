package com.andback.pocketfridge.domain.usecase

import com.andback.pocketfridge.data.model.TempEntity
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUserUseCase @Inject constructor(val repository: UserRepository) {
    fun execute(req: MutableMap<String, String>): Single<TempEntity> = repository.signUp(req)
}