package com.andback.pocketfridge.data.repository.like

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Single
import retrofit2.http.Path

interface LikeRemoteDataSource {
    fun addLike(@Path("recipeId") recipeId: Int): Single<BaseResponse<Any>>
    fun deleteLike(@Path("recipeId") recipeId: Int): Single<BaseResponse<Any>>
}