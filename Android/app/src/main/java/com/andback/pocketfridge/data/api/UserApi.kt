package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Flowable
import org.jetbrains.annotations.NotNull
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @FormUrlEncoded
    @POST("user/signup")
    fun signUp(@FieldMap req: MutableMap<String, String>): Flowable<BaseResponse<Any>>

    @GET("user/{userEmail}")
    fun sendEmail(@Path("userEmail") @NotNull email: String): Flowable<BaseResponse<String>>

    @GET("user/checkemail/{userEmail}")
    fun checkEmail(@Path("userEmail") @NotNull email: String): Flowable<BaseResponse<Any>>

    @GET("user/checknickname/{userNickname}")
    fun checkNickname(@Path("userNickname") @NotNull nickname: String): Flowable<BaseResponse<Any>>
}