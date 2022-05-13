package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Single
import org.jetbrains.annotations.NotNull
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FridgeApi {
    @GET("refrigerator")
    fun getFridges(): Single<BaseResponse<List<FridgeEntity>>>

    @POST("refrigerator/{refrigeratorName}")
    fun createFridge(@Path("refrigeratorName") @NotNull name: String): Single<BaseResponse<Unit>>
}