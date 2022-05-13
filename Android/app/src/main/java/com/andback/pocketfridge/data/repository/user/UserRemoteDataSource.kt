package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.model.*
import com.andback.pocketfridge.domain.model.Password
import com.andback.pocketfridge.domain.model.Token
import io.reactivex.Single
import org.jetbrains.annotations.NotNull
import retrofit2.http.Body
import retrofit2.http.Path

interface UserRemoteDataSource {
    fun getUser(): Single<BaseResponse<UserEntity>>
    fun signUp(@Body signUpEntity: SignUpEntity): Single<BaseResponse<Any>>
    fun sendEmail(@Path("userEmail") @NotNull email: String): Single<BaseResponse<String>>
    fun checkEmail(@Path("userEmail") @NotNull email: String): Single<BaseResponse<Any>>
    fun checkNickname(@Path("userNickname") @NotNull nickname: String): Single<BaseResponse<Any>>
    fun findPW(@Body userForFind: UserForFindEntity): Single<BaseResponse<Any>>
    fun login(@Body loginEntity: LoginEntity): Single<BaseResponse<String>>
    fun updateUser(@Body userEditEntity: UserEditEntity): Single<BaseResponse<Any>>
    fun confirmPW(@Body pw: Password): Single<BaseResponse<Any>>
    fun login(@Body loginEntity: LoginEntity): Single<BaseResponse<Token>>
    fun socialLogin(
        @NotNull socialType: String,
        @NotNull code: String
    ): Single<BaseResponse<Token>>
    fun updateFcmToken(tokenEntity: FcmTokenEntity): Single<BaseResponse<Any>>
}