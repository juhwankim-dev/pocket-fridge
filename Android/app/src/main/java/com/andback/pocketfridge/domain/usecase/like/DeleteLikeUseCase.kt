package com.andback.pocketfridge.domain.usecase.like

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.repository.LikeRepository
import io.reactivex.Single
import javax.inject.Inject

class DeleteLikeUseCase @Inject constructor(
    private val likeRepository: LikeRepository
) {
    operator fun invoke(recipeId: Int): Single<BaseResponse<Any>> {
        return likeRepository.deleteLike(recipeId)
    }
}