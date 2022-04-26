package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Flowable

interface UserRepository {
    fun signUp(req: MutableMap<String, String>): Flowable<BaseResponse<Any>>
    fun sendEmail(email: String): Flowable<BaseResponse<String>>
    fun checkEmail(email: String): Flowable<BaseResponse<Any>>
    fun checkNickname(nickname: String): Flowable<BaseResponse<Any>>
}