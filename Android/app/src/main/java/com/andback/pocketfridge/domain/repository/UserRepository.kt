package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.*
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<BaseResponse<UserEntity>>
    fun signUp(signUpEntity: SignUpEntity): Single<BaseResponse<Any>>
    fun sendEmail(email: String): Single<BaseResponse<String>>
    fun checkEmail(email: String): Single<BaseResponse<Any>>
    fun checkNickname(nickname: String): Single<BaseResponse<Any>>
    fun findPW(userForFind: UserForFindEntity): Single<BaseResponse<Any>>
    fun login(loginEntity: LoginEntity): Single<BaseResponse<String>>
    fun updateUser(userEditEntity: UserEditEntity): Single<BaseResponse<Any>>
    fun socialLogin(socialType: String, code: String): Single<BaseResponse<String>>
}