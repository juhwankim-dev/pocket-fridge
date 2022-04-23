package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseReponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @FormUrlEncoded
    @POST("user/signup")
    fun signUp(@FieldMap req: MutableMap<String, String>): Flowable<BaseReponse<Any>>
}