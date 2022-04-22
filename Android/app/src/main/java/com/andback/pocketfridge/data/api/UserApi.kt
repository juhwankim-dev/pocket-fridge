package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.TempEntity
import io.reactivex.Single
import retrofit2.http.*

interface UserApi {
    @POST("user/signup")
    fun signUp(@FieldMap req: MutableMap<String, String>): Single<TempEntity>
}