package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.repository.like.LikeRemoteDataSource
import com.andback.pocketfridge.domain.repository.LikeRepository
import io.reactivex.Single
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val likeRemoteDataSource: LikeRemoteDataSource
) : LikeRepository {
    override fun addLike(recipeId: Int): Single<BaseResponse<Any>> {
        return likeRemoteDataSource.addLike(recipeId)
    }

    override fun deleteLike(recipeId: Int): Single<BaseResponse<Any>> {
        return likeRemoteDataSource.deleteLike(recipeId)
    }
}