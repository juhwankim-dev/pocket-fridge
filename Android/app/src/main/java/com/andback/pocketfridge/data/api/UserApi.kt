package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Observable
import org.jetbrains.annotations.NotNull
import retrofit2.http.*

interface UserApi {
    @FormUrlEncoded
    @POST("user")
    fun signUp(@FieldMap req: MutableMap<String, String>): Observable<BaseResponse<Any>>

    @GET("user/{userEmail}")
    fun sendEmail(@Path("userEmail") @NotNull email: String): Observable<BaseResponse<String>>

    @GET("user/checkemail/{userEmail}")
    fun checkEmail(@Path("userEmail") @NotNull email: String): Observable<BaseResponse<Any>>

    @GET("user/checknickname/{userNickname}")
    fun checkNickname(@Path("userNickname") @NotNull nickname: String): Observable<BaseResponse<Any>>
}