package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Flowable

interface UserRepository {
    fun signUp(req: MutableMap<String, String>): Flowable<BaseResponse<Any>>
}