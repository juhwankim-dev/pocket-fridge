package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseReponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun signUp(req: MutableMap<String, String>): Flowable<BaseReponse<Any>>
}