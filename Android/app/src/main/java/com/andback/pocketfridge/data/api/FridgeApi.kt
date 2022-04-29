package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FridgeApi {
    @GET("/refrigerator/{userEmail}")
    fun getFridges(@Query("userEmail") email: String): Single<BaseResponse<List<FridgeEntity>>>
}