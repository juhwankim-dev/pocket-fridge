package com.andback.pocketfridge.domain.usecase.fridge

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.ShareUserEntity
import com.andback.pocketfridge.domain.repository.FridgeRepository
import io.reactivex.Single
import javax.inject.Inject

class GetFridgeMembersUseCase @Inject constructor(
    private val fridgeRepository: FridgeRepository
) {
    operator fun invoke(id: Int): Single<BaseResponse<List<ShareUserEntity>>> = fridgeRepository.getFridgeMembers(id)
}