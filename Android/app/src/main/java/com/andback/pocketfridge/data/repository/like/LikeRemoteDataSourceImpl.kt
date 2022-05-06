package com.andback.pocketfridge.data.repository.like

import com.andback.pocketfridge.data.api.LikeApi
import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class LikeRemoteDataSourceImpl @Inject constructor(private val likeApi: LikeApi) :
    LikeRemoteDataSource {

    override fun addLike(recipeId: Int): Single<BaseResponse<Any>> {
        return likeApi.addLike(recipeId)
    }

    override fun deleteLike(recipeId: Int): Single<BaseResponse<Any>> {
        return likeApi.deleteLike(recipeId)
    }
}