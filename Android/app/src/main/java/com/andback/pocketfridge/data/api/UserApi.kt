package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.data.model.UserForFindEntity
import io.reactivex.Single
import org.jetbrains.annotations.NotNull
import retrofit2.http.*

interface UserApi {
    @FormUrlEncoded
    @POST("user")
    fun signUp(@Body userEntity: UserEntity): Single<BaseResponse<Any>>

    @GET("user/{userEmail}")
    fun sendEmail(@Path("userEmail") @NotNull email: String): Single<BaseResponse<String>>

    @GET("user/checkemail/{userEmail}")
    fun checkEmail(@Path("userEmail") @NotNull email: String): Single<BaseResponse<Any>>

    @GET("user/checknickname/{userNickname}")
    fun checkNickname(@Path("userNickname") @NotNull nickname: String): Single<BaseResponse<Any>>

    @PUT("user/findpassword/{userEmail}")
    fun findPW(@Body userForFind: UserForFindEntity): Single<BaseResponse<Any>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@FieldMap req: MutableMap<String, String>): Single<BaseResponse<String>>
}