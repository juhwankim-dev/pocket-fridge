package com.andback.pocketfridge.data.api

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeApi {
    @POST("like/{recipeId}")
    fun addLike(@Path("recipeId") recipeId: Int): Single<BaseResponse<Any>>

    @DELETE("like/{recipeId}")
    fun deleteLike(@Path("recipeId") recipeId: Int): Single<BaseResponse<Any>>
}