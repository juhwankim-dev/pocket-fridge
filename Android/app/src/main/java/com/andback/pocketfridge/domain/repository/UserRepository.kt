package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.TempEntity
import io.reactivex.Single

interface UserRepository {
    fun signUp(req: MutableMap<String, String>): Single<TempEntity>
}