package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Flowable
import org.jetbrains.annotations.NotNull
import retrofit2.http.FieldMap
import retrofit2.http.Path

interface UserRemoteDataSource {
    fun signUp(@FieldMap req: MutableMap<String, String>): Flowable<BaseResponse<Any>>
    fun sendEmail(@Path("userEmail") @NotNull email: String): Flowable<BaseResponse<String>>
    fun checkEmail(@Path("userEmail") @NotNull email: String): Flowable<BaseResponse<Any>>
    fun checkNickname(@Path("userNickname") @NotNull nickname: String): Flowable<BaseResponse<Any>>
}