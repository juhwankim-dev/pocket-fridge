package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FridgeApi {
    @GET("refrigerator/{userEmail}")
    fun getFridges(@Path("userEmail") email: String): Observable<BaseResponse<List<FridgeEntity>>>
}