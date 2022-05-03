package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.UserForFindEntity
import io.reactivex.Single

interface UserRepository {
    fun signUp(req: MutableMap<String, String>): Single<BaseResponse<Any>>
    fun sendEmail(email: String): Single<BaseResponse<String>>
    fun checkEmail(email: String): Single<BaseResponse<Any>>
    fun checkNickname(nickname: String): Single<BaseResponse<Any>>
    fun findPW(userForFind: UserForFindEntity): Single<BaseResponse<Any>>
    fun login(req: MutableMap<String, String>): Single<BaseResponse<String>>
}