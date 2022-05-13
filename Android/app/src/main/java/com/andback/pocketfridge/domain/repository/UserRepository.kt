package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.*
import com.andback.pocketfridge.domain.model.Password
import com.andback.pocketfridge.domain.model.Token
import io.reactivex.Single
import retrofit2.http.Body

interface UserRepository {
    fun getUser(): Single<BaseResponse<UserEntity>>
    fun signUp(signUpEntity: SignUpEntity): Single<BaseResponse<Any>>
    fun sendEmail(email: String): Single<BaseResponse<String>>
    fun checkEmail(email: String): Single<BaseResponse<Any>>
    fun checkNickname(nickname: String): Single<BaseResponse<Any>>
    fun findPW(userForFind: UserForFindEntity): Single<BaseResponse<Any>>
    fun updateUser(userEditEntity: UserEditEntity): Single<BaseResponse<Any>>
    fun confirmPW(password: Password): Single<BaseResponse<Any>>
    fun login(loginEntity: LoginEntity): Single<BaseResponse<Token>>
    fun socialLogin(socialType: String, code: String): Single<BaseResponse<Token>>
    fun updateFcmToken(tokenEntity: FcmTokenEntity): Single<BaseResponse<Any>>
}