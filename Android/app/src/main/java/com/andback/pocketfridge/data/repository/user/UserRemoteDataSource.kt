package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.LoginEntity
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.data.model.UserForFindEntity
import io.reactivex.Single
import org.jetbrains.annotations.NotNull
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.Path

interface UserRemoteDataSource {
    fun getUser(): Single<BaseResponse<UserEntity>>
    fun signUp(@FieldMap req: MutableMap<String, String>): Single<BaseResponse<Any>>
    fun sendEmail(@Path("userEmail") @NotNull email: String): Single<BaseResponse<String>>
    fun checkEmail(@Path("userEmail") @NotNull email: String): Single<BaseResponse<Any>>
    fun checkNickname(@Path("userNickname") @NotNull nickname: String): Single<BaseResponse<Any>>
    fun findPW(@Body userForFind: UserForFindEntity): Single<BaseResponse<Any>>
    fun login(@Body loginEntity: LoginEntity): Single<BaseResponse<String>>
}