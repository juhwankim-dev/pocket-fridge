package com.andback.pocketfridge.domain.usecase.fridge

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.repository.FridgeRepository
import io.reactivex.Single
import javax.inject.Inject

class ShareFridgeUseCase @Inject constructor(
    private val repository: FridgeRepository
) {
    operator fun invoke(email: String, fridgeId: Int): Single<BaseResponse<String>> {
        return repository.shareFridge(email, fridgeId)
    }
}