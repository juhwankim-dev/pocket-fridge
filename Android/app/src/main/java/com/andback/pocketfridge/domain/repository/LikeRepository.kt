package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Single

interface LikeRepository {
    fun addLike(recipeId: Int): Single<BaseResponse<Any>>
    fun deleteLike(recipeId: Int): Single<BaseResponse<Any>>
}