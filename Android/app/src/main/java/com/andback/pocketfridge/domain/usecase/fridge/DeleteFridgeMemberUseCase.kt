package com.andback.pocketfridge.domain.usecase.fridge

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.repository.FridgeRepository
import io.reactivex.Single
import javax.inject.Inject

class DeleteFridgeMemberUseCase @Inject constructor(
    private val fridgeRepository: FridgeRepository
) {
    operator fun invoke(id: Int, email: String): Single<BaseResponse<String>> = fridgeRepository.deleteFridgeMember(id, email)
}