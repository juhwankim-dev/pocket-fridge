package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.*
import com.andback.pocketfridge.domain.model.Token
import io.reactivex.Single
import org.jetbrains.annotations.NotNull
import retrofit2.http.*

interface UserApi {
    @GET("user")
    fun getUser(): Single<BaseResponse<UserEntity>>

    @POST("user")
    fun signUp(@Body signUpEntity: SignUpEntity): Single<BaseResponse<Any>>

    @GET("user/{userEmail}")
    fun sendEmail(@Path("userEmail") @NotNull email: String): Single<BaseResponse<String>>

    @GET("user/checkemail/{userEmail}")
    fun checkEmail(@Path("userEmail") @NotNull email: String): Single<BaseResponse<Any>>

    @GET("user/checknickname/{userNickname}")
    fun checkNickname(@Path("userNickname") @NotNull nickname: String): Single<BaseResponse<Any>>

    @PUT("user/findpassword/{userEmail}")
    fun findPW(@Body userForFind: UserForFindEntity): Single<BaseResponse<Any>>

    @POST("user/login")
    fun login(@Body loginEntity: LoginEntity): Single<BaseResponse<Token>>

    @GET("auth/{socialLoginType}/callback")
    fun socialLogin(
        @Path("socialLoginType") @NotNull socialType: String,
        @Query("code") @NotNull code: String
    ): Single<BaseResponse<Token>>

    @POST("user/token")
    fun updateFcmToken(@Body tokenEntity: FcmTokenEntity): Single<BaseResponse<Any>>
}