package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.LoginEntity
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.data.model.UserForFindEntity
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<BaseResponse<UserEntity>>
    fun signUp(userEntity: UserEntity): Single<BaseResponse<Any>>
    fun sendEmail(email: String): Single<BaseResponse<String>>
    fun checkEmail(email: String): Single<BaseResponse<Any>>
    fun checkNickname(nickname: String): Single<BaseResponse<Any>>
    fun findPW(userForFind: UserForFindEntity): Single<BaseResponse<Any>>
    fun login(loginEntity: LoginEntity): Single<BaseResponse<String>>
}