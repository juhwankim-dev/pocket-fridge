package com.andback.pocketfridge.domain.usecase.fridge

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.domain.repository.FridgeRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetFridgesUseCase @Inject constructor(
    private val fridgeRepository: FridgeRepository
) {
    fun excute(email: String): Observable<BaseResponse<List<FridgeEntity>>> = fridgeRepository.getFridges(email)
}