package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Flowable
import retrofit2.http.*

interface UserApi {
    @FormUrlEncoded
    @POST("user/signup")
    fun signUp(@FieldMap req: MutableMap<String, String>): Flowable<BaseResponse<Any>>
}