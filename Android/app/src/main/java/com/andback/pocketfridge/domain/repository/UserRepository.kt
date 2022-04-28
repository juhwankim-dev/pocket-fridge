package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun signUp(req: MutableMap<String, String>): Single<BaseResponse<Any>>
    fun sendEmail(email: String): Single<BaseResponse<String>>
    fun checkEmail(email: String): Single<BaseResponse<Any>>
    fun checkNickname(nickname: String): Single<BaseResponse<Any>>
    fun login(req: MutableMap<String, String>): Single<BaseResponse<String>>
}