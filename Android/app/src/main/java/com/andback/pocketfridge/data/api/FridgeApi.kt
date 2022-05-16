package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.data.model.ShareUserEntity
import io.reactivex.Single
import org.jetbrains.annotations.NotNull
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FridgeApi {
    @GET("refrigerator")
    fun getFridges(): Single<BaseResponse<List<FridgeEntity>>>

    @POST("refrigerator/{refrigeratorName}")
    fun createFridge(@Path("refrigeratorName") @NotNull name: String): Single<BaseResponse<Unit>>

    @PUT("refrigerator/{refrigeratorId}/{refrigeratorName}")
    fun updateFridgeName(
        @Path("refrigeratorId") @NotNull id: Int,
        @Path("refrigeratorName") @NotNull name: String
    ): Single<BaseResponse<Unit>>

    @DELETE("refrigerator/{refrigeratorId}")
    fun deleteFridge(@Path("refrigeratorId") @NotNull id: Int): Single<BaseResponse<Unit>>

    @GET("refrigerator/share/{userEmail}/{refrigeratorId}")
    fun shareFridge(
        @Path("userEmail") email: String,
        @Path("refrigeratorId") fridgeId: Int
    ): Single<BaseResponse<String>>

    @GET("refrigerator/share/{refrigeratorId}")
    fun getFridgeMembers(@Path ("refrigeratorId") @NotNull id: Int): Single<BaseResponse<List<ShareUserEntity>>>
}